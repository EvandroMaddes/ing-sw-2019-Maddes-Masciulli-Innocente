package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.ViewInterface;

/**
 * @author Francesco Masciulli
 * It's the Client main class, it will start a connection to the server and handle the game
 */
public class Client {

    private ViewInterface viewImplementation;
    private ClientInterface clientImplementation;
    private String user;

    /**
     * it will start the clientImplementation requested by the user
     * @param connectionType is the chosen network implementation
     */
    public void startClient(String connectionType){

    }

}
