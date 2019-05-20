package it.polimi.ingsw.event.controller_view_event;

import java.util.ArrayList;

public class WeaponReloadRequestEvent extends WeaponRequestEvent {

    public WeaponReloadRequestEvent(String user, ArrayList<String> weapons) {
        super(user, weapons);
    }
}
