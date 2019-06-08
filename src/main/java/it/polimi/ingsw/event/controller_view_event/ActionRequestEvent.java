package it.polimi.ingsw.event.controller_view_event;

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

    public boolean isFireEnable() {
        // TODO: 2019-06-07
        return /*fireEnable*/ false;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.actionChoice(isFireEnable());
    }
}
