package it.polimi.ingsw.event.controllerviewevent;

/**
 * Message for a generic position request
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public abstract class PositionRequestEvent extends ControllerViewEvent {
    /**
     * Are all the possible row
     */
    private int[] possibleSquareX;
    /**
     * Are all the possible column
     */
    private int[] possibleSquareY;

    /**
     * Constructor
     *
     * @param user            is the player username
     * @param possibleSquareX Are all the possible row
     * @param possibleSquareY Are all the possible column
     */
    PositionRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user);
        this.possibleSquareX = possibleSquareX;
        this.possibleSquareY = possibleSquareY;
    }

    /**
     * Getter method
     *
     * @return all the possible row
     */
    public int[] getPossibleSquareY() {
        return possibleSquareY;
    }

    /**
     * Getter method
     *
     * @return all the possible column
     */
    public int[] getPossibleSquareX() {
        return possibleSquareX;
    }
}
