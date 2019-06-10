package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private GameManager gameManager;
    private Map<String, VirtualView> usersVirtualView;

    public Controller(Map<String, VirtualView> usersVirtualView, int mapChoice) {
        this.usersVirtualView = usersVirtualView;
        createGameManager(mapChoice);
        gameManager.characterSelect();
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

    public void callView(ControllerViewEvent message){
        if (message.getUser().equals("BROADCAST")){
            for (Player p: getGameManager().getModel().getPlayers()) {
                usersVirtualView.get(p.getUsername()).callRemoteView(message);
            }
        }
        usersVirtualView.get(message.getUser()).callRemoteView(message);
    }

    public Map<String, VirtualView> getUsersVirtualView() {
        return usersVirtualView;
    }
}