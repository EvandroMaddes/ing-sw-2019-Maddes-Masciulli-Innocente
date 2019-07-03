package it.polimi.ingsw.utils;

/**
 * Class containing the net configuration info.
 * @author Francesco Masciulli
 */
public class NetConfiguration {
    /**
     * Is the Server's RMIServer port number
     */
    public static final int RMISERVERPORTNUMBER = 1099;
    /**
     *Is the Server's SocketServer port number
     */
    public static final int SOCKETSERVERPORTNUMBER = 2002;

    //The timers time is in SECONDS, setted on the Server starting by command line
    /**
     * Default gameTimer value, in seconds; is set during Server initialization
     */
    private static int startGameTimer = 5;
    /**
     * Default roundTimer value, in seconds; is set during Server initialization
     */
    private static int roundTimer = 30;

    /**
     * Connection Type implemented, each client could chose one of these
     */
    public enum ConnectionType {
        RMI,
        SOCKET;
    }

    /**
     * Setter method: change gameTimer value;
     * @param startGameTimer is the timer value [seconds]
     */
    public static void setStartGameTimer(int startGameTimer) {
        NetConfiguration.startGameTimer = startGameTimer;
    }

    /**
     * Setter method: change roundTimer value;
     * @param roundTimer is the timer value [seconds]
     */
    public static void setRoundTimer(int roundTimer) {
        NetConfiguration.roundTimer = roundTimer;
    }

    /**
     * Getter method:
     * @return the startGameTimer value
     */
    public static int getStartGameTimer() {
        return startGameTimer;
    }

    /**
     * Getter method:
     * @return the roundTimer value
     */
    public static int getRoundTimer() {
        return roundTimer;
    }
}
