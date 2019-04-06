package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class Square {

    private Direction northDirection;
    private Direction southDirection;
    private Direction eastDirection;
    private Direction westDirection;
    private Room squareRoom;

    public ArrayList<Player> getSquarePlayers()
    {
        ArrayList<Player> players = null;

        return players;

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
