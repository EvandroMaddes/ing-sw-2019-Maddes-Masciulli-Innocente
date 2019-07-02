package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.NetworkHandler;

import java.net.ConnectException;

/**
 * This interface will be implemented accordly with user's Network choice
 * @author Francesco Masciulli
 */
public interface ClientInterface extends NetworkHandler {

    /**
     * this method handle the lookup and the creation of the client RemoteRegistry
     * calling the method on the server to add the Remote reference to this last created registry;
     * @throws ConnectException if couldn't connect properly
     */
    void connectClient() throws ConnectException;
    /**
     * this method handle the ClientInterface disconnection
     */
    void disconnectClient() throws Exception;
    /**
     * this method update the username after a modification request
     * @param user is the old username
     * @param newUsername is the updated username
     */
    void changeUsername(String user, String newUsername);
    /**
     * Setter method:
     * set serverPort attribute
     * @param serverPort is the server port number
     */
    void setServerPort(int serverPort);

    /**
     * This method reconnect the client to the requested port, on the same server
     */
    void reconnectClient();

    /**
     * Getter method:
     * @return attribute connected value
     */
    boolean isConnected();
}
