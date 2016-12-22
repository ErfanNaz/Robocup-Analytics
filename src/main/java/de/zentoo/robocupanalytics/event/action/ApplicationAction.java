package de.zentoo.robocupanalytics.event.action;

/**
 * Created by Erfan Nazmehr on 29.06.15.
 */
public interface ApplicationAction extends Action {

    ActionType type = ActionType.APPLICATION;

    enum Event{
        START,
        STOP
    }

    default Event getEvent(){
        return Event.STOP;
    }

}
