package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message for the request of the square to respawn on
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class RespawnRequestEvent extends PowerUpRequestEvent {

    /**
     * Constructor
     *
     * @param user           is the player username
     * @param powerUpNames   are the possible powerUps type
     * @param powerUpColours are the possible powerUps colours
     */
    public RespawnRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours) {
        super(user, powerUpNames, powerUpColours);
    }

    /**
     * performAction implementation: ask to the player where he want to respawn
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an RespawnChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.respawnChoice(getPowerUpNames(), getPowerUpColours());
    }
}
