package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;

/**
 * @author Francesco Masciulli
 * it's the abstract class, index to the right updated Object (a player or the map)
 */
public abstract class AbstractUpdateEvent extends Event{
    private String updatedResource;

    /**
     * Constructor
     * @param user the Client user
     * @param updatedResource the updated Resource
     */
    public AbstractUpdateEvent(String user, String updatedResource){
        super(user);
        this.updatedResource=updatedResource;
    }
}
