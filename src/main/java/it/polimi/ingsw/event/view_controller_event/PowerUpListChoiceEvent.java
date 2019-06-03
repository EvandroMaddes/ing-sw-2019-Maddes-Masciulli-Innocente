package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;

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
