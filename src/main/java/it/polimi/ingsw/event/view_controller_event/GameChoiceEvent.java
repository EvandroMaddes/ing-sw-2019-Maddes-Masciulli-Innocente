package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.EventType;

/**
 * @author Francesco Masciulli
 * represent the User preferences about map and mod before a new game initialization
 *
 * map coding is:
 *  0: smaller map
 *  1: first medium map
 *  2: second medium map
 *  3: bigger map
 * mod coding is:
 *  0: KillShot mod
 *  1: Domination mod
 *
 */
public class GameChoiceEvent extends Event {

    private int map;
    private int mod;

    /**
     * Constructor
     * @param user the "Master" Client user
     * @param map  the chosen map
     * @param mod  the chosen mod
     */
    public GameChoiceEvent(String user, int map, int mod){
        super(user);
        this.map=map;
        this.mod=mod;
        type= EventType.GameChoiceEvent;
    }

    public int getMap() {
        return map;
    }

    public int getMod() {
        return mod;
    }
}
