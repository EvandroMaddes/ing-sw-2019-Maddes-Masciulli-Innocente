package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

public class EffectPaymentRequest extends PowerUpRequestEvent {
    /**
     * int arrays are built like that:
     * [0] - # Red
     * [1] - # Yellow
     * [2] - # Blue
     */
    private int[] minimumPowerUpRequest;
    private int[] maximumPowerUpRequest;

    public EffectPaymentRequest(String user, String[] powerUpNames, CubeColour[] powerUpColours,
                                int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        super(user, powerUpNames, powerUpColours);
        this.minimumPowerUpRequest = minimumPowerUpRequest;
        this.maximumPowerUpRequest = maximumPowerUpRequest;
    }

    //todo reimplentare
    @Override
    public Event performAction(RemoteView remoteView) {
        return super.performAction(remoteView);
    }
}
