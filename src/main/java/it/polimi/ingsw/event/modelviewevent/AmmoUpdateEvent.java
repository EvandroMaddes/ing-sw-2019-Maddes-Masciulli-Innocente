package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.List;

/**
 * this class handle the update of the ammo that a player, identified by the character, has got
 * @author Federico Innocente
 */
public class AmmoUpdateEvent extends ModelViewBroadcastEvent {
    /**
     * Are the player ammo
     */
    private ArrayList<AmmoCube> ammo;
    /**
     * Is the character of the subject player
     */
    private Character currCharacter;

    /**
     * Constructor: call super-class constructor and set currCharacter and ammo values
     * @param currCharacter is the player character
     * @param ammo are th eplayer ammo
     */
    public AmmoUpdateEvent(Character currCharacter, List<AmmoCube> ammo) {
        super();
        this.currCharacter = currCharacter;
        this.ammo = (ArrayList<AmmoCube>) ammo;
    }

    /**
     * performAction implementation: handle the subject ammo update
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.playerAmmoUpdate(currCharacter, ammo);
    }
}
