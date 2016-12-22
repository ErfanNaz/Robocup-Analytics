package de.zentoo.robocupanalytics.parser;

import de.zentoo.robocupanalytics.entity.PlayerType;
import de.zentoo.robocupanalytics.entity.Show;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Erfan on 02.02.15.
 */
public abstract class Parser extends Thread{

    private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

    Scanner scanner;

    List<ParseListener> parseListener = new ArrayList<>();

    public void setFile(File file){
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException ex) {
            LOGGER.error(String.format("File {} not found", file.getName()), ex);
        }
    }

    public void setString(String input){
        scanner = new Scanner(input);
    }

    public void setStream(InputStream is){
        scanner = new Scanner(is);
    }

    abstract void parse() throws ParseException;

    public interface ParseListener{

        /**
         * will called by parsing when a new show created.
         * @param show
         */
        public void onNewShow(Show show);

        /**
         * will called by parsing when a new show created.
         * @param playerType
         */
        public void onNewPlayerType(PlayerType playerType);
    }

    /**
     * register a listener for the parser modul
     * @param parseListener
     */
    public void addFinishedListener(ParseListener parseListener){
        this.parseListener.add(parseListener);
    }

    void broadCastShow(Show show){
        for(ParseListener listener : parseListener){
            listener.onNewShow(show);
        }
    }

    void broadCastPlayerType(PlayerType playerType){
        for(ParseListener listener : parseListener){
            listener.onNewPlayerType(playerType);
        }
    }

    @Override
    public void run() {
        super.run();
        try{
            parse();
        } catch (ParseException ex) {
            LOGGER.error("error while parsing", null, ex);
        }
    }

}
