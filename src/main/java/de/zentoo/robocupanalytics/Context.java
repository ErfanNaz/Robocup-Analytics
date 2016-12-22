package de.zentoo.robocupanalytics;

import de.zentoo.robocupanalytics.event.ListenerHandler;
import de.zentoo.robocupanalytics.orm.ORM;
import de.zentoo.robocupanalytics.plugin.Pluginload;
import de.zentoo.robocupanalytics.service.PlayService;

import java.util.List;

/**
 * Created by test on 18.02.15.
 */
public interface Context {

    public ORM getOrm();

    public List<Pluginload> getPlugins();

    public ListenerHandler getListenerHandler();

    public PlayService getPlayService();

}
