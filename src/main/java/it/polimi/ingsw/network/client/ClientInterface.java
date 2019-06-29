package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.NetworkHandler;

import java.net.ConnectException;

/**
 * @author Francesco Masciulli
 * This interface will be implemented accordly with user's Network choice
 */
public interface ClientInterface extends NetworkHandler {
    void connectClient() throws ConnectException;
    void disconnectClient() throws Exception;
    void changeUsername(String user, String newUsername);
    void setServerPort(int serverPort);
    void reconnectClient();
}
