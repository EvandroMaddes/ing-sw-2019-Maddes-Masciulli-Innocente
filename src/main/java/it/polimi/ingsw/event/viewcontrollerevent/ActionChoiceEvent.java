package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

/**
 * Event for the choice of the action by a player
 *
 * @author Federico Innocente
 * @author Francesco Masciulli
 */
public class ActionChoiceEvent extends ViewControllerEvent {

    /**
     * Is the chosen action
     */
    private int action;

    /**
     * Constructor: an action is chosen by the player with teh following codification:
     * 1 = move
     * 2 = grab
     * 3 = shot
     * Everything else: skip action
     *
     * @param user   is the player username
     * @param action is the chosen action encoding
     */
    public ActionChoiceEvent(String user, int action) {
        super(user);
        this.action = action;
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        switch (action) {
            case 1:
                controller.getGameManager().getCurrentRound().getActionManager().sendPossibleMoves();
                break;
            case 2:
                controller.getGameManager().getCurrentRound().getActionManager().sendPossibleGrabs();
                break;
            case 3:
                controller.getGameManager().getCurrentRound().getActionManager().manageShot();
                break;
            default:
                controller.getGameManager().getCurrentRound().nextPhase();
        }
    }
}
