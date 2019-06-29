package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;
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
    public abstract boolean isGrabbable(Player grabber);



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
    public ArrayList<Player> findVisiblePlayers(){
        ArrayList<Square> visibleSquares = findVisibleSquare();
        ArrayList<Player> visiblePlayers = new ArrayList<>();
        for (Square s :visibleSquares) {
            visiblePlayers.addAll(s.getSquarePlayers());
        }
        return visiblePlayers;
    }

    // TODO: 2019-06-11 che fare di questo sotto? mai usato, non capisco se serve, penso si possa cancellare
/*    /**
     *
     * @param square one square of the room
     * @param colourSquare colour of the room
     * @return players of one room
     */
/*    public ArrayList<Player> findRoomPlayers(Square square, String colourSquare){
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
*/
    /**
     *
     * @return all current room players
     */
    public ArrayList<Player> findRoomPlayers(){
        String roomColour = this.getSquareColour();
        ArrayList<Square> roomSquares = findVisibleSquare();
        ArrayList<Player> roomPlayers = new ArrayList<>();
        for (Square s:roomSquares) {
            if (s.getSquareColour().equals(roomColour))
                roomPlayers.addAll(s.getSquarePlayers());
        }
        return roomPlayers;
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }

    public ArrayList<Square> reachableInMoves(int numberOfMoves){
        ArrayList<Square> possibleDestination = new ArrayList<>();

        ArrayList<Square> reachAtPreviousStep = new ArrayList<>();
        ArrayList<Square> reachInThatStep = new ArrayList<>();
        reachAtPreviousStep.add(this);
        possibleDestination.add(this);
        for (int i = 0; i < numberOfMoves; i ++){
            for (Square currentSquare: reachAtPreviousStep) {
                for (int direction = 0; direction < 4; direction++){
                    Square possibleNextDestination = currentSquare.getNextSquare(direction);
                    if(currentSquare.checkDirection(direction) && !reachAtPreviousStep.contains(possibleNextDestination)
                                    && !reachInThatStep.contains(possibleNextDestination)){
                        reachInThatStep.add(possibleNextDestination);
                    }
                }
            }
            for (Square currSquare : reachInThatStep) {
                if(!possibleDestination.contains(currSquare)){
                    possibleDestination.add(currSquare);
                }

            }
            //possibleDestination.addAll(reachInThatStep);
            reachAtPreviousStep.clear();
            reachAtPreviousStep.addAll(reachInThatStep);
            reachInThatStep.clear();
        }
        return possibleDestination;
    }

    public ArrayList<Player> getNextSquarePlayer(){
        ArrayList<Player> possibleTargets = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++){
            if (this.checkDirection(direction))
                possibleTargets.addAll(this.getNextSquare(direction).getSquarePlayers());
        }
        return possibleTargets;
    }

    public ArrayList<Square> findVisibleSquare(){
        ArrayList<Square> visibleSquare = new ArrayList<>();
        ArrayList<Square> toCheckSquare = new ArrayList<>();
        ArrayList<String> visibleRoomColours = new ArrayList<>();
        visibleRoomColours.add(this.getSquareColour());



        for (int i = 0; i < 4; i++){
            if ( this.checkDirection(i) && !visibleRoomColours.contains(this.getNextSquare(i).getSquareColour()))
                visibleRoomColours.add(this.getNextSquare(i).getSquareColour());
        }
        toCheckSquare.add(this);
        while (!toCheckSquare.isEmpty()){
            for (int direction = 0; direction < 4; direction++){
                if ( toCheckSquare.get(0).checkDirection(direction) &&
                        !visibleSquare.contains(toCheckSquare.get(0).getNextSquare(direction)) &&
                        !toCheckSquare.contains(toCheckSquare.get(0).getNextSquare(direction)) &&
                        visibleRoomColours.contains(toCheckSquare.get(0).getNextSquare(direction).getSquareColour())) {
                    toCheckSquare.add(toCheckSquare.get(0).getNextSquare(direction));
                }
            }
            visibleSquare.add(toCheckSquare.get(0));
            toCheckSquare.remove(toCheckSquare.get(0));
        }
        return visibleSquare;
    }
}
