package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.Map;

public class RespawnRequestEvent extends PowerUpRequestEvent {

    public RespawnRequestEvent(String user, ArrayList<String> powerUpNames, ArrayList<CubeColour> powerUpColours) {
        super(user, powerUpNames, powerUpColours);
    }

    //TODO Anche per primo spawn,il metodo deve essere diverso dalla classe che estende
    @Override
    public Event performAction(RemoteView remoteView) {
        return super.performAction(remoteView);
    }
}
