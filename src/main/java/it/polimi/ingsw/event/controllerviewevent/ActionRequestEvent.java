package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class ActionRequestEvent extends ControllerViewEvent {
    /**
     * 0 - MOVE
     * 1 - GRAB
     * 2 - SHOT
     */
    private boolean[] usableActions;

    public ActionRequestEvent(String user, boolean[] usableActions) {
        super(user);
        this.usableActions = usableActions;
    }

    private boolean isFireEnable() {
        return usableActions[2];
    }

    public boolean[] getUsableActions() {
        return usableActions;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.actionChoice(isFireEnable());
    }
}
