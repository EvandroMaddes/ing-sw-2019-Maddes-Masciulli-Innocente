package it.polimi.ingsw.network.server;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.client.ClientInterface;

public interface ServerInterface {
    void runServer();
    void acceptClient(ClientInterface currClient);
    void sendBroadcast(Event message);
    void shutDown();
}
