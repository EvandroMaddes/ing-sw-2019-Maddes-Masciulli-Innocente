package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.server.ServerInterface;

import java.net.ServerSocket;
import java.util.ArrayList;

public class SocketServer implements ServerInterface {
    private ServerSocket serverSocket;
    private ArrayList<SocketServerThread> socketList;

    @Override
    public void runServer() {

    }


    @Override
    public void acceptClient(ClientInterface currentClient) {

    }

    @Override
    public void sendBroadcast(Event message) {

    }

    @Override
    public void shutDown() {

    }
}
