package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.EventType;

import java.util.ArrayList;

/**
 * @author Francesco Masciulli
 * represent the  PositionRequest after a movementAction choice
 */
public class PositionRequestEvent extends Event {

    private ArrayList<Integer> possiblePositionsX;
    private ArrayList<Integer> possiblePositionsY;

    /**
     *
     * @param user the Client user
     * @param possiblePositionsX are the X Squares coordinate in which he could move
     * @param possiblePositionsY are the Y Squares coordinate in which he could move,
     *                           he must chose just one pair of coordinates;
     */
    public PositionRequestEvent(String user, ArrayList<Integer> possiblePositionsX, ArrayList<Integer> possiblePositionsY){
        super(user);
        this.possiblePositionsX=possiblePositionsX;
        this.possiblePositionsY=possiblePositionsY;
        type= EventType.PositionRequestEvent;
    }

    public ArrayList<Integer> getPossiblePositionsX() {
        return possiblePositionsX;
    }

    public ArrayList<Integer> getPossiblePositionsY() {
        return possiblePositionsY;
    }
}
