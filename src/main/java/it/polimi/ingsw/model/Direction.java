package it.polimi.ingsw.model;

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
