package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.viewcontrollerevent.DisconnectedEvent;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.utils.CustomLogger;


import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * This class implements a SocketThread for each client connected with this technology
 *
 * @author Francesco Masciulli
 */
public class SocketServerThread extends Thread implements NetworkHandler {
    /**
     * Is the connected client Socket
     */
    private Socket client;
    /**
     * is the connecte client username
     */
    private String clientUser;
    /**
     * Is the SocketThread input stream
     */
    private ObjectInputStream inputStream;
    /**
     * Is the SocketThread output stream
     */
    private ObjectOutputStream outputStream;
    /**
     * Is the last set message
     */
    private Event currMessage;
    /**
     * This boolean is true while the client is connected
     */
    private boolean connected;

    /**
     * Constructor:
     * create a socket thread that handle the communication with a socket client and connect their input and output streams.
     *
     * @param socket is the client socket
     */
    SocketServerThread(Socket socket) {
        this.client = socket;
        connected = true;
        try {
            outputStream = new ObjectOutputStream(client.getOutputStream());
            inputStream = new ObjectInputStream(client.getInputStream());
            while (clientUser == null) {
                clientUser = inputStream.readUTF();
            }

        } catch (IOException e) {
            CustomLogger.logException(e);
        }
    }

    /**
     * Is the Thread's run implementation:
     * while the client is connected, it continues with the messages listening
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            while (connected) {
                currMessage = listenMessage();
            }
            setPriority(2);
            interrupt();
        }

    }

    /**
     * Setter method: seth the given client username
     *
     * @param clientUser is the username
     */
    void setClientUser(String clientUser) {
        this.clientUser = clientUser;
    }

    /**
     * Getter method
     *
     * @return true if the client is connected
     */
    public boolean isConnected() {
        try {
            if (connected) {
                join(200);
            }
        } catch (InterruptedException e) {
            interrupt();
        }

        return connected;
    }

    /**
     * Handle the thread's disconnection process
     */
    synchronized void disconnect() {
        currMessage = new DisconnectedEvent(getClientUser());
        connected = false;
    }

    /**
     * Getter method
     *
     * @return the last listened Event
     */
     Event getCurrMessage() {
        return currMessage;
    }

    /**
     * Getter method
     *
     * @return the username of the client connected with this thread
     */
    String getClientUser() {
        return clientUser;
    }

    /**
     * Getter method
     *
     * @return the connected Socket
     */
    public Socket getClient() {
        return client;
    }


    /**
     * This method, which is called from a client disconnection, kill the SocketThread
     */
    synchronized void kill() {
        interrupt();
    }

    /**
     * Write the message on the client's socket input stream
     *
     * @param message is the message that must be sent
     */
    @Override
    public void sendMessage(Event message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (Exception e) {
            CustomLogger.logException(e);
            disconnect();
        }

    }

    /**
     * Wait for a message wrote on its input stream
     *
     * @return
     */
    @Override
    public Event listenMessage() {

        try {
            return (Event) inputStream.readObject();
        } catch (SocketException | EOFException socketClosed) {
            disconnect();
            try {
                join();
            } catch (InterruptedException e) {
                interrupt();
            }
        } catch (Exception e) {
            CustomLogger.logException(e);
        }
        return currMessage;
    }

    void resetMessage() {
        currMessage = null;
    }

}
