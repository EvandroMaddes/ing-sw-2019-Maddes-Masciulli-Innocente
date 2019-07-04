package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

/**
 * Message for the request of a weapon grab
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class WeaponGrabRequestEvent extends WeaponRequestEvent {

    /**
     * Constructor
     *
     * @param user    is teh player username
     * @param weapons are teh possible weapons name
     */
    public WeaponGrabRequestEvent(String user, ArrayList<String> weapons) {
        super(user, weapons);
    }

    /**
     * performAction implementation: ask to the player which weapon he want to grab
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an WeaponGrabChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.weaponGrabChoice(getWeapons());
    }
}
