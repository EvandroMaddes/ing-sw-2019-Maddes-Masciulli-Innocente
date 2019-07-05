package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Is the Square abstract class
 *
 * @author Evandro Maddes
 * @author Federico Innocente
 */
public abstract class Square extends Observable {
    /**
     * is the row in the Map squares matrix
     */
    private final int row;
    /**
     * is the column in the Map squares matrix
     */
    private final int column;
    /**
     * An array containig the near squares;
     * the association between cardinal directions and array index is:
     * north = 0;
     * south = 1;
     * east = 2;
     * west = 3;
     */
    private final Square[] nearSquares = new Square[4];
    /**
     * An array containing booleans that indicate if a near square is reachable
     * the association between cardinal directions and array index is:
     * north = 0;
     * south = 1;
     * east = 2;
     * west = 3;
     */
    private final boolean[] reachable = new boolean[4];
    /**
     * A String that represent the square's room colour
     */
    private String squareColour;
    /**
     * An ArrayList that contains all the players on the square
     */
    private ArrayList<Player> squarePlayers = new ArrayList<>();


    /**
     * Constructor: set row and column
     *
     * @param row    of the Square
     * @param column of the Square
     */
    public Square(int row, int column) {

        this.row = row;
        this.column = column;

    }

    /**
     * Will'be implemented in the concrete Classes;
     *
     * @param grabber is the player that would grab from the Square
     * @return true if the square have ammo or at least a weapon to grab
     */
    public abstract boolean isGrabbable(Player grabber);

    /**
     * Seter method: set the near squares
     *
     * @param north is the Square to the north
     * @param south is the Square to the south
     * @param east  is the Square to the east
     * @param west  is the Square to the west
     */
    void setNearSquares(Square north, Square south, Square east, Square west) {

        nearSquares[0] = north;
        nearSquares[1] = south;
        nearSquares[2] = east;
        nearSquares[3] = west;

    }

    /**
     * Setter method: set the respective value depending if the near square in that direction is reachable or not
     *
     * @param reachableNorth is true if the Square to the north is reachable
     * @param reachableSouth is true if the Square to the south is reachable
     * @param reachableEast  is true if the Square to the east is reachable
     * @param reachableWest  is true if the Square to the west is reachable
     */
    void setSquareReachable(boolean reachableNorth, boolean reachableSouth, boolean reachableEast, boolean reachableWest) {
        reachable[0] = reachableNorth;
        reachable[1] = reachableSouth;
        reachable[2] = reachableEast;
        reachable[3] = reachableWest;

    }

    /**
     * Setter method: set the Square colour
     *
     * @param squareColour is the String that identifies the colour
     */
    void setSquareColour(String squareColour) {
        this.squareColour = squareColour;
    }

    /**
     * Getter method:
     *
     * @return the square column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Getter method:
     *
     * @return the square row
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter method:
     *
     * @return the ArrayList containing the player on the square
     */
    public ArrayList<Player> getSquarePlayers() {
        return squarePlayers;
    }

    /**
     * Getter method:
     *
     * @return the nearSquare array
     */
    Square[] getNearSquares() {
        return nearSquares;
    }

    /**
     * Getter method
     *
     * @return the room's colour of this Square;
     */
    public String getSquareColour() {
        return squareColour;
    }

    /**
     * Add a player to this square's player List
     *
     * @param currentPlayer Player on this square
     */
    public void addCurrentPlayer(Player currentPlayer) {

        squarePlayers.add(currentPlayer);

    }

    /**
     * Remove a player from this square's player List
     *
     * @param currentPlayer player that isn't anymore on this square
     */
    public void removeCurrentPlayer(Player currentPlayer) {

        squarePlayers.remove(currentPlayer);

    }

    /**
     * It calls checkDirection and it sees the square in passed direction
     *
     * @param direction direction of the movement
     * @return square in passed direction
     */
    public Square getNextSquare(int direction) {
        return nearSquares[direction];
    }

    /**
     * it checks if the square in the passed direction is reachable by the value of attribute reachable
     *
     * @param direction direction of the movement
     * @return true if the value of attribute reachable is true
     */
    public boolean checkDirection(int direction) {
        return reachable[direction];
    }


    /**
     * Method that evaluate the players that are visible, as intended on
     *
     * @return plyers visible by this square
     */
    public ArrayList<Player> findVisiblePlayers() {
        ArrayList<Square> visibleSquares = findVisibleSquare();
        ArrayList<Player> visiblePlayers = new ArrayList<>();
        for (Square s : visibleSquares) {
            visiblePlayers.addAll(s.getSquarePlayers());
        }
        return visiblePlayers;
    }

    /**
     * Method that find all the players in the square's room
     *
     * @return an ArrayList that contains all the square's room players
     */
    public ArrayList<Player> findRoomPlayers() {
        String roomColour = this.getSquareColour();
        ArrayList<Square> roomSquares = findVisibleSquare();
        ArrayList<Player> roomPlayers = new ArrayList<>();
        for (Square s : roomSquares) {
            if (s.getSquareColour().equals(roomColour))
                roomPlayers.addAll(s.getSquarePlayers());
        }
        return roomPlayers;
    }

    /**
     * Override of Observable notifyObserver
     *
     * @param arg is the updated Object
     */
    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }

    /**
     * Find all the Squares reachable from the current Square in a given number of moves
     *
     * @param numberOfMoves is the max number of step from the Square
     * @return an ArrayList that contains all the Squares reachable in the given number of step
     */
    public ArrayList<Square> reachableInMoves(int numberOfMoves) {
        ArrayList<Square> possibleDestination = new ArrayList<>();

        ArrayList<Square> reachAtPreviousStep = new ArrayList<>();
        ArrayList<Square> reachInThatStep = new ArrayList<>();
        reachAtPreviousStep.add(this);
        possibleDestination.add(this);
        for (int i = 0; i < numberOfMoves; i++) {
            for (Square currentSquare : reachAtPreviousStep) {
                for (int direction = 0; direction < 4; direction++) {
                    Square possibleNextDestination = currentSquare.getNextSquare(direction);
                    if (currentSquare.checkDirection(direction) && !reachAtPreviousStep.contains(possibleNextDestination)
                            && !reachInThatStep.contains(possibleNextDestination)) {
                        reachInThatStep.add(possibleNextDestination);
                    }
                }
            }
            for (Square currSquare : reachInThatStep) {
                if (!possibleDestination.contains(currSquare)) {
                    possibleDestination.add(currSquare);
                }

            }
            reachAtPreviousStep.clear();
            reachAtPreviousStep.addAll(reachInThatStep);
            reachInThatStep.clear();
        }
        return possibleDestination;
    }

    /**
     * Find all the players that are on a near Square
     *
     * @return an ArrayList containing all the near squares players
     */
    public ArrayList<Player> getNextSquarePlayer() {
        ArrayList<Player> possibleTargets = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++) {
            if (this.checkDirection(direction))
                possibleTargets.addAll(this.getNextSquare(direction).getSquarePlayers());
        }
        return possibleTargets;
    }

    /**
     * Find all the visible Squares from the current Square
     *
     * @return an ArrayList containing all the visibles squares
     */
    public ArrayList<Square> findVisibleSquare() {
        ArrayList<Square> visibleSquare = new ArrayList<>();
        ArrayList<Square> toCheckSquare = new ArrayList<>();
        ArrayList<String> visibleRoomColours = new ArrayList<>();
        visibleRoomColours.add(this.getSquareColour());


        for (int i = 0; i < 4; i++) {
            if (this.checkDirection(i) && !visibleRoomColours.contains(this.getNextSquare(i).getSquareColour()))
                visibleRoomColours.add(this.getNextSquare(i).getSquareColour());
        }
        toCheckSquare.add(this);
        while (!toCheckSquare.isEmpty()) {
            for (int direction = 0; direction < 4; direction++) {
                if (toCheckSquare.get(0).checkDirection(direction) &&
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
