package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;

/**
 * @author Francesco Masciulli
 *  it could be use in the initializing phase for choosing the player
 *   or for choosing a target to shot
 */
public class PlayerRequestEvent extends Event {

    private boolean[] targetPlayers = new boolean[5];
    private int targetsNumber;

    public boolean[] getTargetPlayers() {
        return targetPlayers;
    }

    public int getTargetsNumber() {
        return targetsNumber;
    }

    /**
     * Constructor
     * @param user the Client user
     * @param targetPlayers the playerList from wich he must chose
     * @param targetsNumber the number of player that he must chose
     */
    public PlayerRequestEvent(String user,boolean[] targetPlayers, int targetsNumber){
        super(user);
        this.targetPlayers=targetPlayers;
        this.targetsNumber= targetsNumber;
    }
}
