package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
    private GameManager gameManager;
    private Map<String, VirtualView> usersVirtualView;
    private boolean gameOn;

    public Controller(Map<String, VirtualView> usersVirtualView, int mapChoice) {
        this.usersVirtualView = usersVirtualView;
        createGameManager(mapChoice);
        gameManager.characterSelect();
        gameOn = true;
    }

    @Override
    /**
     *
     * @param message is the message that arrives from view with the map and mode choice
     */
    public void update(Observable virtualView, Object message){
       ((ViewControllerEvent)message).performAction(this);
    }

    public void createGameManager(int mapChoice){
        gameManager = new GameManager(this, mapChoice);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public boolean isGameOn() {
        return gameOn;
    }

    public void callView(ControllerViewEvent message){
        usersVirtualView.get(message.getUser()).callRemoteView(message);
    }

    public Map<String, VirtualView> getUsersVirtualView() {
        return usersVirtualView;
    }

    public void gameOff() {
        gameOn = false;
    }
}