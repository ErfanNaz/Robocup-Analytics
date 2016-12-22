package de.zentoo.robocupanalytics.orm;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import de.zentoo.robocupanalytics.entity.Ball;
import de.zentoo.robocupanalytics.entity.Player;
import de.zentoo.robocupanalytics.entity.PlayerType;
import de.zentoo.robocupanalytics.entity.Show;
import de.zentoo.robocupanalytics.event.action.ApplicationAction;
import de.zentoo.robocupanalytics.event.listener.ApplicationActionListener;
import de.zentoo.robocupanalytics.event.ListenerHandler;
import de.zentoo.robocupanalytics.util.Propertieloader;
import org.h2gis.h2spatial.CreateSpatialExtension;
import org.h2gis.utilities.SFSUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Erfan
 */
public class ORMBuilder implements Builder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ORMBuilder.class);

    // this uses h2 by default but change to match your database
    private static String databaseUrl = "jdbc:h2:mem:robocup";
    private static String databaseDriver = "org.h2.Driver";
    private static String databaseUser = "";
    private static String databasePassword = "";
    private static boolean tableDrop = false;
    private static boolean tableCreate = false;
    private static ConnectionSource connectionSource;
    private static Connection connection;
    private static ListenerHandler listenerHandler;

    /**
     * Will return an ORM object or null by connection error
     * @return ORM object
     */
    public ORM build(ListenerHandler listenerHandler){
        ORM orm = null;
        try {
            ORMBuilder.listenerHandler = listenerHandler;
            Properties prop = Propertieloader.getInstance().getProperties();
            init(prop);
            // instantiate the DAO
            Dao<Ball, String> ballDao = DaoManager.createDao(connectionSource, Ball.class);
            Dao<Player, String> playerDao = DaoManager.createDao(connectionSource, Player.class);
            Dao<Show, String> showDao = DaoManager.createDao(connectionSource, Show.class);
            Dao<PlayerType, String> playerType = DaoManager.createDao(connectionSource, PlayerType.class);

            if(tableDrop){
                TableUtils.dropTable(connectionSource, Ball.class, false);
                TableUtils.dropTable(connectionSource, Player.class, false);
                TableUtils.dropTable(connectionSource, Show.class, false);
                TableUtils.dropTable(connectionSource, PlayerType.class, false);
            }
            if(tableCreate) {
                TableUtils.createTableIfNotExists(connectionSource, Ball.class);
                TableUtils.createTableIfNotExists(connectionSource, Player.class);
                TableUtils.createTableIfNotExists(connectionSource, Show.class);
                TableUtils.createTableIfNotExists(connectionSource, PlayerType.class);
            }

            if(prop.getProperty("database.gis").equals("true")){
                createGis(prop); // creating Gis information
            }

            // webserver for db on http://localhost:8082/ only for H2
            if(databaseDriver.contains("h2")){
                final String[] commandArgs = new String[] {"-webAllowOthers"};

                org.h2.tools.Server server = org.h2.tools.Server.createWebServer(commandArgs).start();

                listenerHandler.addListener(new ApplicationActionListener() {
                    @Override
                    public void onAction(ApplicationAction action) {
                        if (action.getEvent() == ApplicationAction.Event.STOP)
                            server.stop();
                    }
                });
            }

            orm = new RobocupOrm(connection, showDao, ballDao, playerDao, playerType);
        } catch (SQLException ex) {
            LOGGER.error("Error on Connect to the Database and Create The Tables", null , ex);
        }
        return orm;
    }

    private static void init(Properties prop){
        try {
            if(connectionSource == null) {
                // create a connection source to our database
                databaseUrl = prop.getProperty("database.url");
                databaseDriver = prop.getProperty("database.driver");
                databaseUser = prop.getProperty("database.user");
                databasePassword = prop.getProperty("database.password");
                String databaseTable = prop.getProperty("database.table");
                if(databaseTable.contains("create")) tableCreate = true;
                if(databaseTable.contains("drop")) tableDrop = true;
                Class.forName(databaseDriver);
                if(databaseUser.trim() != ""){
                    connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
                    connectionSource = new JdbcConnectionSource(databaseUrl, databaseUser, databasePassword);
                } else {
                    connection = DriverManager.getConnection(databaseUrl);
                    connectionSource = new JdbcConnectionSource(databaseUrl);
                }

            }
        } catch (SQLException ex) {
            LOGGER.error("Error on Connect to the Database and Create The Tables", null, ex);
        } catch (ClassNotFoundException ex) {
            LOGGER.error("{0} not found", databaseDriver, ex);
        }
    }


    private static void createGis(Properties prop) {
        LOGGER.info("creating gis table and trigger started");
        String gisFile = "conf/h2gis.sql";
        try {
            String sql = "";
            if(databaseDriver.contains("h2")){
                connection = SFSUtilities.wrapConnection(connection);
                CreateSpatialExtension.initSpatialExtension(connection);
            }
            if(tableDrop){
                sql = prop.getProperty("database.gis.drop");
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            }
            if(tableCreate){
                sql = prop.getProperty("database.gis.create");
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            }
            LOGGER.info("creating gis table and trigger finished");
        } catch (SQLException ex) {
            LOGGER.error("failed creating gis table and trigger", null , ex);
        }
    }

}
