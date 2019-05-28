package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class WeaponRequestEvent extends ControllerViewEvent {
    private ArrayList<String> weapons;

    public WeaponRequestEvent(String user, ArrayList<String> weapons) {
        super(user);
        this.weapons = weapons;
    }

    public ArrayList<String> getWeapons() {
        return weapons;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.weaponChoice(getWeapons());
    }
}
