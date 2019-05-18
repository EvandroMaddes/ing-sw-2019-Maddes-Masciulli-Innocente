package it.polimi.ingsw.event.controller_view_event;

import java.util.ArrayList;

public abstract class WeaponRequestEvent extends ControllerViewEvent {
    private ArrayList<String> weapons;

    public WeaponRequestEvent(String user, ArrayList<String> weapons) {
        super(user);
        this.weapons = weapons;
    }
}
