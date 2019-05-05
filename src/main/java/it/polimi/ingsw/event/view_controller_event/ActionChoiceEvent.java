package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.event.Event;

/**
 * @author Francesco Masciulli
 * represent the selected Action after a request
 */
public class ActionChoiceEvent extends Event {

    private int action;

    /**
     * Constructor
     * @param user the Client user
     * @param action the chosen action encoding
     *               (?Map: Action-> int)
     */
    public ActionChoiceEvent(String user, int action){
        super(user);
        this.action=action;
    }
}
