package de.zentoo.robocupanalytics.plugin;

import de.zentoo.robocupanalytics.Context;
import de.zentoo.robocupanalytics.DefaultContext;
import de.zentoo.robocupanalytics.util.Propertieloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Will load the plugins
 * 
 * @author Erfan
 */
public class PluginBuilder implements Builder{

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginBuilder.class);

    private static String pluginPath;
    private static String typeOfFile = ".jar";
    private static String manifestURL = "META-INF/MANIFEST.MF";
    private static Context context;
    
    private final static List<Pluginload> pluginList = new ArrayList<>();

    @Override
    public List<Pluginload> build(Context context) {
        PluginBuilder.context = context;
        Properties properties = Propertieloader.getInstance().getProperties();
        pluginPath = properties.getProperty("plugin.url");
        ArrayList<URL> jarList = new ArrayList<>();
        HashMap<String, String> pluginClassList = new HashMap<>();
        try {
            Files.walk(Paths.get(pluginPath)).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    if (filePath.toString().endsWith(typeOfFile)) {
                        try {
                            jarList.add(filePath.toUri().toURL());
                            String filename = filePath.getFileName().toString();
                            String pluginLoadClass = manifestLoader(filePath);
                            if (!pluginClassList.containsKey(filename) && pluginLoadClass != null) {
                                pluginClassList.put(filename, pluginLoadClass);
                            }
                        } catch (MalformedURLException ex) {
                            LOGGER.error("cant transform file to URL {}", filePath, ex);
                        }
                    }
                }
            });
        } catch (IOException ex) {
            LOGGER.error("the directory {} did not exist", pluginPath, ex);
        }
        URL[] urlList = jarList.toArray(new URL[jarList.size()]);
        invokePlugin(urlList, pluginClassList);
        return pluginList;
    }

    private String manifestLoader(Path path){
        String pluginClass = null;
        String manifestPath = "jar:file:" + path +"!/" + manifestURL;
        try {
            URL url = new URL(manifestPath);
            InputStreamReader manifestReader = new InputStreamReader(url.openStream());
            BufferedReader bufferedReader = new BufferedReader(manifestReader);
            while(bufferedReader.ready()){
                String readLine = bufferedReader.readLine();
                if(readLine.contains("Main-Class:")){
                    pluginClass = readLine.replace("Main-Class:", "").trim();
                }
            }
            manifestReader.close();
            if(pluginClass == null) LOGGER.error("Main-Class not found in {}", manifestPath);
        } catch (FileNotFoundException ex) {
            LOGGER.error("cant find file {}", manifestPath, ex);
        } catch (IOException ex) {
            LOGGER.error("cant find from file {}", manifestPath, ex);
        }
        return pluginClass;
    }

    private void invokePlugin(URL[] urls, HashMap<String,String> pluginClassList){
        if(urls.length > 0) {
            URLClassLoader child = new URLClassLoader(urls, PluginBuilder.class.getClassLoader());
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URLClassLoader urlClassLoader = new URLClassLoader(urls, classLoader);
            Thread.currentThread().setContextClassLoader(urlClassLoader);
            for(String pluginName: pluginClassList.keySet()){
                String className = pluginClassList.get(pluginName);
                try {
                    Class pluginClass = Class.forName(className, true, child);
                    boolean implementPluginload = Pluginload.class.isAssignableFrom(pluginClass);
                    if(implementPluginload){
                        Pluginload plugin = (Pluginload) pluginClass.newInstance();
                        plugin.init(context);
                        pluginList.add(plugin);
                    } else {
                        LOGGER.error("the plugin class {} did not implements Pluginload interface", className);
                    }
                } catch (ClassNotFoundException ex) {
                    LOGGER.error("the class {} can not loaded", className, ex);
                } catch (InstantiationException ex) {
                    LOGGER.error("error on creating a new instance of the class {}", className, ex);
                } catch (IllegalAccessException ex) {
                    LOGGER.error("error on creating a new instance of the class {}", className, ex);
                }
            }
        }
    }

}