package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.board.Square;

import java.util.ArrayList;

/**
 * represent the  PositionRequest after a movementAction choice
 */
public class PositionRequestEvent extends Event {

    private ArrayList<Square> possiblePositions;

    /**
     *
     * @param user the Client user
     * @param possiblePositions are the Square in which he could move,
     *                          he must chose just one of them
     */
    public PositionRequestEvent(String user, ArrayList<Square> possiblePositions){
        super(user);
        this.possiblePositions=possiblePositions;
    }
}
