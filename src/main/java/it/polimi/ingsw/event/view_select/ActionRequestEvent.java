package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.EventType;

/**
 * @author Francesco Masciulli
 * it represent the ActionRequestEvent
 * availableActions the actionList from which he must chose just one
 * availableAction[i]:
 *  0:  couldMove
 *  1:  couldGrab
 *  2:  couldShot
 *  (couldMove and couldGrab are ALWAYS true)
 *
 */
public class ActionRequestEvent extends Event {

    private boolean[] availableActions= new boolean[3];


    /**
     *
     * @param user the Client user
     * @param couldShot is a boolean that indicates if the player has a valid target
     *
     */
    public ActionRequestEvent(String user,boolean couldShot){
        super(user);
        availableActions[0] = true;
        availableActions[1] = true;
        availableActions[2] = couldShot;
        type= EventType.ActionRequestEvent;

    }

    public boolean[] getAvailableActions() {
        return availableActions;
    }
}
