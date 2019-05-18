package it.polimi.ingsw.network;

public class NetConfiguration {
    public static final int RMISERVERPORTNUMBER = 1099;
    public static final int SOCKETSERVERPORTNUMBER = 4002;
    public enum ConnectionType {
        RMI,
        SOCKET;
    }
}
