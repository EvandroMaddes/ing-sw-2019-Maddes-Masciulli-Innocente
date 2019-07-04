package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

/**
 * Message for the request of the weapon to use for a shot action
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class WeaponRequestEvent extends ControllerViewEvent {
    private ArrayList<String> weapons;

    /**
     * Constructor
     *
     * @param user    is the player username
     * @param weapons are all the possible weapons name
     */
    public WeaponRequestEvent(String user, ArrayList<String> weapons) {
        super(user);
        this.weapons = weapons;
    }

    /**
     * Getter method
     *
     * @return all the possible weapons name
     */
    public ArrayList<String> getWeapons() {
        return weapons;
    }

    /**
     * performAction implementation: ask to the player which weapon he want to use for shot
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an WeaponChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.weaponChoice(getWeapons());
    }
}
