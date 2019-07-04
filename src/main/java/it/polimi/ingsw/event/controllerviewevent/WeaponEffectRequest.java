package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message for the request to choose an effect of a weapon
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class WeaponEffectRequest extends ControllerViewEvent {

    /**
     * Are the available effects
     */
    private boolean[] availableEffect;

    /**
     * Constructor
     *
     * @param user            is the player username
     * @param availableEffect are the available effects
     */
    public WeaponEffectRequest(String user, boolean[] availableEffect) {
        super(user);
        this.availableEffect = availableEffect;
    }

    /**
     * Getter method
     *
     * @return the available effect
     */
    public boolean[] getAvailableEffect() {
        return availableEffect;
    }

    /**
     * performAction implementation: ask to the player which effect he want to use
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an WeaponEffectChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.weaponEffectChoice(getAvailableEffect());
    }
}
