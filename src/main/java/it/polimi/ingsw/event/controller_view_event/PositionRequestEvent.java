package it.polimi.ingsw.event.controller_view_event;

public abstract class PositionRequestEvent extends ControllerViewEvent {
    private int[] possibleSquareX;
    private int[] possibleSquareY;

    public PositionRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user);
        this.possibleSquareX = possibleSquareX;
        this.possibleSquareY = possibleSquareY;
    }


}