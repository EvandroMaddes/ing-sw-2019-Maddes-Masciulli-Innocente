package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.view.RemoteView;

public class PlayerWeaponUpdateEvent extends ModelViewEvent {
    private String[] playerWeapon;

    public PlayerWeaponUpdateEvent(String user, String[] playerWeapon) {
        super(user);
        this.playerWeapon = playerWeapon;
    }

    @Override
    public void performAction(RemoteView remoteView) {

    }
}
