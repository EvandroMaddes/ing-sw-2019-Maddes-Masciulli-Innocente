package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.PlayerBoard;

/**
 * @author Francesco Masciulli
 * represent the PlayerBoard Update after a damage, a mark or a death
 */
public class PlayerBoardUpdateEvent extends Event {

    private PlayerBoard playerBoard;

    /**
     * Constructor
     * @param user the Client user
     * @param updatedPlayer the hit Player (given by int)
     * @param playerBoard the Updated Board
     */
    public PlayerBoardUpdateEvent(String user, PlayerBoard playerBoard){
        super(user);
        this.playerBoard=playerBoard;
    }
}
