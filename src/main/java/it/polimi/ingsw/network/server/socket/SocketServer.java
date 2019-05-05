package it.polimi.ingsw.network.server.socket;

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
    public void acceptClient() {

    }

    @Override
    public void sendBroadcast() {

    }

    @Override
    public void shutDown() {

    }
}
