package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.board.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that define the methods to get the action that players can perform and all the possible targets of those actions in the case of final frenzy phase
 *
 * @author Federico Innocente
 */
public class FinalFrenzyValidator implements Validator {

    /**
     * Calculate all the possible destination of a move action, at most 4 square from the player starting position
     * Only players who play their round before the first one are allowed to perform this action.
     *
     * @param controller is teh controller of the game, with all the information about the map and the game itself
     * @return a list of all the possible destination
     */
    @Override
    public List<Square> availableMoves(Controller controller) {
        if (!controller.getGameManager().isFirsPlayerPlayed())
            return controller.getGameManager().getCurrentRound().getCurrentPlayer().getPosition().reachableInMoves(4);
        else throw new IllegalArgumentException("Player action not valid!");
    }

    /**
     * Calculate all possible squares that can be grab by the current player in only one grab action, both basic and spawn square.
     * Basic square with no ammo tile and empty spawn square or with not correctly payable weapons will not be count.
     * In case of frenetic actions the grab can be performed at most three moves away from the player current position for the round before the first player, at most two otherwise
     *
     * @param controller is the controller of the game, with all the information about the map and the game itself
     * @return a list of all possible destination of a correct grab action
     */
    @Override
    public List<Square> availableGrab(Controller controller) {
        int numberOfMoves;
        if (!controller.getGameManager().isFirsPlayerPlayed())
            numberOfMoves = 2;
        else
            numberOfMoves = 3;

        ArrayList<Square> possibleSquare = controller.getGameManager().getCurrentRound().getCurrentPlayer().getPosition().reachableInMoves(numberOfMoves);
        ArrayList<Square> grabbableSquare = new ArrayList<>();
        for (Square currentSquare : possibleSquare) {
            if (currentSquare.isGrabbable(controller.getGameManager().getCurrentRound().getCurrentPlayer()))
                grabbableSquare.add(currentSquare);
        }
        return grabbableSquare;
    }


    /**
     * Calculate which actions the current player can perform in his round.
     * Move action is allowed only in the round before the first player.
     * Grab action are always allowed
     * Shot can be performed only if the player has loaded weapon.
     * With an frenetic shot action the player will be allowed to perform a one square move before to shot: if after that the player wont be able to correctly fire, the action will be consider as a move action.
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
        if (controller.getGameManager().isFirsPlayerPlayed())
            usableActions[0] = false;
        return usableActions;
    }
}
