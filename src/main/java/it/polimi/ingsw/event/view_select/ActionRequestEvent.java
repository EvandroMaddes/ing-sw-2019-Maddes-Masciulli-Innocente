package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;

/**
 * @author Francesco Masciulli
 * it represent the ActionRequestEvent
 */
public class ActionRequestEvent extends Event {

    private boolean[] availableActions= new boolean[3];
    private int damageContext;

    /**
     *
     * @param user the Client user
     * @param availableActions the actionList from which he must chose just one
     * @param damageContext set the right avaible action
     *                         1: normal action
     *                         2: first Adrenalinic
     *                         3: second Adrenalinic
     *                         4: final Frenzy
     *                         5: final Frenzi First player
     */
    public ActionRequestEvent(String user, boolean[] availableActions, int damageContext){
        super(user);
        this.availableActions=availableActions;
        this.damageContext=damageContext;
    }
}
