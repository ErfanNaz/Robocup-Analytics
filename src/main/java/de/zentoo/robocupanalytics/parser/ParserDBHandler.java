package de.zentoo.robocupanalytics.parser;

import de.zentoo.robocupanalytics.entity.Ball;
import de.zentoo.robocupanalytics.entity.Player;
import de.zentoo.robocupanalytics.entity.PlayerType;
import de.zentoo.robocupanalytics.entity.Show;
import de.zentoo.robocupanalytics.orm.ORM;
import de.zentoo.robocupanalytics.service.PlayService;
import de.zentoo.robocupanalytics.util.Propertieloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by Erfan Nazmehr on 20.02.15.
 */
public class ParserDBHandler implements Parser.ParseListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParserDBHandler.class);

    private static String GIS_BALL_CREATE = ""; // example  "INSERT INTO BALL_GIS VALUES (NULL, %d, POINT(%.4f%n , %.4f%n));"
    private static String GIS_PLAYER_CREATE = ""; // example "INSERT INTO PLAYER_GIS VALUES (NULL, %d, POINT(%.4f%n , %.4f%n));"
    private static String GIS_BALL_DELETE = ""; // example  "DELETE FROM BALL_GIS;"
    private static String GIS_PLAYER_DELETE = ""; // example "DELETE FROM PLAYER_GIS;"

    private final ORM ORM;
    private final boolean WITH_GIS;
    private final Connection CONNECTION;
    private final PlayService PLAYSERVICE;
    private Parser showParser;
    private Parser playerTypeParser;

    public ParserDBHandler(ORM orm, boolean withGis, PlayService playService){
        WITH_GIS = withGis;
        ORM = orm;
        if(WITH_GIS){
            CONNECTION = orm.getConnection();
            Properties prop = Propertieloader.getInstance().getProperties();
            GIS_BALL_CREATE = prop.getProperty("database.gis.ballCreate");
            GIS_PLAYER_CREATE = prop.getProperty("database.gis.playerCreate");
            GIS_BALL_DELETE = prop.getProperty("database.gis.ballDelete");
            GIS_PLAYER_DELETE = prop.getProperty("database.gis.playerDelete");
        } else {
            CONNECTION = null;
        }
        PLAYSERVICE = playService;
    }

    public void setFile(File file){
        if(showParser != null){
            showParser.interrupt();
        }
        if(playerTypeParser != null){
            playerTypeParser.interrupt();
        }
        this.clearAll();
        PLAYSERVICE.setPlayerTypeList(null);
        playerTypeParser = new PlayerTypeParser();
        playerTypeParser.addFinishedListener(this);
        playerTypeParser.setFile(file);
        playerTypeParser.start();

        PLAYSERVICE.setShowList(null);
        showParser = new ShowParser();
        showParser.addFinishedListener(this);
        showParser.setFile(file);
        showParser.start();
    }

    @Override
    public void onNewShow(Show show) {
        if(PLAYSERVICE != null) PLAYSERVICE.addShow(show);
        try {
            if(WITH_GIS) {
                ORM.getBallDao().create(show.getBall());
                ORM.getShowDao().create(show);
                for (Player player : show.getPlayerlist()) {
                    player.setShow(show);
                    ORM.getPlayDao().create(player);
                    createGisPlayer(player);
                }
                createGisBall(show.getBall());
            } else {
                ORM.getBallDao().create(show.getBall());
                ORM.getShowDao().create(show);
                for (Player player : show.getPlayerlist()) {
                    player.setShow(show);
                    ORM.getPlayDao().create(player);
                }
            }
        } catch (SQLException ex){
            LOGGER.error("error while saving show object {}", show, ex);
        }
    }

    @Override
    public void onNewPlayerType(PlayerType playerType) {
        if(PLAYSERVICE != null) PLAYSERVICE.addPlayerType(playerType);
        try {
            ORM.getPlayerTypeDao().create(playerType);
        } catch (SQLException ex){
            LOGGER.error("error while saving playertype object {} ", playerType, ex);
        }
    }

    public void clearAll(){
        try {
            ORM.getBallDao().deleteBuilder().delete();
            ORM.getShowDao().deleteBuilder().delete();
            ORM.getPlayDao().deleteBuilder().delete();
            ORM.getPlayerTypeDao().deleteBuilder().delete();
            if(WITH_GIS) {
                Statement statement = CONNECTION.createStatement();
                String sql =  String.format(Locale.ENGLISH, GIS_BALL_DELETE);
                statement.executeUpdate(sql);
                sql =  String.format(Locale.ENGLISH, GIS_PLAYER_DELETE);
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createGisBall(Ball ball) throws SQLException {
        Statement statement = CONNECTION.createStatement();
        String sql =  String.format(Locale.ENGLISH,GIS_BALL_CREATE,ball.getId(), ball.getX(), ball.getY());
        statement.executeUpdate(sql);
    }

    private void createGisPlayer(Player player) throws SQLException{
        Statement statement = CONNECTION.createStatement();
        String sql =  String.format(Locale.ENGLISH, GIS_PLAYER_CREATE, player.getId(), player.getX(), player.getY());
        statement.executeUpdate(sql);
    }

}
