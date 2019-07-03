package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

public abstract class PowerUpListChoiceEvent extends ViewControllerEvent {
    private String[] powerUpType;
    private CubeColour[] powerUpColour;

    public PowerUpListChoiceEvent(String user, String[] powerUpType, CubeColour[] powerUpColour) {
        super(user);
        this.powerUpType = powerUpType;
        this.powerUpColour = powerUpColour;
    }

    public String[] getPowerUpType() {
        return powerUpType;
    }

    public CubeColour[] getPowerUpColour() {
        return powerUpColour;
    }
}
