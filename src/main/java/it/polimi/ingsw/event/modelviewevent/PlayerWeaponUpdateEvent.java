package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message to handle the update of the player's weapon
 *
 * @author Federico Inncente
 * @author Evandro Maddes
 */
public class PlayerWeaponUpdateEvent extends ModelViewBroadcastEvent {
    /**
     * Is the list of all the new player weapons names
     */
    private String[] playerWeapon;
    /**
     * The character that update his weapon
     */
    private Character currCharacter;
    /**
     * A boolean for every weapon to check if it is loaded
     */
    private boolean[] loadedWeapons;

    /**
     * Getter method
     *
     * @return the current character
     */
    private Character getCurrCharacter() {
        return currCharacter;
    }

    /**
     * Constructor
     *
     * @param currCharacter is the character who update his weapons
     * @param playerWeapon  are the name of the new weapons
     * @param loadedWeapons are flags for the loaded or less weapons
     */
    public PlayerWeaponUpdateEvent(Character currCharacter, String[] playerWeapon, boolean[] loadedWeapons) {
        super();
        this.playerWeapon = playerWeapon;
        this.currCharacter = currCharacter;
        this.loadedWeapons = loadedWeapons;
    }

    /**
     * Getter method
     *
     * @return the player's weapon names
     */
    private String[] getPlayerWeapon() {
        return playerWeapon;
    }

    /**
     * PerformAction implementation: handle the player weapon update
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.playerWeaponUpdate(getCurrCharacter(), getPlayerWeapon(), loadedWeapons);
    }
}
