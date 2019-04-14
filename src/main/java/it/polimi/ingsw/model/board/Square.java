package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * @author Evandro Maddes
 */
public class Square {

    private Direction northDirection;
    private Direction southDirection;
    private Direction eastDirection;
    private Direction westDirection;
    private Room squareRoom;

    /**
     *
     * @param squareRoom room of the square
     */
    public void setSquareRoom(Room squareRoom) {
        this.squareRoom = squareRoom;
    }

    /**SAREBBE OTTIMALE NON PASSARE NESSUN PARAMETRO
     * this metod checks which players are on this square
     * @param playersGame  who are playing
     * @return players on the square
     */
    public ArrayList<Player> getSquarePlayers(ArrayList<Player> playersGame)
    {
        ArrayList<Player> playersSquare = null;
        int i=0;

        while (i < playersGame.size()) {

            if(playersGame.get(i).getPosition()==this)
            {
                playersSquare.add(playersGame.get(i));
            }

            i++;
        }
        return playersSquare;
    }

    /**
     *It calls checkDirection and it sees the square in passed direction
     * @param direction direction of the moviment
     * @return square in passed direction
     */
    public Square getNextSquare(Direction direction)
    {
        return direction.getNextSquare();
    }

    /**
     * it checks if the square in the passed direction is richeable by the value of attribute richeable
     * @param direction direction of the moviment
     * @return true if the value of attribute richeable is true
     */
    public boolean checkDirection(Direction direction)
    {
        return direction.isReachable();
    }
}
