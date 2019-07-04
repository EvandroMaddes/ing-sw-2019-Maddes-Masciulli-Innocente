package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message for the request for the weapon reload pay modality
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class WeaponReloadPaymentRequestEvent extends PaymentRequestEvent {

    /**
     * Constructor
     *
     * @param user                  is the player username
     * @param powerUpNames          are the possible powerUps type to pay with
     * @param powerUpColours        are the possible powerUps colour to pay with
     * @param minimumPowerUpRequest are the minimum number to powerUps for each colour to chose
     * @param maximumPowerUpRequest are the maximum number to powerUps for each colour to chose
     */
    public WeaponReloadPaymentRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        super(user, powerUpNames, powerUpColours, minimumPowerUpRequest, maximumPowerUpRequest);
    }

    /**
     * performAction implementation: ask to the player how he want to pay a weapon reload
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an WeaponReloadPaymentChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.weaponReloadPaymentChoice(getPowerUpNames(), getPowerUpColours(), getMinimumPowerUpRequest(), getMaximumPowerUpRequest());
    }
}
