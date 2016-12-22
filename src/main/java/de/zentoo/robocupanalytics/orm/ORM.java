package de.zentoo.robocupanalytics.orm;

import com.j256.ormlite.dao.Dao;
import de.zentoo.robocupanalytics.entity.Ball;
import de.zentoo.robocupanalytics.entity.Player;
import de.zentoo.robocupanalytics.entity.PlayerType;
import de.zentoo.robocupanalytics.entity.Show;

import java.sql.Connection;

/**
 * The interface between the entity objects and the ORM
 * 
 * @author Erfan
 */
public interface ORM {

    /**
     * Will return the show ORM object.
     * @return show ORM object
     */
    public Dao<Show, String> getShowDao();

    /**
     * Will return the ball ORM object.
     * @return ball ORM object
     */
    public Dao<Ball, String> getBallDao();

    /**
     * Will return the player ORM object.
     * @return player ORM object
     */
    public Dao<Player, String> getPlayDao();

    /**
     * Will return the player ORM object.
     * @return player ORM object
     */
    public Dao<PlayerType, String> getPlayerTypeDao();

    /**
     * will return the connection to communicate with the DB
     * @return
     */
    public Connection getConnection();
}
