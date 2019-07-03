package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.utils.Decoder;

import java.util.ArrayList;

/**
 * Message sent by the player to notify his choice about a generic payment
 *
 * @author Federico Inncennte
 */
public class GenericPayChoiceEvent extends ViewControllerEvent {
    /**
     * The three boolean represents the choice of an ammo cube of tha three colour:
     * 0 - Red
     * 1 - Yellow
     * 2 - Blue
     */
    private boolean[] ammoChoice;
    /**
     * Is the chosen powerUp type
     */
    private String powerUpType;
    /**
     * Is the chosen powerUp colour
     */
    private CubeColour powerUpColour;

    /**
     * Constructor
     *
     * @param user          is the player username
     * @param ammoChoice    is the ammo choice (Red - Yellow - Blue). True value means teh choice
     * @param powerUpType   is the chosen powerUp type
     * @param powerUpColour is the chosen powerUp colour
     */
    public GenericPayChoiceEvent(String user, boolean[] ammoChoice, String powerUpType, CubeColour powerUpColour) {
        super(user);
        this.ammoChoice = ammoChoice;
        this.powerUpType = powerUpType;
        this.powerUpColour = powerUpColour;
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        if (ammoChoice[0])
            controller.getGameManager().getCurrentRound().getActionManager().payCost(new int[]{1, 0, 0}, new ArrayList<>());
        else if (ammoChoice[1])
            controller.getGameManager().getCurrentRound().getActionManager().payCost(new int[]{0, 1, 0}, new ArrayList<>());
        else if (ammoChoice[2])
            controller.getGameManager().getCurrentRound().getActionManager().payCost(new int[]{0, 0, 1}, new ArrayList<>());
        else {
            PowerUp choice = Decoder.decodePowerUp(controller.getGameManager().getCurrentRound().getCurrentPlayer(), powerUpType, powerUpColour);
            ArrayList<PowerUp> choiceAsList = new ArrayList<>();
            choiceAsList.add(choice);
            controller.getGameManager().getCurrentRound().getActionManager().payCost(new int[]{0, 0, 0}, choiceAsList);
        }
        controller.getGameManager().getCurrentRound().getActionManager().askTargetTargetingScope();
    }
}
