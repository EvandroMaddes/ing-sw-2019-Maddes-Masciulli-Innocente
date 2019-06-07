package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

public class AsActionPowerUprequestEvent extends PowerUpRequestEvent {

    public AsActionPowerUprequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours) {
        super(user, powerUpNames, powerUpColours);
    }

    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.powerUpChoice(getPowerUpNames(),getPowerUpColours());
    }
}
