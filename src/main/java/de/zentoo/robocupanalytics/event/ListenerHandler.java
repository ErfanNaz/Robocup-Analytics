package de.zentoo.robocupanalytics.event;

import de.zentoo.robocupanalytics.event.action.Action;
import de.zentoo.robocupanalytics.event.action.ApplicationAction;
import de.zentoo.robocupanalytics.event.action.ControlAction;
import de.zentoo.robocupanalytics.event.action.ShowAction;
import de.zentoo.robocupanalytics.event.handler.ActionListenerHandler;
import de.zentoo.robocupanalytics.event.handler.ApplicationListenerHandler;
import de.zentoo.robocupanalytics.event.handler.ControlListenerHandler;
import de.zentoo.robocupanalytics.event.handler.ShowListenerHandler;
import de.zentoo.robocupanalytics.event.listener.ActionListener;
import de.zentoo.robocupanalytics.event.listener.ApplicationActionListener;
import de.zentoo.robocupanalytics.event.listener.ControlActionListener;
import de.zentoo.robocupanalytics.event.listener.ShowActionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erfan Nazmehr on 01.03.15.
 */
public class ListenerHandler implements ActionListenerHandler, ControlListenerHandler, ShowListenerHandler, ApplicationListenerHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(ListenerHandler.class);

    private List<ActionListener> actionListeners = new ArrayList<>();
    private List<ControlActionListener> controlActionListeners = new ArrayList<>();
    private List<ShowActionListener> showActionListeners = new ArrayList<>();
    private List<ApplicationActionListener> applicationActionListeners = new ArrayList<>();

    @Override
    public void addListener(ControlActionListener listener) {
        controlActionListeners.add(listener);
        LOGGER.debug("new listener added : {}", listener.getClass().getName());
    }

    @Override
    public void removeListener(ControlActionListener listener) {
        controlActionListeners.remove(listener);
        LOGGER.debug("{} listener removed", listener.getClass().getName());
    }

    @Override
    public void setAction(ControlAction action) {
        for(ControlActionListener listener:controlActionListeners){
            listener.onAction(action);
        }
        setAction((Action) action);
    }

    @Override
    public void addListener(ShowActionListener listener) {
        showActionListeners.add(listener);
        LOGGER.debug("new listener added : {}", listener.getClass().getName());
    }

    @Override
    public void removeListener(ShowActionListener listener) {
        showActionListeners.remove(listener);
        LOGGER.debug("{} listener removed", listener.getClass().getName());
    }

    @Override
    public void setAction(ShowAction action) {
        for(ShowActionListener listener:showActionListeners){
            listener.onAction(action);
        }
        setAction((Action) action);
    }

    @Override
    public void addListener(ActionListener listener) {
        actionListeners.add(listener);
        LOGGER.debug("new listener added : {}", listener.getClass().getName());
    }

    @Override
    public void removeListener(ActionListener listener) {
        actionListeners.remove(listener);
        LOGGER.debug("{} listener removed", listener.getClass().getName());
    }

    @Override
    public void setAction(Action action) {
        for(ActionListener listener:actionListeners){
            listener.onAction(action);
        }
    }

    @Override
    public void addListener(ApplicationActionListener listener) {
        applicationActionListeners.add(listener);
        LOGGER.debug("new listener added : {}", listener.getClass().getName());
    }

    @Override
    public void removeListener(ApplicationActionListener listener) {
        applicationActionListeners.remove(listener);
        LOGGER.debug("{} listener removed", listener.getClass().getName());
    }

    @Override
    public void setAction(ApplicationAction action) {
        for(ApplicationActionListener listener:applicationActionListeners){
            listener.onAction(action);
        }
    }
}
