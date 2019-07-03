package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

/**
 * Message to notify a generic choice of many powerUps
 *
 * @author Federico Innocente
 */
public abstract class PowerUpListChoiceEvent extends ViewControllerEvent {
    /**
     * is the chosen pouyerUps type
     */
    private String[] powerUpType;
    /**
     * Is the chosen powerUps colour
     */
    private CubeColour[] powerUpColour;

    /**
     * Constructor
     *
     * @param user          is the player username
     * @param powerUpType   are the powerUps type
     * @param powerUpColour are the powerUps colour
     */
    PowerUpListChoiceEvent(String user, String[] powerUpType, CubeColour[] powerUpColour) {
        super(user);
        this.powerUpType = powerUpType;
        this.powerUpColour = powerUpColour;
    }

    /**
     * Getter method
     *
     * @return the powerUps type
     */
    public String[] getPowerUpType() {
        return powerUpType;
    }

    /**
     * Getter method
     *
     * @return the powerUps colour
     */
    public CubeColour[] getPowerUpColour() {
        return powerUpColour;
    }
}
