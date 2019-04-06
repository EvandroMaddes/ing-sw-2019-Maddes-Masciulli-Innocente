package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.Square;

public class Direction {

    private Square nextSquare;
    private boolean reachable;

    public Square getSquare()
    {
        return nextSquare;
    }

    public boolean isReachable()
    {
        return reachable;
    }

}
