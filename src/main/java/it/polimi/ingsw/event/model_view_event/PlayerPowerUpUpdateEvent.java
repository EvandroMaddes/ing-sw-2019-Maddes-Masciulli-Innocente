package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;

import java.util.Map;

public class PlayerPowerUpUpdateEvent extends ModelViewEvent {
    private Map<String, CubeColour> powerUps;

    public PlayerPowerUpUpdateEvent(String user, Map<String, CubeColour> powerUps) {
        super(user);
        this.powerUps = powerUps;
    }


}
