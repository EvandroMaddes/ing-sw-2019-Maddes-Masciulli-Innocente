package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

public class EndRoundPowerUpRequestEvent extends PowerUpRequestEvent {
    private int maxUsablePowerUps;

    public EndRoundPowerUpRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours, int maxUsablePowerUps) {
        super(user, powerUpNames, powerUpColours);
        this.maxUsablePowerUps = maxUsablePowerUps;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.endRoundPowerUpChoice(getPowerUpNames(),getPowerUpColours(),maxUsablePowerUps);
    }

    public int getMaxUsablePowerUps() {
        return maxUsablePowerUps;
    }
}
