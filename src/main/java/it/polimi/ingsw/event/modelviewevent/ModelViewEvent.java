package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.ClientEvent;

/**
 * Is the Model to View update abstract class
 */
abstract class ModelViewEvent extends ClientEvent {
    /**
     * Constructor: call the super-class constructor
     *
     * @param user is the addressee user
     */
    ModelViewEvent(String user) {
        super(user);
    }
}
