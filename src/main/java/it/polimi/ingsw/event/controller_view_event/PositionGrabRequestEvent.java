package it.polimi.ingsw.event.controller_view_event;

public class PositionGrabRequestEvent extends PositionRequestEvent {

    public PositionGrabRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user, possibleSquareX, possibleSquareY);
    }
}
