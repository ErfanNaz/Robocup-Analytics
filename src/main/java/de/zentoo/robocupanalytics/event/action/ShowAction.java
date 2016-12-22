package de.zentoo.robocupanalytics.event.action;

import de.zentoo.robocupanalytics.entity.Show;

/**
 * Created by test on 01.03.15.
 */
public interface ShowAction extends Action {

    ActionType type = ActionType.SHOW;

    Show getShow();

}
