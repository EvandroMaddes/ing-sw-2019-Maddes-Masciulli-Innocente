package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

public class WhileActionPowerUpRequestEvent extends PowerUpRequestEvent {

    public WhileActionPowerUpRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours) {
        super(user, powerUpNames, powerUpColours);
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.whileActionPowerUpRequestEvent(getPowerUpNames(),getPowerUpColours());
    }
}
