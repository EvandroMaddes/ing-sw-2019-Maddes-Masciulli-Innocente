package it.polimi.ingsw.event.controller_view_event;

import java.util.ArrayList;

public class WeaponDiscardRequestEvent extends WeaponRequestEvent {

    public WeaponDiscardRequestEvent(String user, ArrayList<String> weapons) {
        super(user, weapons);
    }
}
