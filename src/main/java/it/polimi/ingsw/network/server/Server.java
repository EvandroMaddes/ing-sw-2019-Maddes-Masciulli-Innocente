package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.ViewInterface;

import java.util.ArrayList;
import java.util.Map;

public class Server {
    private ViewInterface virtualView;
    private ServerInterface serverRMI;
    private ServerInterface serverSocket;
    private ArrayList<Map<String, Character>> users;

    public void startServer(){

    }

    public void shutDown(){

    }
}
