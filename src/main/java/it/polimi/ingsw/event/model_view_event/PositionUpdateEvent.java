package it.polimi.ingsw.event.model_view_event;


import it.polimi.ingsw.event.Event;

/**
 * @author Francesco Masciulli
 * represent the updatePlayer updated Position
 */
public class PositionUpdateEvent extends ModelViewEvent {

    private int positionX;
    private int positionY;

    /**
     * Constructor
     * @param user the Client user
     * @param updatedPlayer the updated Player (given by int)
     * @param positionX  his next position X coordinate
     * @param positionY his next position Y coordinate
     */
    public PositionUpdateEvent(String user, int positionX, int positionY){
        super(user);
        this.positionX=positionX;
        this.positionY=positionY;

    }
}
