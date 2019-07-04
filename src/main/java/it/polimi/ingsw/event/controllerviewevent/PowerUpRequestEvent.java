package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

/**
 * Message for a generic powerUp request
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public abstract class PowerUpRequestEvent extends ControllerViewEvent {
    /**
     * Are all the possible powerUps type
     */
    private String[] powerUpNames;
    /**
     * Are all the possible powerUps colour
     */
    private CubeColour[] powerUpColours;

    /**
     * Constructor
     *
     * @param user           is teh player username
     * @param powerUpNames   Are all the possible powerUps type
     * @param powerUpColours Are all the possible powerUps colour
     */
    PowerUpRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours) {
        super(user);
        this.powerUpNames = powerUpNames;
        this.powerUpColours = powerUpColours;
    }

    /**
     * Getter method
     *
     * @return all the possible powerUps type
     */
    public String[] getPowerUpNames() {
        return powerUpNames;
    }

    /**
     * Getter method
     *
     * @return all the possible powerUps colour
     */
    public CubeColour[] getPowerUpColours() {
        return powerUpColours;
    }
}
