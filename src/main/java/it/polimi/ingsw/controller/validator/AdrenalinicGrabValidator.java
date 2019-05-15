package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class AdrenalinicGrabValidator extends BaseActionValidator {

    @Override
    /**
     *
     * @param player is the moving player
     * @return all possible grabbable square
     */
    public ArrayList<Square> avaibleGrab(Player player){
        ArrayList<Square> grabbableSquare = reachbleInMoves(player.getPosition(), 2);
        for (Square currentSquare: grabbableSquare) {
            if (!currentSquare.isGrabbable())
                grabbableSquare.remove(currentSquare);
        }
        // todo controllare che le armi siano evenualmente pagabili (almeno 1)
        return grabbableSquare;
    }

}
