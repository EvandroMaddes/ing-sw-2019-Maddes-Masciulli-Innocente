package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.event.Event;

public class PositionChoiceEvent extends Event {

    private int positionX;
    private int positionY;

    public PositionChoiceEvent(String user, int positionX){
        super(user);
        this.positionX=positionX;
        this.positionX=positionX;
    }

}
