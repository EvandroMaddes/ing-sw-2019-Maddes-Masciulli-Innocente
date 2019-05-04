package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class BaseActionValidator extends Validator {

    private static final int MAX_MOVES = 3;
    private static final int MAX_GRAB_MOVES = 1;

    /**
     *
     * @param player is the player that move
     * @return all possible destination
     */
    public ArrayList<Square> aviableMoves(Player player){
        return reachbleInMoves(player.getPosition(), MAX_MOVES);
    }

    /**
     *
     * @param player is the moving player
     * @return all possible grabbable square
     */
    public ArrayList<Square> avaiableGrab(Player player){
        ArrayList<Square> grabbableSquare = reachbleInMoves(player.getPosition(), MAX_GRAB_MOVES);
        for (Square currentSquare: grabbableSquare) {
            if (!currentSquare.isGrabbable())
                grabbableSquare.remove(currentSquare);
        }
        return grabbableSquare;
    }
}
