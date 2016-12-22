package de.zentoo.robocupanalytics.service;

import de.zentoo.robocupanalytics.Context;
import de.zentoo.robocupanalytics.entity.PlayerType;
import de.zentoo.robocupanalytics.entity.Show;
import de.zentoo.robocupanalytics.event.action.ControlAction;
import de.zentoo.robocupanalytics.event.action.ShowAction;
import de.zentoo.robocupanalytics.event.listener.ControlActionListener;
import de.zentoo.robocupanalytics.event.ListenerHandler;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by test on 01.03.15.
 */
public class PlayService implements ControlActionListener{

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayService.class);

    ListenerHandler listenerHandler;

    private List<Show> showList = new ArrayList<>();
    private List<PlayerType> playerTypeList = new ArrayList<>();

    private int nextTime = 0;

    private Show currentShow = null;

    private Timer timer;

    private PlayTask playTask;

    private boolean isRunning = false;

    public PlayService(Context context){
        listenerHandler = context.getListenerHandler();
        listenerHandler.addListener(this);
    }

    private void play(){
        if(nextTime >= showList.size() || isRunning) return;
        timer = new Timer();
        isRunning = true;
        playTask = new PlayTask();
        timer.scheduleAtFixedRate(playTask, 0, 100);
    }

    private void stop(){
        if(timer != null) timer.cancel();
        isRunning = false;
        listenerHandler.setAction(new ControlAction() {
            @Override
            public Event getEvent() {
                return Event.STOP;
            }

            @Override
            public String getSenderID() {
                return PlayService.class.getName();
            }
        });
    }

    public List<Show> getShowList() {
        return showList;
    }

    public void setShowList(List<Show> showList) {
        stop();
        nextTime = 0;
        if(showList == null){
            this.showList = new ArrayList<>();
        } else {
            this.showList = showList;
        }
    }

    public void addShow(Show show){
        showList.add(show);
    }

    public List<PlayerType> getPlayerTypeList() {
        return playerTypeList;
    }

    public void setPlayerTypeList(List<PlayerType> playerTypeList) {
        if(playerTypeList == null){
            this.playerTypeList = new ArrayList<>();
        } else {
            this.playerTypeList = playerTypeList;
        }
    }

    public void addPlayerType(PlayerType playerType){
        playerTypeList.add(playerType);
    }

    @Override
    public void onAction(ControlAction action) {
        switch (action.getEvent()){
            case STOP: {
                if(action.getSenderID() != PlayService.class.getName())
                stop();
                break;
            }
            case PLAY: {
                play();
                break;
            }
            case FORWARD: {
                nextStep();
                break;
            }
            case FASTBACK:{
                nextTime = 0;
                break;
            }
            case BACK: {
                backStep();
                break;
            }
            case FASTFORWARD: {
                nextTime = showList.size() -2;
                nextStep();
                break;
            }
            case JUMPTO: {
                nextTime = action.getTime() -2;
                nextStep();
                break;
            }
        }
    }

    private void backStep(){
        if(nextTime > 0){
            currentShow = showList.get(--nextTime);
            listenerHandler.setAction(new ShowAction() {
                @Override
                public Show getShow() {
                    return currentShow;
                }
            });
        }
    }

    private void nextStep(){
        if(nextTime < 0 || nextTime >= showList.size() -1){
            stop();
        } else {
            int i = nextTime;
            boolean founded = false;
            while(i < showList.size() && !founded){
                Show nextShow = showList.get(i++);
                if(nextShow.getTime() >= nextTime){
                    currentShow = showList.get(i);
                    founded = true;
                }
            }
            nextTime++;
            listenerHandler.setAction(new ShowAction() {
                @Override
                public Show getShow() {
                    return currentShow;
                }
            });
        }
    }

    private class PlayTask extends TimerTask{
        @Override
        public void run() {
            Platform.runLater(() -> {
                nextStep();
            });
        }
    }
}
