package de.zentoo.robocupanalytics.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Erfan on 13.02.15.
 */
public class Propertieloader {

    private static final Logger LOGGER = LoggerFactory.getLogger(Propertieloader.class);

    private static Propertieloader instance = new Propertieloader();

    private Properties properties = null;

    private Propertieloader(){
        Properties propertie = new Properties();
        InputStream defaultPropertyStream = getResourceAsStream("conf/robocup.properties");
        if (defaultPropertyStream != null) {
            try {
                propertie.load(defaultPropertyStream);
            } catch (IOException ex) {
                LOGGER.error("Configuration file with robocup.properties is not valide", null, ex);
            }
        } else {
            LOGGER.error("robocup.properties not found in conf");
        }
        properties = propertie;
    }

    public static Propertieloader getInstance(){
        return instance;
    }

    /**
     * loading propertie file from conf/robocup.properties
     * @return Properties loaded from conf/robocup.properties
     */
    public Properties getProperties(){
        return properties;
    }

    /**
     * Searches for a resource and returns the InputStream from the resource or
     * null if not found.
     *
     * @param filename The name of the resource
     * @return InputStream of the searched resource. Returns null if not found.
     */
    public InputStream getResourceAsStream(String filename) {
        String stripped = filename.startsWith("/") ? filename.substring(1) : filename;
        InputStream stream = null;
        ClassLoader classLoader = Propertieloader.class.getClassLoader();
        if (classLoader != null) {
            stream = classLoader.getResourceAsStream(stripped);
        }
        if (stream == null) {
            stream = Propertieloader.class.getResourceAsStream(filename);
        }
        return stream;
    }

}
