package it.polimi.ingsw.network.server;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.client.ClientInterface;

import java.util.ArrayList;

public interface ServerInterface extends NetworkHandler {
    void runServer();
    void acceptClient();
    ArrayList<String> getClientList();
    void sendBroadcast(Event message);
    void shutDown();
    void gameCouldStart();
}
