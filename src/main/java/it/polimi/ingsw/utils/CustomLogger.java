package it.polimi.ingsw.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomLogger {





    public static void logException(Exception e){
       Logger.getLogger("ExceptionLogger").log(Level.SEVERE,"Exception:",e);
    }
}
