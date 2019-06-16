package it.polimi.ingsw.network.server;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.client.ClientInterface;

import java.util.ArrayList;

public interface ServerInterface extends NetworkHandler {
    void runServer();
    void acceptClient();
    ArrayList<Event> ping();
    ArrayList<String> getClientList();
    Event disconnectClient(String user);
    void sendBroadcast(Event message);
    void shutDown();
    void gameCouldStart();
    void updateUsername(String username, String newUser);
    int getPort();
}
