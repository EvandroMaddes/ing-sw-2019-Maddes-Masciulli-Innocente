package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Square {

    private Direction northDirection;
    private Direction southDirection;
    private Direction eastDirection;
    private Direction westDirection;
    private Room squareRoom;

    public ArrayList<Player> getSquarePlayers()
    {

    }

    public Square getNextSquare(Direction direction)
    {
        return direction.getSquare();
    }

    public boolean checkDirection(Direction direction)
    {
        return direction.isReachable();
    }
}
