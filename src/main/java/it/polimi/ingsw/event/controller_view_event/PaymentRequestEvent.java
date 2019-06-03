package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public abstract class PaymentRequestEvent extends PowerUpRequestEvent {
    public enum Context{
        WEAPON_EFFECT,
        WEAPON_GRAB,
        WEAPON_RELOAD;
    }

    /**
     * int arrays are built like that:
     * [0] - # Red
     * [1] - # Yellow
     * [2] - # Blue
     */
    private int[] minimumPowerUpRequest;
    private int[] maximumPowerUpRequest;

    public PaymentRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours,
                                int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        super(user, powerUpNames, powerUpColours);
        this.minimumPowerUpRequest = minimumPowerUpRequest;
        this.maximumPowerUpRequest = maximumPowerUpRequest;
    }

    public int[] getMinimumPowerUpRequest() {
        return minimumPowerUpRequest;
    }

    public int[] getMaximumPowerUpRequest() {
        return maximumPowerUpRequest;
    }
}
