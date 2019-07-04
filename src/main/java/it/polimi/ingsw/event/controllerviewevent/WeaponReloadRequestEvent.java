package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

/**
 * Message for the request of weapon reload
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class WeaponReloadRequestEvent extends WeaponRequestEvent {

    /**
     * Constructor
     *
     * @param user    is teh player username
     * @param weapons are teh possible weapons name
     */
    public WeaponReloadRequestEvent(String user, ArrayList<String> weapons) {
        super(user, weapons);
    }

    /**
     * performAction implementation: ask to the player which weapon he want to reload
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an WeaponReloadChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.reloadChoice(getWeapons());
    }

}
