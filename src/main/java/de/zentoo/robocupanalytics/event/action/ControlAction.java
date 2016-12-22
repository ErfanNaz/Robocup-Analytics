package de.zentoo.robocupanalytics.event.action;

/**
 * Created by test on 01.03.15.
 */
public interface ControlAction extends Action {

    ActionType type = ActionType.CONTROL;

    enum Event{
        FASTBACK,
        BACK,
        STOP,
        PLAY,
        FORWARD,
        FASTFORWARD,
        JUMPTO
    }

    default Event getEvent(){
        return Event.STOP;
    }

    default int getTime(){
        return 0;
    }

}
