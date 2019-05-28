package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.NetworkHandler;

/**
 * @author Francesco Masciulli
 * This interface will be implemented accordly with user's Network choice
 */
public interface ClientInterface extends NetworkHandler {
    void connectClient();
    void disconnectClient() throws Exception;
    void changeUsername(String user, String newUsername);
}
