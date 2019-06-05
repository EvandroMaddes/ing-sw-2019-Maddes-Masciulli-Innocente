package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class TeleporterTargetRequestEvent extends PositionRequestEvent {

    public TeleporterTargetRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user, possibleSquareX, possibleSquareY);
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        // TODO: 2019-06-04
        return remoteView.newtonTeleporterTargetSquareChoice(getPossibleSquareX(),getPossibleSquareY());
    }
}
