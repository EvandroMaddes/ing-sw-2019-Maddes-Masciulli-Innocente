package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

public class PlayerWeaponUpdateEvent extends ModelViewEvent {
    private String[] playerWeapon;
    private Character currCharacter;

    public Character getCurrCharacter() {
        return currCharacter;
    }

    public PlayerWeaponUpdateEvent(String user, String[] playerWeapon, Character currCharacter) {
        super(user);
        this.playerWeapon = playerWeapon;
        this.currCharacter = currCharacter;
    }

    public String[] getPlayerWeapon() {
        return playerWeapon;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        //todo aggiorna la risorsa sul client

        return remoteView.playerWeaponUpdate(getCurrCharacter(),getPlayerWeapon());
    }
}
