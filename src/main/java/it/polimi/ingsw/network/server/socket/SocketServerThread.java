package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetworkHandler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketServerThread extends Thread implements NetworkHandler {
    private Socket client;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void sendMessage(Event message) {

    }

    @Override
    public Event listenMessage() {
        Event message = null;
        return message;
    }

}
