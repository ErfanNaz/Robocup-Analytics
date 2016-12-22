package de.zentoo.robocupanalytics;

import java.util.*;

import de.zentoo.robocupanalytics.event.ListenerHandler;
import de.zentoo.robocupanalytics.orm.Builder;
import de.zentoo.robocupanalytics.orm.ORM;
import de.zentoo.robocupanalytics.orm.ORMBuilder;
import de.zentoo.robocupanalytics.plugin.PluginBuilder;
import de.zentoo.robocupanalytics.plugin.Pluginload;
import de.zentoo.robocupanalytics.service.PlayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The context of RobocupAnalytics
 * 
 * @author Erfan
 */
public class DefaultContext implements Context{

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultContext.class);
    
    private ORM orm;

    private ListenerHandler listenerHandler;

    private List<Pluginload> plugins = new ArrayList<>();

    private PlayService playService;
    
    private DefaultContext() {
        listenerHandler = new ListenerHandler();
        Builder ormBuilder = new ORMBuilder();
        orm = ormBuilder.build(listenerHandler);
        playService = new PlayService(this);
        de.zentoo.robocupanalytics.plugin.Builder pluginBuilder = new PluginBuilder();
        plugins = pluginBuilder.build(this);
    }
    
    public synchronized static DefaultContext getInstance() {
        return ContextHolder.INSTANCE;
    }
    
    private static class ContextHolder {
        private static final DefaultContext INSTANCE = new DefaultContext();
    }

    @Override
    public ORM getOrm() {
        return orm;
    }

    @Override
    public final List<Pluginload> getPlugins() {
        return plugins;
    }

    @Override
    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }

    @Override
    public PlayService getPlayService() {
        return playService;
    }
}
