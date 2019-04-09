package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class Square {

    private Direction northDirection;
    private Direction southDirection;
    private Direction eastDirection;
    private Direction westDirection;
    private Room squareRoom;

    /**
     * controlla l'attributo position di ogni player
     * @return i player presenti sul quadrato
     */
    public ArrayList<Player> getSquarePlayers()
    {
        ArrayList<Player> playersInSquare = null;


        return playersInSquare;

    }

    /**
     *It calls checkDirection and it sees the square in passed direction
     * @param direction
     * @return square in passed direction
     */
    public Square getNextSquare(Direction direction)
    {
        return direction.getSquare();
    }

    /**
     * it checks if the square in the passed direction is richeable by the value of attribute richeable
     * @param direction
     * @return true if the value of attribute richeable is true
     */
    public boolean checkDirection(Direction direction)
    {
        return direction.isReachable();
    }
}
