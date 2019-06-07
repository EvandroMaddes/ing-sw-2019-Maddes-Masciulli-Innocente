package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * @author federicoinnocente
 */
public class BaseActionValidator extends Validator {

    @Override
    /**
     *
     * @param player is the player that move
     * @return all possible destination
     */
    public ArrayList<Square> availableMoves(Controller controller){
        return reachableInMoves(controller.getGameManager().getCurrentRound().getCurrentPlayer().getPosition(), 3);
    }

    @Override
    /**
     *
     * @param player is the moving player
     * @return all possible grabbable square
     */
    public ArrayList<Square> availableGrab(Controller controller){
        ArrayList<Square> grabbableSquare = reachableInMoves(controller.getGameManager().getCurrentRound().getCurrentPlayer().getPosition(), 1);
        for (Square currentSquare: grabbableSquare) {
            if (!currentSquare.isGrabbable(controller.getGameManager().getCurrentRound().getCurrentPlayer()))
                grabbableSquare.remove(currentSquare);
        }
        return grabbableSquare;
    }

    @Override
    public boolean[] getUsableActions(Controller controller) {
        boolean[] usableActions = new boolean[]{true, true, true};
        usableActions[2] = !availableToFireWeapons(controller.getGameManager().getCurrentRound().getCurrentPlayer()).isEmpty();
        return usableActions;
    }
}
