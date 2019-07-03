package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * This Event update the match end
 * @author Federico Innocente
 */
public class EndGameUpdate extends ModelViewBroadcastEvent {
    /**
     * Is the endGame String that contains the result
     */
    private String endGameMessage;

    /**
     * Constructor: call super-class constructor and set the endGameMessage
     * @param endGameMessage
     */
    public EndGameUpdate(String endGameMessage) {
        this.endGameMessage = endGameMessage;
    }

    /**
     * performAction implementation: handle the match result notify
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        remoteView.disconnect();
        return remoteView.winnerUpdate(this);
    }

    public String getEndGameMessage() {
        return endGameMessage;
    }
}
