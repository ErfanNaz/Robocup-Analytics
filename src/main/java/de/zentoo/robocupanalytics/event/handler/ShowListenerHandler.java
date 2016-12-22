package de.zentoo.robocupanalytics.event.handler;

import de.zentoo.robocupanalytics.event.action.ShowAction;
import de.zentoo.robocupanalytics.event.listener.ShowActionListener;

/**
 * Created by test on 01.03.15.
 */
public interface ShowListenerHandler extends ActionListenerHandler{

    public void addListener(ShowActionListener listener);

    public void removeListener(ShowActionListener listener);

    public void setAction(ShowAction action);

}
