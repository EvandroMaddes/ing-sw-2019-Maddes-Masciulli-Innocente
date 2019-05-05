package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.event.Event;

/**
 * @author Francesco Masciulli
 * represent the User preferences about map and mod before a new game initialization
 *
 */
public class GameChoiceEvent extends Event {

    private String map;
    private String mod;

    /**
     * Constructor
     * @param user the "Master" Client user
     * @param map  the chosen map
     * @param mod  the chosen mod
     */
    public GameChoiceEvent(String user, String map, String mod){
        super(user);
        this.map=map;
        this.mod=mod;
    }

}
