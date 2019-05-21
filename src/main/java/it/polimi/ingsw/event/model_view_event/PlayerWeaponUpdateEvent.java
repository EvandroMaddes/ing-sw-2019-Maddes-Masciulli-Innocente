package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class PlayerWeaponUpdateEvent extends ModelViewEvent {
    private String[] playerWeapon;

    public PlayerWeaponUpdateEvent(String user, String[] playerWeapon) {
        super(user);
        this.playerWeapon = playerWeapon;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        //todo aggiorna la risorsa sul client

        return null;
    }
}
