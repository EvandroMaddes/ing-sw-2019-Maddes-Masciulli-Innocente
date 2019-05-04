package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.model.player.PlayerBoard;

/**
 * @author Francesco Masciulli
 * represent the PlayerBoard Update after a damage, a mark or a death
 */
public class PlayerBoardUpdateEvent extends AbstractUpdateEvent {

    private PlayerBoard playerBoard;

    /**
     * Constructor
     * @param user the Client user
     * @param updatedPlayer the hit Player
     * @param playerBoard the Updated Board
     */
    public PlayerBoardUpdateEvent(String user, String updatedPlayer, PlayerBoard playerBoard){
        super(user, updatedPlayer);
        this.playerBoard=playerBoard;
    }
}
