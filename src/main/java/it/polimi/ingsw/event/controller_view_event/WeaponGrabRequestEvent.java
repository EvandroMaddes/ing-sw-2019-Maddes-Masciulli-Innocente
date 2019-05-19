package it.polimi.ingsw.event.controller_view_event;

import java.util.ArrayList;

public class WeaponGrabRequestEvent extends WeaponRequestEvent {

    public WeaponGrabRequestEvent(String user, ArrayList<String> weapons) {
        super(user, weapons);
    }
}
