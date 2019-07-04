package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message for the end round powerUps request
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class EndRoundPowerUpRequestEvent extends PowerUpRequestEvent {
    /**
     * Max number of usable powerUps
     */
    private int maxUsablePowerUps;

    /**
     * Constructor
     *
     * @param user              is the player username
     * @param powerUpNames      are the powerUps types
     * @param powerUpColours    are the powerUps colours
     * @param maxUsablePowerUps is the maximum number of usable powerUps
     */
    public EndRoundPowerUpRequestEvent(String user, String[] powerUpNames, CubeColour[] powerUpColours, int maxUsablePowerUps) {
        super(user, powerUpNames, powerUpColours);
        this.maxUsablePowerUps = maxUsablePowerUps;
    }

    /**
     * performAction implementation: ask to the player which powerUps he want to use
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an EndRoundPowerUpChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.endRoundPowerUpChoice(getPowerUpNames(), getPowerUpColours(), maxUsablePowerUps);
    }

    /**
     * Getter method
     *
     * @return the max number of usable powerUps
     */
    public int getMaxUsablePowerUps() {
        return maxUsablePowerUps;
    }
}
