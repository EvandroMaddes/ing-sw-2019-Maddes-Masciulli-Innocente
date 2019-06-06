package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class NewtonTargetSquareRequestEvent extends PositionRequestEvent {

    public NewtonTargetSquareRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user, possibleSquareX, possibleSquareY);
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.newtonTeleporterTargetSquareChoice(getPossibleSquareX(),getPossibleSquareY());
    }
}
