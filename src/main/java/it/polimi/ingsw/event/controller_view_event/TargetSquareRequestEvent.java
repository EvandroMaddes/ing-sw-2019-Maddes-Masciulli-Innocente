package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.view.RemoteView;

public class TargetSquareRequestEvent extends ControllerViewEvent {
    private int[] possibleTargetsX;
    private int[] possibleTargetsY;

    public TargetSquareRequestEvent(String user, int[] possibleTargetsX, int[] possibleTargetsY) {
        super(user);
        this.possibleTargetsX = possibleTargetsX;
        this.possibleTargetsY = possibleTargetsY;
    }

    public int[] getPossibleTargetsX() {
        return possibleTargetsX;
    }

    public int[] getPossibleTargetsY() {
        return possibleTargetsY;
    }

    @Override
    public void performAction(RemoteView remoteView) {
        // TODO: 2019-05-22
    }
}