package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.Map;

public class PlayerPowerUpUpdateEvent extends ModelViewEvent {
    private String[] powerUps;
    private CubeColour[] colours;
    private Character currCharacter;
    public PlayerPowerUpUpdateEvent(String user, Character currCharacter, String[] powerUps, CubeColour[] colours) {
        super(user);
        this.powerUps = powerUps;
        this.colours = colours;
        this.currCharacter = currCharacter;
    }

    public Character getCurrCharacter() {
        return currCharacter;
    }

    public String[] getPowerUps() {
        return powerUps;
    }

    public CubeColour[] getColours() {
        return colours;
    }

    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.playerPowerUpUpdate(getCurrCharacter(),getPowerUps(),getColours());
    }

}
