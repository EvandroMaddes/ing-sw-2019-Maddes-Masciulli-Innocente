package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message for the request of the use ofwhile action powerUps
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class WhileActionPowerUpRequestEvent extends PowerUpRequestEvent {

    /**
     * Constructor
     *
     * @param user           is the player username
     * @param powerUpNames   are teh possible powerUps type
     * @param powerUpColours are teh possible powerUps colours
     */
    public WhileActionPowerUpRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours) {
        super(user, powerUpNames, powerUpColours);
    }

    /**
     * performAction implementation: ask to the player if he want to ue while action powerUp
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an WhileActionPowerUpChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.whileActionPowerUpRequestEvent(getPowerUpNames(), getPowerUpColours());
    }
}
