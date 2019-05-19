package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.view.RemoteView;

public class PositionGrabRequestEvent extends PositionRequestEvent {

    public PositionGrabRequestEvent(String user, int[] possibleSquareX, int[] possibleSquareY) {
        super(user, possibleSquareX, possibleSquareY);
    }

    @Override
    public void performAction(RemoteView remoteView) {

    }
}
