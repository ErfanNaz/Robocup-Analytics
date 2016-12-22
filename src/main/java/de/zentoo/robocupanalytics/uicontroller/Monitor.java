package de.zentoo.robocupanalytics.uicontroller;

import java.net.URL;
import java.util.*;

import de.zentoo.robocupanalytics.DefaultContext;
import de.zentoo.robocupanalytics.entity.Ball;
import de.zentoo.robocupanalytics.entity.Player;
import de.zentoo.robocupanalytics.entity.Show;
import de.zentoo.robocupanalytics.event.action.ShowAction;
import de.zentoo.robocupanalytics.event.listener.ShowActionListener;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Monitor implements Initializable, ShowActionListener{

    private static final Logger LOGGER = LoggerFactory.getLogger(Monitor.class);

    DefaultContext context;

    Main ui_main;

    //animation millisec
    private Duration duration = Duration.millis(100);
    private double multX = 5.55;
    private double multY = 6.4;

    private Show currentShow;

    @FXML
    private Circle ui_ball;
    @FXML
    private Circle ui_player_L_prototype; // online needed for first design in code Copys are used
    @FXML
    private Circle ui_player_R_prototype; // online needed for first design in code Copys are used

    private Map<String,Circle> ui_playerMap = new HashMap<>();

    private Map<Circle,ParallelTransition> parallelPool;
    private Map<Circle,TranslateTransition> translatePool;
    private Map<Circle,RotateTransition> rotatePool;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ui_main = (Main) rb.getObject("root");
        context = DefaultContext.getInstance();
        context.getListenerHandler().addListener(this);
        parallelPool = new TreeMap<>(new CircleComperator());
        translatePool = new TreeMap<>(new CircleComperator());
        rotatePool = new TreeMap<>(new CircleComperator());
        addToPool(ui_ball);
    }

    @Override
    public void onAction(ShowAction action) {
        updateUI(action.getShow());
    }

    private void updateUI(Show show){
        currentShow = show;
        Ball ball = show.getBall();
        if(ball != null){
            animateBall(ball);
            show.getPlayerlist().stream().forEach(player -> {
                animatePlayer(player);
            });
        }
        parallelPool.values().stream().forEach((tranition) -> {
            tranition.play();
        });
    }

    private void animateBall(Ball ball){
        TranslateTransition ballTransition = translatePool.get(ui_ball);
        ballTransition.setToX(ball.getX() * multX);
        ballTransition.setToY(ball.getY() * multY);
        ui_ball.setStyle(ball.getStyle());
    }

    private void animatePlayer(Player player){
        Circle ui_player = getUi_PlayerFor(player.getSide(),player.getUnum());
        TranslateTransition playerTransition = translatePool.get(ui_player);
        playerTransition.setToX(player.getX() * multX);
        playerTransition.setToY(player.getY() * multY);
        ui_player.setStyle(player.getStyle());
        rotatePool.get(ui_player).setToAngle(player.getBody());
    }

    private Circle getUi_PlayerFor(char side, int id){
        String uuid = side + String.valueOf(id);
        if(ui_playerMap.containsKey(uuid)){
            return ui_playerMap.get(uuid);
        } else {
            return clonePlayer(uuid, side == 'l');
        }
    }

    private Circle clonePlayer(String id, boolean left){
        Circle newPlayer;
        if(left) {
            newPlayer = new Circle(ui_player_L_prototype.getRadius());
            newPlayer.setCenterX(ui_player_L_prototype.getCenterX());
            newPlayer.setCenterY(ui_player_L_prototype.getCenterY());
            newPlayer.setStyle(ui_player_L_prototype.getStyle());
            newPlayer.setScaleX(ui_player_L_prototype.getScaleX());
            newPlayer.setScaleY(ui_player_L_prototype.getScaleY());
            newPlayer.setStroke(ui_player_L_prototype.getStroke());
            newPlayer.setStrokeType(ui_player_L_prototype.getStrokeType());
            newPlayer.getStyleClass().addAll(ui_player_L_prototype.getStyleClass());
        } else {
            newPlayer = new Circle(ui_player_R_prototype.getRadius());
            newPlayer.setCenterX(ui_player_R_prototype.getCenterX());
            newPlayer.setCenterY(ui_player_R_prototype.getCenterY());
            newPlayer.setStyle(ui_player_R_prototype.getStyle());
            newPlayer.setScaleX(ui_player_R_prototype.getScaleX());
            newPlayer.setScaleY(ui_player_R_prototype.getScaleY());
            newPlayer.setStroke(ui_player_R_prototype.getStroke());
            newPlayer.setStrokeType(ui_player_R_prototype.getStrokeType());
            newPlayer.getStyleClass().addAll(ui_player_R_prototype.getStyleClass());
        }
        newPlayer.setId(id);
        ui_main.getPlayground().getChildren().add(newPlayer);
        newPlayer.toBack();
        ui_playerMap.put(id,newPlayer);
        addToPool(newPlayer);
        return newPlayer;
    }

    private void addToPool(Circle shape){
        TranslateTransition translateTransition = new TranslateTransition(duration,shape);
        RotateTransition rotateTransition = new RotateTransition(duration,shape);
        ParallelTransition parallelTransition = new ParallelTransition(shape,translateTransition,rotateTransition);
        parallelPool.put(shape,parallelTransition);
        translatePool.put(shape,translateTransition);
        rotatePool.put(shape,rotateTransition);
    }


    class CircleComperator implements Comparator<Circle> {
        @Override
        public int compare(Circle c1, Circle c2) {
            return c1.getId().compareTo(c2.getId());
        }
    }

}
