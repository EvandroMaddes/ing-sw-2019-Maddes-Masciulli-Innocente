package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class AdrenalinicGrabValidator extends BaseActionValidator {

    /**
     *
     * @return all possible grabbable square
     */
    public ArrayList<Square> availableGrab(Controller controller){
        ArrayList<Square> grabbableSquare = reachableInMoves(controller.getGameManager().getCurrentRound().getCurrentPlayer().getPosition(), 2);
        for (Square currentSquare: grabbableSquare) {
            if (!currentSquare.isGrabbable(controller.getGameManager().getCurrentRound().getCurrentPlayer()))
                grabbableSquare.remove(currentSquare);
        }
        return grabbableSquare;
    }

}
