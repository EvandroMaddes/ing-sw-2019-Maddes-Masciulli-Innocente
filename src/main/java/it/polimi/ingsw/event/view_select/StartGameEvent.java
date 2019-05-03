package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;

public class StartGameEvent extends Event {

    private boolean gameCanStart;

    StartGameEvent(String user, boolean canStart){
        super(user);
        this.gameCanStart=canStart;
    }

}
