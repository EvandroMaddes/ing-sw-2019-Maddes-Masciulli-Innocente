package it.polimi.ingsw.network.server;

public interface ServerInterface {
    void runServer();
    void acceptClient();
    void sendBroadcast();
    void shutDown();
}
