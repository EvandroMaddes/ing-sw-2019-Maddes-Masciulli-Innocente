package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

/**
 * Message sent to notify the position choice for the respawn
 *
 * @author Federico Innocente
 */
public class SpawnChoiceEvent extends CardChoiceEvent {
    /**
     * Is the colour of the chosen powerUp
     */
    private CubeColour cardColour;



    /**
     * Constructor
     *
     * @param user       is the player username
     * @param card       is the powerUp type
     * @param cardColour is the powerUp colour
     */
    public SpawnChoiceEvent(String user, String card, CubeColour cardColour) {
        super(user, card);
        this.cardColour = cardColour;
    }


    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getDeathManager().spawn(getCard(), cardColour);
    }
}
