package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

public class EndRoundPowerUpRequestEvent extends PowerUpRequestEvent {
    private int maxUsablePowerUps;

    public EndRoundPowerUpRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours, int maxUsablePowerUps) {
        super(user, powerUpNames, powerUpColours);
        this.maxUsablePowerUps = maxUsablePowerUps;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        // TODO: 2019-06-06
        return null;
    }
}
