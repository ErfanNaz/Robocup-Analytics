package de.zentoo.robocupanalytics.event.listener;

import de.zentoo.robocupanalytics.event.action.Action;
import de.zentoo.robocupanalytics.event.action.ApplicationAction;

/**
 * Created by Erfan Nazmehr on 29.06.15.
 */
public interface ApplicationActionListener extends ActionListener {

    @Override
    default void onAction(Action action){}

    void onAction(ApplicationAction action);

}
