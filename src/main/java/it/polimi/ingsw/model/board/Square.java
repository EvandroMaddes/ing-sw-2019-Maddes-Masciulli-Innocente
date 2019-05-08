package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * @author Evandro Maddes
 */
public abstract class Square {
    private final int row;
    private final int column;
    private final Square[] nearSquares = new Square[4];//north,south, east,west
    private final boolean[] reachable = new boolean[4];//north,south, east,west
    private  String squareColour;

    /**
     * constructor
     * @param row
     * @param column
     */
    public Square(int row, int column ){

        this.row = row;
        this.column = column;

    }

    /**
     *
     * @param north
     * @param south
     * @param east
     * @param west
     */
    public void setNearSquares(Square north, Square south, Square east,Square west){

        nearSquares[0]=north;
        nearSquares[1]=south;
        nearSquares[2]=east;
        nearSquares[3]=west;

    }

    /**
     *
     * @param reachableNorth
     * @param reachableSouth
     * @param reachableEast
     * @param reachableWest
     */
    public void setSquareReachable(boolean reachableNorth,boolean reachableSouth,boolean reachableEast,boolean reachableWest){
        reachable[0]=reachableNorth;
        reachable[1]=reachableSouth;
        reachable[2]=reachableEast;
        reachable[3]=reachableWest;

    }

    /**
     *
     * @param squareColour
     */
    public void setSquareColour(String squareColour) {
        this.squareColour = squareColour;
    }

    /**
     *
     * @return
     */
    public int getColumn() {
        return column;
    }

    /**
     *
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     *
     * @return
     */
    public boolean[] getReachable() {
        return reachable;
    }

    /**
     *
     * @return
     */
    public Square[] getNearSquares() {
        return nearSquares;
    }

    /**
     * Getter method
     * @return the room's colour of this Square;
     */
    public String getSquareColour() {
        return squareColour;
    }

    /**
     * @return true if the square have ammo or at least a weapon to grab
     */
    public abstract boolean isGrabbable();

    /**SAREBBE OTTIMALE NON PASSARE NESSUN PARAMETRO
     * this method checks which players are on this square
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
     * @param direction direction of the movement
     * @return square in passed direction
     */
    public Square getNextSquare( int direction)
    {
        return nearSquares[direction];
    }

    /**
     * it checks if the square in the passed direction is reachable by the value of attribute reachable
     * @param direction direction of the movement
     * @return true if the value of attribute reachable is true
     */
    public boolean checkDirection(int direction)
    {
        return reachable[direction];
    }

    public ArrayList<Player> findVisiblePlayers(){
        return null;
    }


}
