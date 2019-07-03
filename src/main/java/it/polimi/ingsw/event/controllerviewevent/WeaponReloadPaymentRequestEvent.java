package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

public class WeaponReloadPaymentRequestEvent extends PaymentRequestEvent {

    public WeaponReloadPaymentRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        super(user, powerUpNames, powerUpColours, minimumPowerUpRequest, maximumPowerUpRequest);
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.weaponReloadPaymentChoice(getPowerUpNames(),getPowerUpColours(),getMinimumPowerUpRequest(),getMaximumPowerUpRequest());
    }
}
