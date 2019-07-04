package it.polimi.ingsw.event.modelviewevent;

/**
 * Message to handle a generic position update
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public abstract class PositionUpdateEvent extends ModelViewBroadcastEvent {
    /**
     * Is the row of the updated position
     */
    private int positionX;
    /**
     * Is the column of the updated position
     */
    private int positionY;

    /**
     * Constructor
     * @param positionX is the updated position row
     * @param positionY is the updated position column
     */
    PositionUpdateEvent(int positionX, int positionY) {
        super();
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Getter method
     * @return the updated position row
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Getter method
     * @return the updated position column
     */
    public int getPositionY() {
        return positionY;
    }
}
