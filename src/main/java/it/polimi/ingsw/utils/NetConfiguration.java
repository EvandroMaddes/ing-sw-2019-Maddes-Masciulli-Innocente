package it.polimi.ingsw.utils;

public class NetConfiguration {
    public static final int RMISERVERPORTNUMBER = 1099;
    public static final int SOCKETSERVERPORTNUMBER = 2002;
    //The timers time is in SECONDS, setted on the Server starting by command line
    public static int startGameTimer = 5;
    public static int roundTimer = 30;
    public enum ConnectionType {
        RMI,
        SOCKET;
    }

    public static void setStartGameTimer(int startGameTimer) {
        NetConfiguration.startGameTimer = startGameTimer;
    }

    public static void setRoundTimer(int roundTimer) {
        NetConfiguration.roundTimer = roundTimer;
    }
}
