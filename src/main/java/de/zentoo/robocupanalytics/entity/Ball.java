package de.zentoo.robocupanalytics.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Ball entity object
 * 
 * @author Erfan
 */
public class Ball {

    @DatabaseField(unique = true,index = true, generatedId = true)
    private int id;

    @DatabaseField(index = true)
    private int time;
    @DatabaseField private double x;
    @DatabaseField private double y;
    @DatabaseField private double vx;
    @DatabaseField private double vy;
    @DatabaseField private String style;

    /**
     * No arg constructor for the ORM
     */
    public Ball(){}
    
    public Ball(int time, double x, double y, double vx, double vy){
        this.time = time;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public int getId(){
        return id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public String toString(){
        return "{x:" + x + ",y:" + y + ",vx:" + vx + ",vy:" + vy + "}"; 
    }
    
}
