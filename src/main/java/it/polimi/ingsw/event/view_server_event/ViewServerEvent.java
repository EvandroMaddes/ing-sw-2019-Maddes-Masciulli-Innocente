package it.polimi.ingsw.event.view_server_event;

import it.polimi.ingsw.event.Event;

public abstract class ViewServerEvent  extends Event {
    private boolean isNewGame;

    public ViewServerEvent(String user, boolean isNewGame) {
        super(user);
        this.isNewGame = isNewGame;
    }

    public boolean isNewGame() {
        return isNewGame;
    }

    public abstract String performAction();
}
