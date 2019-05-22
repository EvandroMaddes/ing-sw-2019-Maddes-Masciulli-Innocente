package it.polimi.ingsw.controller.validator;

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
    public ArrayList<Square> aviableMoves(Player player){
        return reachableInMoves(player.getPosition(), 3);
    }

    @Override
    /**
     *
     * @param player is the moving player
     * @return all possible grabbable square
     */
    public ArrayList<Square> aviableGrab(Player player){
        ArrayList<Square> grabbableSquare = reachableInMoves(player.getPosition(), 1);
        for (Square currentSquare: grabbableSquare) {
            if (!currentSquare.isGrabbable(player))
                grabbableSquare.remove(currentSquare);
        }
        return grabbableSquare;
    }

    @Override
    public ArrayList<Player> aviableShot(Weapon weapon) {
        // TODO: 2019-05-20
        return null;
    }
}
