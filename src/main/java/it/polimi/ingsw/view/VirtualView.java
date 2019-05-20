package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

/**
 * todo O eventi girati al controller oppure girati alla remote view
 *
 * E' observer del model e observable del controller:
 * 1) Inoltra gli eventi ricevuti dal model attraverso la rete ( alla view del client);
 *
 * 2) Riceve eventi dalla view del client attraverso la rete e li inoltra al controller.
 *
 */

public class VirtualView  extends Observable implements Observer{


    private String user;
    private Event toController;
    private Event toRemoteView;

    /**
     * Every player has a his own view
     * @param user
     */
    public VirtualView(String user)
    {
        this.user = user;
    }

    /**
     *
     * @param toRemoteView
     */
    public void setToRemoteView(Event toRemoteView) {
        this.toRemoteView = toRemoteView;
    }

    /**
     *
     * @return
     */
    public Event getToRemoteView() {
        return toRemoteView;
    }



    /**
     * send  a message to remote view
     */
    public void toRemoteView(){

        //todo metodi che dal server inviano al client;
        // essendo interna al server ritorna toRemoteView
        // che è stato girato dal model

    }

    /**
     * this method is called when a message arrives from Remote view;
     * this means that message should be send to controller.
     * Remember: VIRTUAL_VIEW IS AN OBSERVABLE FROM THE CONTROLLER
     */
    public void toController(Event message){
        //todo metodi che dal server ricevono il messaggio;
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

        setToRemoteView((Event)arg);

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



}
