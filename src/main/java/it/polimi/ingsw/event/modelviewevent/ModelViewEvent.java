package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.ClientEvent;

/**
 * Is the Model to View update abstract class
 */
public abstract class ModelViewEvent extends ClientEvent {
    /**
     * Constructor: call the super-class constructor
     *
     * @param user is the addressee user
     */
    public ModelViewEvent(String user) {
        super(user);
    }
}
