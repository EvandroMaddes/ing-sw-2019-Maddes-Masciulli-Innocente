package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_select.PositionRequestEvent;
import it.polimi.ingsw.model.board.Square;

public class PositionChoiceEvent extends Event {

    private Square position;

    public PositionChoiceEvent(String user, Square position){
        super(user);
        this.position=position;
    }

}
