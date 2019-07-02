package it.polimi.ingsw.network.server;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.NetworkHandler;


import java.util.ArrayList;

/**
 * Server Class interface: declare the methods used by Server and Lobby to handle, via RMI/Socket, the clients requests
 */
public interface ServerInterface extends NetworkHandler {
    /**
     * Start the RMI/Socket server
     */
    void runServer();

    /**
     * Accept and register a client on the respectively server implementation
     */
    void acceptClient();

    /**
     * Ping each one of the connected client and, if disconnected, retrieve the Disconnection event
     * @return an ArrayList of DisconnectionEvent
     */
    ArrayList<Event> ping();

    /**
     * Pseudo-Getter method:
     * @return the username of each client connected on the implementation
     */
    ArrayList<String> getClientList();

    /**
     * handle the client, identified by user, disconnection from the server implementation
     * @param user is the disconnecting client's username
     * @return a DisconnectedEvent
     */
    Event disconnectClient(String user);

    /**
     * Send to each client, connected to this implementation, the message given
     * @param message is the message that must be sent
     */
    void sendBroadcast(Event message);

    /**
     * shut-down the server
     */
    void shutDown();

    /**
     * Set the game state
     */
    void gameCouldStart();

    /**
     * after an Username Modification request, update the username of the respectively client
     * @param username is the old username
     * @param newUser is the new username
     */
    void updateUsername(String username, String newUser);

    /**
     * Getter method:
     * @return the port on which the implementation is connected
     */
    int getPort();
}
