package de.zentoo.robocupanalytics.event.handler;

import de.zentoo.robocupanalytics.event.action.ApplicationAction;
import de.zentoo.robocupanalytics.event.listener.ApplicationActionListener;

/**
 * Created by Erfan Nazmehr on 29.06.15.
 */
public interface ApplicationListenerHandler extends ActionListenerHandler{

    public void addListener(ApplicationActionListener listener);

    public void removeListener(ApplicationActionListener listener);

    public void setAction(ApplicationAction action);

}
