package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

public class PlayerWeaponUpdateEvent extends ModelViewBroadcastEvent {
    private String[] playerWeapon;
    private Character currCharacter;
    private boolean[] loadedWeapons;

    public Character getCurrCharacter() {
        return currCharacter;
    }

    public PlayerWeaponUpdateEvent(Character currCharacter, String[] playerWeapon, boolean[] loadedWeapons) {
        super();
        this.playerWeapon = playerWeapon;
        this.currCharacter = currCharacter;
        this.loadedWeapons = loadedWeapons;
    }

    public String[] getPlayerWeapon() {
        return playerWeapon;
    }

    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.playerWeaponUpdate(getCurrCharacter(),getPlayerWeapon(), loadedWeapons);
    }
}
