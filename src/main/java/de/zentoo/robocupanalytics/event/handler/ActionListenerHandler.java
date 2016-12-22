package de.zentoo.robocupanalytics.event.handler;

import de.zentoo.robocupanalytics.event.action.Action;
import de.zentoo.robocupanalytics.event.listener.ActionListener;

/**
 * Created by test on 01.03.15.
 */
public interface ActionListenerHandler {

    public void addListener(ActionListener listener);

    public void removeListener(ActionListener listener);

    public void setAction(Action action);

}
