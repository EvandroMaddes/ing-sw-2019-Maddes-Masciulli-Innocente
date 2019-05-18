package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.List;

public class AmmoUpdateEvent extends ModelViewEvent {
    private ArrayList<AmmoCube> ammo;

    public AmmoUpdateEvent(String user, List<AmmoCube> ammo) {
        super(user);
        this.ammo = (ArrayList<AmmoCube>) ammo;
    }

    @Override
    public void performAction(RemoteView remoteView) {

    }
}
