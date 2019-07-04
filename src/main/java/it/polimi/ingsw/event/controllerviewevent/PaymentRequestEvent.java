package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

/**
 * Generic message for the payment of an effect request
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public abstract class PaymentRequestEvent extends PowerUpRequestEvent {
    /**
     * Are the possible context of the pay
     */
    public enum Context {
        WEAPON_EFFECT,
        WEAPON_GRAB,
        WEAPON_RELOAD
    }

    /**
     * Minimum number of powerUps to use
     * int arrays are built like that:
     * [0] - # Red
     * [1] - # Yellow
     * [2] - # Blue
     */
    private int[] minimumPowerUpRequest;

    /**
     * Maximum number of powerUps to use
     */
    private int[] maximumPowerUpRequest;

    /**
     * Constructor
     *
     * @param user                  player username
     * @param powerUpNames          are the powerUps type
     * @param powerUpColours        are the powerUps colours
     * @param minimumPowerUpRequest Minimum number of powerUps to use
     * @param maximumPowerUpRequest Maximum number of powerUps to use
     */
    PaymentRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours,
                        int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        super(user, powerUpNames, powerUpColours);
        this.minimumPowerUpRequest = minimumPowerUpRequest;
        this.maximumPowerUpRequest = maximumPowerUpRequest;
    }

    /**
     * Getter method
     *
     * @return Minimum number of powerUps to use
     */
    public int[] getMinimumPowerUpRequest() {
        return minimumPowerUpRequest;
    }

    /**
     * Getter method
     *
     * @return Maximum number of powerUps to use
     */
    public int[] getMaximumPowerUpRequest() {
        return maximumPowerUpRequest;
    }
}
