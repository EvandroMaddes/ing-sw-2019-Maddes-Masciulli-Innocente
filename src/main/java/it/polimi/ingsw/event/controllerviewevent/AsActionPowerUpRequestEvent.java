package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

/**
 * Event to ask to the player if and which powerUp they want to use as an action
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class AsActionPowerUpRequestEvent extends PowerUpRequestEvent {

    /**
     * Constructor
     * @param user is the username of the player to who send teh message
     * @param powerUpNames is an array of possible powerUps type
     * @param powerUpColours is an array of possible powerUps colour
     */
    public AsActionPowerUpRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours) {
        super(user, powerUpNames, powerUpColours);
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.powerUpChoice(getPowerUpNames(),getPowerUpColours());
    }
}
