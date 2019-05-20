package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

import java.util.Map;

public class PowerUpRequestEvent extends ControllerViewEvent {
    Map<String, CubeColour> powerUp;

    public PowerUpRequestEvent(String user, Map<String, CubeColour> powerUp) {
        super(user);
        this.powerUp = powerUp;
    }

    @Override
    public void performAction(RemoteView remoteView) {

    }
}
