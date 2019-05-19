package it.polimi.ingsw.event.controller_view_event;

public class PositionMoveRequestEvent extends PositionRequestEvent {

    public PositionMoveRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user, possibleSquareX, possibleSquareY);
    }
}
