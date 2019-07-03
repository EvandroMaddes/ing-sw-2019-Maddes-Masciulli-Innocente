package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

public class AsActionPowerUpRequestEvent extends PowerUpRequestEvent {

    public AsActionPowerUpRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours) {
        super(user, powerUpNames, powerUpColours);
    }

    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.powerUpChoice(getPowerUpNames(),getPowerUpColours());
    }
}
