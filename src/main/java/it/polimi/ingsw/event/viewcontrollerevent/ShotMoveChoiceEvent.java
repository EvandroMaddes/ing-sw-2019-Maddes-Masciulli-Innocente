package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

/**
 * Message to notify the move choice before a shot action
 *
 * @author Federico Innocente
 */
public class ShotMoveChoiceEvent extends PositionChoiceEvent {

    /**
     * Constructor
     *
     * @param user      is the player username
     * @param positionX is the destination's row
     * @param positionY is the destinations's column
     */
    public ShotMoveChoiceEvent(String user, int positionX, int positionY) {
        super(user, positionX, positionY);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().managePreEffectShot(getPositionX(), getPositionY());
    }
}
