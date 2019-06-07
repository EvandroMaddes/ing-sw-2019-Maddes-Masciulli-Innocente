package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public abstract class PowerUpRequestEvent extends ControllerViewEvent {
    private String[] powerUpNames;
    private CubeColour[] powerUpColours;

    public PowerUpRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours) {
        super(user);
        this.powerUpNames = powerUpNames;
        this.powerUpColours = powerUpColours;
    }

    public String[] getPowerUpNames() {
        return powerUpNames;
    }

    public CubeColour[] getPowerUpColours() {
        return powerUpColours;
    }
}
