package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class TeleporterTargetRequestEvent extends PositionRequestEvent {

    public TeleporterTargetRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user, possibleSquareX, possibleSquareY);
    }

    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.newtonTeleporterTargetSquareChoice(getPossibleSquareX(),getPossibleSquareY());
    }
}
