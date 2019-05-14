package it.polimi.ingsw.event.view_controller_event;

public abstract class PositionChoiceEvent extends ViewControllerEvent {

    private int positionX;
    private int positionY;

    public PositionChoiceEvent(String user, int positionX, int positionY){
        super(user);
        this.positionX=positionX;
        this.positionY=positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
