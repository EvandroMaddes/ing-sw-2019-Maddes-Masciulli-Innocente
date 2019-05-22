package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class ActionRequestEvent extends ControllerViewEvent {
    private boolean fireEnable;

    public ActionRequestEvent(String user, boolean couldFire) {
        super(user);
        fireEnable=couldFire;
    }

    public boolean isFireEnable() {
        return fireEnable;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.actionChoice(isFireEnable());
    }
}
