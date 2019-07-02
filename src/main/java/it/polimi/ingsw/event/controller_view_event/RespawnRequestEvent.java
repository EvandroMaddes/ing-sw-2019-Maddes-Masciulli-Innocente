package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

public class RespawnRequestEvent extends PowerUpRequestEvent {

    public RespawnRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours) {
        super(user, powerUpNames, powerUpColours);
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.respawnChoice(getPowerUpNames(),getPowerUpColours());
    }
}
