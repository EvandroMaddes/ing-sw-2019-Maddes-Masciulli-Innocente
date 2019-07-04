package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message to handle the change of the player's powerUps
 *
 * @author Federico Inncente
 * @author Evandro Maddes
 */
public class PlayerPowerUpUpdateEvent extends ModelViewBroadcastEvent {
    /**
     * Is the updated list of powerUps type
     */
    private String[] powerUps;

    /**
     * Is the updated list of powerUps colour
     */
    private CubeColour[] colours;

    /**
     * Is the character of the player that updated his powerUps
     */
    private Character currCharacter;

    /**
     * Constructor
     * @param currCharacter is the character who updated his powerUps
     * @param powerUps is the type of the powerUps
     * @param colours is the powerUps colour
     */
    public PlayerPowerUpUpdateEvent(Character currCharacter, String[] powerUps, CubeColour[] colours) {
        super();
        this.powerUps = powerUps;
        this.colours = colours;
        this.currCharacter = currCharacter;
    }

    /**
     * performAction implementation: handle the player's powerUps chance
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.playerPowerUpUpdate(currCharacter,powerUps,colours);
    }

}
