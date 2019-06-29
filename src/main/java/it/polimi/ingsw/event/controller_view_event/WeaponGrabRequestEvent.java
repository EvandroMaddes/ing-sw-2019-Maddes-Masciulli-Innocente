package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class WeaponGrabRequestEvent extends WeaponRequestEvent {


    public WeaponGrabRequestEvent(String user, ArrayList<String> weapons) {
        super(user, weapons);
    }

    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.weaponGrabChoice(getWeapons());
    }
}
