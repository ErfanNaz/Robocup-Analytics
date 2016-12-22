package de.zentoo.robocupanalytics.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import java.util.Collection;

/**
 * The show entity holds all player for the current time and the Ball.
 * 
 * @author Erfan Nazmehr
 */
public class Show {
    
    @DatabaseField(unique = true, generatedId = true)
    private int id;

    @DatabaseField(index = true)
    private int time;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Ball ball;
    
    @ForeignCollectionField
    private Collection<Player> playerlist;
    
    /**
     * No arg constructor for the ORM
     */
    public Show(){}
    
    public Show(int time,Ball ball, Collection<Player> playerlist){
        this.time = time;
        this.ball = ball;
        this.playerlist = playerlist;
    };

    public int getId(){
        return id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Collection<Player> getPlayerlist() {
        return playerlist;
    }

    public void setPlayerlist(Collection<Player> playerlist) {
        this.playerlist = playerlist;
    }

    public Player findPlayerByID(int id){
        for(Player player: getPlayerlist()){
            if(player.getPlayerID() == id){
                return player;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        String playerListString = "";
        for(Player p: playerlist){
            playerListString = playerListString + p.toString();
        }
        return String.format("{time:%d, ball:%s, playerlist:{%s}}", time, ball, playerListString );
    }
    
}
