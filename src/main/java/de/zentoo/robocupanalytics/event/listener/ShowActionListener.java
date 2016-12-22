package de.zentoo.robocupanalytics.event.listener;

import de.zentoo.robocupanalytics.event.action.Action;
import de.zentoo.robocupanalytics.event.action.ShowAction;

/**
 * Created by test on 01.03.15.
 */
public interface ShowActionListener extends ActionListener {

    @Override
    default void onAction(Action action){}

    void onAction(ShowAction action);

}
