package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.model.board.Square;

/**
 * @author Francesco Masciulli
 * represent the updatePlayer updated Position
 */
public class PositionUpdateEvent extends AbstractUpdateEvent {

    private Square position;

    /**
     * Constructor
     * @param user the Client user
     * @param updatedPlayer the updated Player
     * @param position  his next position
     */
    public PositionUpdateEvent(String user, String updatedPlayer, Square position){
        super(user, updatedPlayer);
        this.position=position;

    }
}
