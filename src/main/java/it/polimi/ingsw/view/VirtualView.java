package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;

import java.util.*;

/**
 * todo O eventi girati al controller oppure girati alla remote view
 *
 * E' observer del model e observable del controller:
 * 1) Inoltra gli eventi ricevuti dal model attraverso la rete ( alla view del client);
 *
 * 2) Riceve eventi dalla view del client attraverso la rete e li inoltra al controller.
 *
 */

public class VirtualView extends Observable implements Observer{


    private String user;
    private Event toController;
    private Event toRemoteView;
    private Queue<Event> modelUpdateQueue;
    private boolean playerConnected;

    /**
     * Every player has a his own view
     * @param user
     */
    public VirtualView(String user)
    {
        this.user = user;
        playerConnected = true;
        modelUpdateQueue = new LinkedList<>();
    }

    /**
     *
     * @return
     */
    public Event getToRemoteView() {
        return toRemoteView;
    }

    public Queue<Event> getModelUpdateQueue() {
        return modelUpdateQueue;
    }

    /**
     * this method is called by the server when a message arrives from Remote view;
     * this means that message should be send to controller.
     * Remember: VIRTUAL_VIEW IS AN OBSERVABLE FROM THE CONTROLLER
     */
    public void toController(Event message){
        setToController(message);
        setChanged();
        notifyObservers(this.getToController());
    }

    /**
     * this method is called from the model through notifyObservers() defined in the model.
     * this method set event that should be send to RemoteView.
     * Remember: VIRTUAL_VIEW IS AN OBSERVER OF THE MODEL
     * @param o
     * @param arg message
     */
    @Override
    public void update(Observable o, Object arg) {
        if(((Event)arg).getUser().equals("BROADCAST")||((Event) arg).getUser().equals(user)) {
            modelUpdateQueue.offer((Event) arg);
        }
    }



    /**
     *
     * @param toController
     */
    public void setToController(Event toController) {
        this.toController = toController;
    }

    /**
     *
     * @return
     */
    public Event getToController() {
        return toController;
    }
    
    public void callRemoteView(ControllerViewEvent message){
        toRemoteView = message;
    }

    public void setPlayerConnected(){
        playerConnected = true;
    }

    public void setPlayerDisonnected(){
        playerConnected = false;
    }
}
