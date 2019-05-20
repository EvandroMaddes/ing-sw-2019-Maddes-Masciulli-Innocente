package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;

import java.util.Map;

public class RespownRequestEvent extends PowerUpRequestEvent {

    public RespownRequestEvent(String user, Map<String, CubeColour> powerUp) {
        super(user, powerUp);
    }
}
