package de.zentoo.robocupanalytics.entity;

import com.j256.ormlite.field.DatabaseField;
import javafx.scene.shape.Circle;

/**
 * The player with all its attributes for the current show.
 * 
 * @author Erfan Nazmehr
 */
public class Player {

    @DatabaseField(unique = true, generatedId = true)
    private int id;

    @DatabaseField private char side;
    @DatabaseField private int unum;
    @DatabaseField private int type;
    @DatabaseField private String state;
    @DatabaseField private double x;
    @DatabaseField private double y;
    @DatabaseField private double vx;
    @DatabaseField private double vy;
    @DatabaseField private double body;
    @DatabaseField private double neck;
    
    //optional
    @DatabaseField private double point_x;
    @DatabaseField private double point_y;
    
    //view
    @DatabaseField private char view_quality;
    @DatabaseField private double view_width;
    
    //info
    @DatabaseField private double info_stamina;
    @DatabaseField private double info_effort;
    @DatabaseField private double info_recovery;
    @DatabaseField private double info_capacity;
    
    //focus
    @DatabaseField private char focus_side;
    @DatabaseField private int focus_unum;
    
    //counter
    @DatabaseField private int count_kick;
    @DatabaseField private int count_dash;
    @DatabaseField private int count_turn;
    @DatabaseField private int count_catch;
    @DatabaseField private int count_move;
    @DatabaseField private int count_turn_neck;
    @DatabaseField private int count_change_view;
    @DatabaseField private int count_say;
    @DatabaseField private int count_tackle;
    @DatabaseField private int count_pointto;
    @DatabaseField private int count_attentionto;
    
    @DatabaseField private String style;
    
    @DatabaseField(foreign = true,index = true, foreignAutoRefresh = true)
    private Show show;
    
    /**
     * No arg constructor for the ORM
     */
    public Player(){}

    public int getId(){
        return id;
    }

    public char getSide() {
        return side;
    }

    public void setSide(char side) {
        this.side = side;
    }

    public int getUnum() {
        return unum;
    }

    public void setUnum(int unum) {
        this.unum = unum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public double getBody() {
        return body;
    }

    public void setBody(double body) {
        this.body = body;
    }

    public double getNeck() {
        return neck;
    }

    public void setNeck(double neck) {
        this.neck = neck;
    }

    public double getPoint_x() {
        return point_x;
    }

    public void setPoint_x(double point_x) {
        this.point_x = point_x;
    }

    public double getPoint_y() {
        return point_y;
    }

    public void setPoint_y(double point_y) {
        this.point_y = point_y;
    }

    public char getView_quality() {
        return view_quality;
    }

    public void setView_quality(char view_quality) {
        this.view_quality = view_quality;
    }

    public double getView_width() {
        return view_width;
    }

    public void setView_width(double view_width) {
        this.view_width = view_width;
    }

    public double getInfo_stamina() {
        return info_stamina;
    }

    public void setInfo_stamina(double info_stamina) {
        this.info_stamina = info_stamina;
    }

    public double getInfo_effort() {
        return info_effort;
    }

    public void setInfo_effort(double info_effort) {
        this.info_effort = info_effort;
    }

    public double getInfo_recovery() {
        return info_recovery;
    }

    public void setInfo_recovery(double info_recovery) {
        this.info_recovery = info_recovery;
    }

    public double getInfo_capacity() {
        return info_capacity;
    }

    public void setInfo_capacity(double info_capacity) {
        this.info_capacity = info_capacity;
    }

    public char getFocus_side() {
        return focus_side;
    }

    public void setFocus_side(char focus_side) {
        this.focus_side = focus_side;
    }

    public int getFocus_unum() {
        return focus_unum;
    }

    public void setFocus_unum(int focus_unum) {
        this.focus_unum = focus_unum;
    }

    public int getCount_kick() {
        return count_kick;
    }

    public void setCount_kick(int count_kick) {
        this.count_kick = count_kick;
    }

    public int getCount_dash() {
        return count_dash;
    }

    public void setCount_dash(int count_dash) {
        this.count_dash = count_dash;
    }

    public int getCount_turn() {
        return count_turn;
    }

    public void setCount_turn(int count_turn) {
        this.count_turn = count_turn;
    }

    public int getCount_catch() {
        return count_catch;
    }

    public void setCount_catch(int count_catch) {
        this.count_catch = count_catch;
    }

    public int getCount_move() {
        return count_move;
    }

    public void setCount_move(int count_move) {
        this.count_move = count_move;
    }

    public int getCount_turn_neck() {
        return count_turn_neck;
    }

    public void setCount_turn_neck(int count_turn_neck) {
        this.count_turn_neck = count_turn_neck;
    }

    public int getCount_change_view() {
        return count_change_view;
    }

    public void setCount_change_view(int count_change_view) {
        this.count_change_view = count_change_view;
    }

    public int getCount_say() {
        return count_say;
    }

    public void setCount_say(int count_say) {
        this.count_say = count_say;
    }

    public int getCount_tackle() {
        return count_tackle;
    }

    public void setCount_tackle(int count_tackle) {
        this.count_tackle = count_tackle;
    }

    public int getCount_pointto() {
        return count_pointto;
    }

    public void setCount_pointto(int count_pointto) {
        this.count_pointto = count_pointto;
    }

    public int getCount_attentionto() {
        return count_attentionto;
    }

    public void setCount_attentionto(int count_attentionto) {
        this.count_attentionto = count_attentionto;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public int getPlayerID(){
        return side * unum;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString(){
        return "player" + unum + side + ":{x:" + x + ",y:" + y + ",vx:" + vx + ",vy:" + vy + ",kick:" + count_kick + "}";
    }
    
}
