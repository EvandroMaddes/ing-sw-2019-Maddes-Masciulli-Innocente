package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.board.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that define the methods to get the action that players can perform and all the possible targets of those actions in the case of nor frenetic or adrenalinic actions
 *
 * @author Federico Innocente
 */
public class BaseActionValidator implements Validator {

    /**
     * Calculate all the possible destination of a move
     *
     * @param controller is the controller of the game, with all the information about the map and the game itself
     * @return a list of all the possible destination of a moves
     */
    @Override
    public List<Square> availableMoves(Controller controller) {
        return controller.getGameManager().getCurrentRound().getCurrentPlayer().getPosition().reachableInMoves(3);
    }

    /**
     * Calculate all possible squares that can be grab by the current player in only one grab action, both basic and spawn square.
     * Basic square with no ammo tile and empty spawn square or with not correctly payable weapons will not be count.
     * In case of basic action the grab can be performed at most one moves from the player current position
     *
     * @param controller is the controller of the game, with all the information about the map and the game itself
     * @return a list of all possible destination of a correct grab action
     */
    @Override
    public List<Square> availableGrab(Controller controller) {
        ArrayList<Square> reachableSquare = controller.getGameManager().getCurrentRound().getCurrentPlayer().getPosition().reachableInMoves(1);
        ArrayList<Square> grabbableSquare = new ArrayList<>();
        for (Square currentSquare : reachableSquare) {
            if (currentSquare.isGrabbable(controller.getGameManager().getCurrentRound().getCurrentPlayer()))
                grabbableSquare.add(currentSquare);
        }
        return grabbableSquare;
    }

    /**
     * Calculate which actions the current player can perform in his round.
     * Move and grab are always allowed, while the shot can be performed only if the player has at least one loaded weapons which can perform correctly from his current position.
     * The usability of an action will be determinate by a boolean value of an array, with the following codification:
     * 0 - move action
     * 1 - grab action
     * 2 - shot action
     *
     * @param controller is the controller of the game, with all the information about the map and the game state
     * @return a boolean vector, which true value define the usability of an action
     */
    @Override
    public boolean[] getUsableActions(Controller controller) {
        boolean[] usableActions = new boolean[]{true, true, true};
        usableActions[2] = !Validator.availableToFireWeapons(controller.getGameManager().getCurrentRound().getCurrentPlayer()).isEmpty();
        return usableActions;
    }
}
