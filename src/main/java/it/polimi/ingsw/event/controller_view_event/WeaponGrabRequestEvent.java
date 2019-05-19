package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class WeaponGrabRequestEvent extends WeaponRequestEvent {

    @Override
    public void performAction(RemoteView remoteView) {

    }

    public WeaponGrabRequestEvent(String user, ArrayList<String> weapons) {
        super(user, weapons);
    }
}
