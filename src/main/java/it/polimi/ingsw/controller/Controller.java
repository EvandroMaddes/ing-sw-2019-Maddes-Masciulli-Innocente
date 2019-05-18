package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private GameManager gameManager;


    @Override
    /**
     *
     * @param message is the message that arrives from view with the map and mode choice
     */
    public void update(Observable virtualView, Object message){
       ((ViewControllerEvent)message).performAction(this);
    }

    public void createGameManager(int mapChoice){
        gameManager = new GameManager(mapChoice);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public static void callView(ControllerViewEvent message){
        // TODO: 2019-05-18   
    }
}

