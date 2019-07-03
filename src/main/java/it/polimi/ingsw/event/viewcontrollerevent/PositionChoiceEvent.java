package it.polimi.ingsw.event.viewcontrollerevent;

/**
 * Message to notify a generic position choice by the player
 *
 * @author Federico Innocente
 */
public abstract class PositionChoiceEvent extends ViewControllerEvent {
    /**
     * Is the row of the chosen position
     */
    private int positionX;
    /**
     * Is the column of the chosen position
     */
    private int positionY;

    /**
     * Constructor
     *
     * @param user      is the player username
     * @param positionX is the position row
     * @param positionY is the position column
     */
    PositionChoiceEvent(String user, int positionX, int positionY) {
        super(user);
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Getter method
     *
     * @return the position row
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Getter method
     *
     * @return the position column
     */
    public int getPositionY() {
        return positionY;
    }
}
