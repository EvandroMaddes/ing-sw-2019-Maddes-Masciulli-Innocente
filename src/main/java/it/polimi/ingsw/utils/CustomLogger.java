package it.polimi.ingsw.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Is a custom Logger that log exceptions
 */
public class CustomLogger {
    /**
     * Log a given exception
     * @param e is the given exception
     */
    public static void logException(Exception e){
       Logger.getLogger("ExceptionLogger").log(Level.SEVERE,"Exception:",e);
    }
}
