package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class PositionMoveRequestEvent extends PositionRequestEvent {

    public PositionMoveRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user, possibleSquareX, possibleSquareY);
    }

    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.positionMoveChoice(getPossibleSquareX(),getPossibleSquareY());
    }
}