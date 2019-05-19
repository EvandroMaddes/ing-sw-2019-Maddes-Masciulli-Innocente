package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Observable;

/**
 * @author Evandro Maddes
 */
public abstract class Square extends Observable {
    private final int row;
    private final int column;
    private final Square[] nearSquares = new Square[4];//north,south, east,west
    private final boolean[] reachable = new boolean[4];//north,south, east,west
    private  String squareColour;
    private ArrayList<Player> squarePlayers = new ArrayList<Player>();


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
    public ArrayList<Player> getSquarePlayers() {
        return squarePlayers;
    }

    /**
     *
     * @param currentPlayer  Player on this square
     */
    public void addCurrentPlayer(Player currentPlayer){

        squarePlayers.add(currentPlayer);

    }

    /**
     * @param currentPlayer player moves from this square
     */
    public void removeCurrentPlayer(Player currentPlayer){

        squarePlayers.remove(currentPlayer);

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


    /**
     *
     * @return plyers visible by this square
     */
    public ArrayList<Player>  findVisiblePlayers(){
     ArrayList<Player> visiblePlayers = new ArrayList<Player>();


    visiblePlayers.addAll(findRoomPlayers(this,this.getSquareColour())); // player in the same room of current square);

     for(int i =0; i<4; i++){
         if(checkDirection(i) && getNextSquare(i).getSquareColour()!= this.getSquareColour())
         visiblePlayers.addAll(findRoomPlayers(getNextSquare(i),getNextSquare(i).getSquareColour()));//player in room near current square
     }

      return visiblePlayers;
    }
    /**
     *
     * @param square one square of the room
     * @param colourSquare colour of the room
     * @return players of one room
     */
    private ArrayList<Player> findRoomPlayers(Square square, String colourSquare){
        ArrayList<Square> squareRoom = new ArrayList<Square>();
        squareRoom.add(square);

        ArrayList<Player> playerRoom = new ArrayList<Player>();

        for (Square currentSquare : squareRoom) {
            for(int i=0; i<4; i++) {
                if (currentSquare.checkDirection(i)) {
                  //  currentSquare = getNextSquare(i);
                    if (currentSquare.getNextSquare(i).getSquareColour() == colourSquare && !squareRoom.contains(currentSquare)) {

                        squareRoom.add(currentSquare.getNextSquare(i));

                    }
                }
            }
        }
        for (Square currentSquare:squareRoom
             ) {
            playerRoom.addAll(currentSquare.getSquarePlayers());

        }
        return playerRoom;
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}
