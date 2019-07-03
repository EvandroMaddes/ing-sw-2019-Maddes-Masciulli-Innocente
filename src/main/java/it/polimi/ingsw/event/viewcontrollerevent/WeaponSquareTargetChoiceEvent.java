package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

/**
 * Notify the target of a effect
 *
 * @author Federico Inncente
 */
public class WeaponSquareTargetChoiceEvent extends ViewControllerEvent {
    /**
     * Target row
     */
    private int x;
    /**
     * Target column
     */
    private int y;

    /**
     * Constructor
     *
     * @param user player username
     * @param x    target row
     * @param y    target column
     */
    public WeaponSquareTargetChoiceEvent(String user, int x, int y) {
        super(user);
        this.x = x;
        this.y = y;
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().performWeaponEffect(x, y);
    }
}

