package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class EndGameUpdate extends ModelViewEvent {
    private String endGameMessage;

    public EndGameUpdate(String endGameMessage) {
        super("BROADCAST");
        this.endGameMessage = endGameMessage;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return null;
    }

    public String getEndGameMessage() {
        return endGameMessage;
    }
}
