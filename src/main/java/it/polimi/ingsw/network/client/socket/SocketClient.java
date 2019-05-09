package it.polimi.ingsw.network.client.socket;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.client.ClientInterface;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Francesco Masciulli
 * This is the Socket Network implementation
 */
public class SocketClient implements NetworkHandler, ClientInterface {

    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    @Override
    public void connectClient(){

    }

    @Override
    public Event listenMessage() {
        Event message = null;
        return message;
    }

    @Override
    public void sendMessage(Event message) {

    }

}
