package de.zentoo.robocupanalytics.orm;

import de.zentoo.robocupanalytics.event.ListenerHandler;

/**
 * Created by Erfan Nazmehr on 07.08.15.
 */
public interface Builder {

    public ORM build(ListenerHandler listenerHandler);

}
