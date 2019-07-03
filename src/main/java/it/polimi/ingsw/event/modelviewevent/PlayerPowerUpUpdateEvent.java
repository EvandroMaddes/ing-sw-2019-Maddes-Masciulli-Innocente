package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

public class PlayerPowerUpUpdateEvent extends ModelViewBroadcastEvent {
    private String[] powerUps;
    private CubeColour[] colours;
    private Character currCharacter;
    public PlayerPowerUpUpdateEvent(Character currCharacter, String[] powerUps, CubeColour[] colours) {
        super();
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
