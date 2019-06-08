package it.polimi.ingsw.event.model_view_event;

public abstract class PositionUpdateEvent extends ModelViewEvent {
    private int positionX;
    private int positionY;

    public PositionUpdateEvent(String user, int positionX, int positionY) {
        super(user);
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
