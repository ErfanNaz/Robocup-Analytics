package de.zentoo.robocupanalytics.orm;

import com.j256.ormlite.dao.Dao;
import de.zentoo.robocupanalytics.entity.Ball;
import de.zentoo.robocupanalytics.entity.Player;
import de.zentoo.robocupanalytics.entity.PlayerType;
import de.zentoo.robocupanalytics.entity.Show;

import java.sql.Connection;

/**
 * Orm implementation
 * 
 * @author Erfan
 */
public class RobocupOrm implements ORM{
    
    /**
     * Internel Dao objects set by constructor
     */
    private final Dao<Show, String> SHOW_DAO;
    private final Dao<Ball, String> BALL_DAO;
    private final Dao<Player, String> PLAYER_DAO;
    private final Dao<PlayerType, String> PLAYER_TYPE;
    private final Connection CONNECTION;
    
    public RobocupOrm(Connection connection, Dao<Show, String> showDao, Dao<Ball, String> ballDao, Dao<Player, String> playerDao, Dao<PlayerType, String> PlayerType){
        CONNECTION = connection;
        SHOW_DAO = showDao;
        BALL_DAO = ballDao;
        PLAYER_DAO = playerDao;
        PLAYER_TYPE = PlayerType;
    }

    @Override
    public Dao<Show, String> getShowDao() {
        return SHOW_DAO;
    }

    @Override
    public Dao<Ball, String> getBallDao() {
        return BALL_DAO;
    }

    @Override
    public Dao<Player, String> getPlayDao() {
        return PLAYER_DAO;
    }

    @Override
    public Dao<PlayerType, String> getPlayerTypeDao() {
        return PLAYER_TYPE;
    }

    @Override
    public Connection getConnection(){
        return CONNECTION;
    }
}
