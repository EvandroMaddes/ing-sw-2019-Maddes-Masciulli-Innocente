package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;

/**
 * @author Francesco Masciulli
 *  it could be use in the initializing phase for choosing the player
 *   or for choosing a target to shot
 */
public class PlayerRequestEvent extends Event {

    private ArrayList<Character> targetPlayers;
    private int targetsNumber;

    public ArrayList<Character> getTargetPlayers() {
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
    public PlayerRequestEvent(String user, ArrayList<Character> targetPlayers, int targetsNumber){
        super(user);
        this.targetPlayers=targetPlayers;
        this.targetsNumber= targetsNumber;
        type= EventType.PlayerRequestEvent;
    }
}
