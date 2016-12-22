package de.zentoo.robocupanalytics.event.action;

/**
 * Created by test on 01.03.15.
 */
public interface Action {

    enum ActionType{
        ACTION, CONTROL, SHOW, APPLICATION
    }

    default String getSenderID(){return "";}

    ActionType type = ActionType.ACTION;

}
