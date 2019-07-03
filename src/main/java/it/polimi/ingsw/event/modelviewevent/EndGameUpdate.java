package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class EndGameUpdate extends ModelViewBroadcastEvent {
    private String endGameMessage;

    public EndGameUpdate(String endGameMessage) {
        this.endGameMessage = endGameMessage;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        remoteView.disconnect();
        return remoteView.winnerUpdate(this);
    }

    public String getEndGameMessage() {
        return endGameMessage;
    }
}
