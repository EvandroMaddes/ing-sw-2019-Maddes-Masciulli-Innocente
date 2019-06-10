package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.List;

public class AmmoUpdateEvent extends ModelViewEvent {
    private ArrayList<AmmoCube> ammo;
    private Character currCharacter;
    public AmmoUpdateEvent(Character currCharacter, List<AmmoCube> ammo) {
        super("BROADCAST");
        this.currCharacter = currCharacter;
        this.ammo = (ArrayList<AmmoCube>) ammo;
    }

    public Character getCurrCharacter() {
        return currCharacter;
    }

    public ArrayList<AmmoCube> getAmmo() {
        return ammo;
    }

    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.playerAmmoUpdate(getCurrCharacter(),getAmmo());
    }
}
