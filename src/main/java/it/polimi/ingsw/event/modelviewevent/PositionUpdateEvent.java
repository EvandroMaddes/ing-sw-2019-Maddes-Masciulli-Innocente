package it.polimi.ingsw.event.modelviewevent;

public abstract class PositionUpdateEvent extends ModelViewBroadcastEvent {
    private int positionX;
    private int positionY;

    public PositionUpdateEvent(int positionX, int positionY) {
        super();
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}