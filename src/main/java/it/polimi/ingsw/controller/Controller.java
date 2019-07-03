package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;
import it.polimi.ingsw.view.VirtualView;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Controller class: is the interface between the virtual view and the controller's class that manage the game logic
 * Receive the updates from the virtual view perform the correlated methods and send to the virtual view the request message for the clients
 */
public class Controller implements Observer {
    private GameManager gameManager;
    private Map<String, VirtualView> usersVirtualView;

    public Controller(Map<String, VirtualView> usersVirtualView, int mapChoice) {
        this.usersVirtualView = usersVirtualView;
        createGameManager(mapChoice);
        gameManager.characterSelect();
    }

    /**
     * Receive the notify from the virtual view and perform the message's actions
     *
     * @param virtualView is the class which notify the messages
     * @param message     is the event notified
     */
    @Override
    public void update(Observable virtualView, Object message) {
        ((ViewControllerEvent) message).performAction(this);
    }

    /**
     * Method called at the instantiation of the controller to create the game's GameManager.
     * It create the game map, according with the following codification:
     * 0: small map
     * 1: big left, small right
     * 2: small left, big right
     * 3: big map
     *
     * @param mapChoice is the codification of the map chosen for the game
     */
    public void createGameManager(int mapChoice) {
        gameManager = new GameManager(this, mapChoice);
    }

    /**
     * This method is used to send to the virtual views the request messages generated by the controller's class to allowed players to make decisions
     *
     * @param message is the request message sent to the virtual views
     */
    void callView(ControllerViewEvent message) {
        usersVirtualView.get(message.getUser()).callRemoteView(message);
    }


    /*
     * Getter and setter methods
     */

    /**
     * Getter method
     *
     * @return the mapping between the players username and their virtual view
     */
    public Map<String, VirtualView> getUsersVirtualView() {
        return usersVirtualView;
    }

    /**
     * Getter method
     *
     * @return the game manager
     */
    public GameManager getGameManager() {
        return gameManager;
    }
}