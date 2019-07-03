package it.polimi.ingsw.event.viewcontrollerevent;


import it.polimi.ingsw.controller.Controller;

/**
 * Message to notify the controller about the grab square choice of a player
 *
 * @author Federico Innocente
 */
public class GrabChoiceEvent extends PositionChoiceEvent {

    /**
     * Constructor
     *
     * @param user      is the player username
     * @param positionX is the grab square row
     * @param positionY is the grab square column
     */
    public GrabChoiceEvent(String user, int positionX, int positionY) {
        super(user, positionX, positionY);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().performGrab(getPositionX(), getPositionY());
    }
}
