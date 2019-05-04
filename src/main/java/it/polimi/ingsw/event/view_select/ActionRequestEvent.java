package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;

/**
 * @author Francesco Masciulli
 * it represent the ActionRequestEvent
 */
public class ActionRequestEvent extends Event {

    private boolean[] availableActions= new boolean[5];
    private int damageContext;

    public boolean[] getAvailableActions() {
        return availableActions;
    }

    public int getDamageContext() {
        return damageContext;
    }

    /**
     *
     * @param user the Client user
     * @param availableActions the actionList from which he must chose just one
     *                         availableAction[i]:
     *                          0:  couldMove
     *                          1:  couldGrab
     *                          2:  couldShot
     *                          3:  couldReload
     *                          4:  endTurn (Always true, must be set by Controller)
     * @param damageContext set the right available action
     *                         1: normal action
     *                         2: + first Adrenalinic
     *                         3: + second Adrenalinic
     *                         4: final Frenzy
     *                         5: final Frenzy First player
     */
    public ActionRequestEvent(String user, boolean[] availableActions, int damageContext){
        super(user);
        this.availableActions=availableActions;
        this.damageContext=damageContext;
    }
}
