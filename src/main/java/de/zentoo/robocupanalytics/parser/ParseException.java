package de.zentoo.robocupanalytics.parser;

/**
 * Created by erfan on 02.02.15.
 */
public class ParseException extends Exception{

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
