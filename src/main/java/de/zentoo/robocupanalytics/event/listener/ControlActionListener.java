package de.zentoo.robocupanalytics.event.listener;

import de.zentoo.robocupanalytics.event.action.Action;
import de.zentoo.robocupanalytics.event.action.ControlAction;

/**
 *
 * @author test
 */
public interface ControlActionListener extends ActionListener {

    @Override
    default void onAction(Action action){}

    void onAction(ControlAction action);

}
