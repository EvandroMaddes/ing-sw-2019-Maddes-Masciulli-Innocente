package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message for the payment of an effetc request
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class EffectPaymentRequestEvent extends PaymentRequestEvent {

    /**
     * Constructor
     * @param user the player username
     * @param powerUpNames the list of powerUps types he can use to pay
     * @param powerUpColours the list of powerUps colours
     * @param minimumPowerUpRequest the minimum number of powerUps to use for pay (Red - Yellow - Blue)
     * @param maximumPowerUpRequest the maximum number of powerUps to use for pay (Red - Yellow - Blue)
     */
    public EffectPaymentRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        super(user, powerUpNames, powerUpColours, minimumPowerUpRequest, maximumPowerUpRequest);
    }

    /**
     * performAction implementation: ask to the player which powerUps he want to use for pay an effect
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an EffectPaymentChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.weaponEffectPaymentChoice(getPowerUpNames(),getPowerUpColours(),getMinimumPowerUpRequest(),getMaximumPowerUpRequest());
    }
}
