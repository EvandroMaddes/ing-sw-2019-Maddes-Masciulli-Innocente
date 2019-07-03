package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.board.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that define the methods to get the action that players can perform and all the possible targets of those actions in the case of adrenalinic grab state
 *
 * @author Federico Innocente
 */
public class AdrenalinicGrabValidator extends BaseActionValidator {

    /**
     * Calculate all possible squares that can be grab by the current player in only one grab action, both basic and spawn square.
     * Basic square with no ammo tile and empty spawn square or with not correctly payable weapons will not be count.
     * In case of adrenalinic action the grab can be performed at most two moves from the player current position
     *
     * @param controller is the controller of the game, with all the information about the map and the game itself
     * @return a list of all possible destination of a correct grab action
     */
    public List<Square> availableGrab(Controller controller) {
        ArrayList<Square> possibleSquare = controller.getGameManager().getCurrentRound().getCurrentPlayer().getPosition().reachableInMoves(2);
        ArrayList<Square> grabbableSquare = new ArrayList<>();
        for (Square currentSquare : possibleSquare) {
            if (currentSquare.isGrabbable(controller.getGameManager().getCurrentRound().getCurrentPlayer()))
                grabbableSquare.add(currentSquare);
        }
        return grabbableSquare;
    }

}
