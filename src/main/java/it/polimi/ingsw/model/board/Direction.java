package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.Square;

/**
 * @author Evandro Maddes
 */
public class Direction {

    private final Square nextSquare;
    private final boolean reachable;

    /**
     *
     * @return the square in this direction
     */
    public Square getNextSquare()
    {
        return nextSquare;
    }

    /**
     * it checks if there is wall or not
     * @return
     */

    public boolean isReachable()
    {
        return reachable;
    }

}
