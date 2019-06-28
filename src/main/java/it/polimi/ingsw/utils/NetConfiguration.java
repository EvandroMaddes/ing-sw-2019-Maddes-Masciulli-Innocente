package it.polimi.ingsw.utils;

public class NetConfiguration {
    public static final int RMISERVERPORTNUMBER = 1099;
    public static final int SOCKETSERVERPORTNUMBER = 2002;
    //The timers time is in SECONDS
    //todo modificare a valori accettabili(Ex 60-120 sec), ora 5 sec per TESTING
    public static  final int STARTGAMETIMER = 5;
    public static final int ROUNDTIMER = 100;
    public enum ConnectionType {
        RMI,
        SOCKET;
    }
}
