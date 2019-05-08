package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.EventType;

public class StartGameEvent extends Event {

    private boolean gameCanStart;

    StartGameEvent(String user, boolean canStart){
        super(user);
        this.gameCanStart=canStart;
        type= EventType.StartGameEvent;
    }

}
