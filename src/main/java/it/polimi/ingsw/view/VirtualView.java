package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;

import java.util.*;

/**
 * Is a model observer and controller observable
 * Has three functions :
 * 1 - Send the messages receved from the model to controller threw the networck
 * 2 - Send the message from the client to the controller
 * 3 - Take the request messages from the controller to the clients
 */

public class VirtualView extends Observable implements Observer {

    /**
     * Is the user of who is associated to the virtual view
     */
    private String user;
    /**
     * Is the message from the client that has to be send to the controller
     */
    private Event toController;
    /**
     * Is the message from the controller that has to be send to the client
     */
    private Event toRemoteView;
    /**
     * Is the queue of update messages from the model that has to be send to the clients
     */
    private Queue<Event> modelUpdateQueue;
    /**
     * Flag to denote if the player associated to the virtual view is connect
     */
    private boolean playerConnected;

    /**
     * Every player has a his own view
     *
     * @param user is the username of the player associated with teh virtual view
     */
    public VirtualView(String user) {
        this.user = user;
        playerConnected = true;
        modelUpdateQueue = new LinkedList<>();
    }

    /**
     * Getter method
     * @return the message that has to go to the client
     */
    public Event getToRemoteView() {
        return toRemoteView;
    }

    /**
     * Getter method
     * @return the update messages queue
     */
    public Queue<Event> getModelUpdateQueue() {
        return modelUpdateQueue;
    }

    /**
     * this method is called by the server when a message arrives from Remote view;
     * this means that message should be send to controller.
     * Remember: VIRTUAL_VIEW IS AN OBSERVABLE FROM THE CONTROLLER
     */
    public void toController(Event message) {
        setToController(message);
        setChanged();
        notifyObservers(this.getToController());
    }

    /**
     * this method is called from the model through notifyObservers() defined in the model.
     * this method set event that should be send to RemoteView.
     * Remember: VIRTUAL_VIEW IS AN OBSERVER OF THE MODEL
     *
     * @param o Is the observable
     * @param arg message
     */
    @Override
    public void update(Observable o, Object arg) {
        if (((Event) arg).getUser().equals("BROADCAST") || ((Event) arg).getUser().equals(user)) {
            modelUpdateQueue.offer((Event) arg);
        }
    }


    /**
     * Set the message from the server to the controller
     * @param toController is the message
     */
    private void setToController(Event toController) {
        this.toController = toController;
    }

    /**
     * Getter method
     * @return the message to the controller
     */
    private Event getToController() {
        return toController;
    }

    /**
     * Method called by the controller to set the request messages to send to the client
     * @param message is the message for the client
     */
    public void callRemoteView(ControllerViewEvent message) {
        toRemoteView = message;
    }

    /**
     * Set the player connected
     */
    public void setPlayerConnected() {
        playerConnected = true;
    }

    /**
     * Set the player disconnected
     */
    public void setPlayerDisonnected() {
        playerConnected = false;
    }
}
