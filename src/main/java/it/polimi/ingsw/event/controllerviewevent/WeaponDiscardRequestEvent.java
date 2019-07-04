package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

/**
 * Message for the request of a weapon discard
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class WeaponDiscardRequestEvent extends WeaponRequestEvent {

    /**
     * Constructor
     *
     * @param user    is the player username
     * @param weapons are the weapons name
     */
    public WeaponDiscardRequestEvent(String user, ArrayList<String> weapons) {
        super(user, weapons);
    }

    /**
     * performAction implementation: ask to the player which weapon he want to discard
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an WeaponDiscardChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.weaponDiscardChoice(getWeapons());
    }
}
