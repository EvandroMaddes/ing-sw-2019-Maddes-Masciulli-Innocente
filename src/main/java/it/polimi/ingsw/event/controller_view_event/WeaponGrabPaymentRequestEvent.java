package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

public class WeaponGrabPaymentRequestEvent extends PaymentRequestEvent {

    public WeaponGrabPaymentRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        super(user, powerUpNames, powerUpColours, minimumPowerUpRequest, maximumPowerUpRequest);
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.weaponGrabPaymentChoice(getPowerUpNames(),getPowerUpColours(),getMinimumPowerUpRequest(),getMaximumPowerUpRequest());
    }
}
