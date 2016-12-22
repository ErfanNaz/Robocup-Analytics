package de.zentoo.robocupanalytics.event.handler;

import de.zentoo.robocupanalytics.event.action.ControlAction;
import de.zentoo.robocupanalytics.event.listener.ControlActionListener;

/**
 * Created by test on 17.02.15.
 */
public interface ControlListenerHandler extends ActionListenerHandler {

    public void addListener(ControlActionListener listener);

    public void removeListener(ControlActionListener listener);

    public void setAction(ControlAction action);
}
