package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.board.Square;

import java.util.ArrayList;

public class FinalFrenzyValidator extends Validator{

    @Override
    public ArrayList<Square> availableMoves(Controller controller) {
        if (!controller.getGameManager().isFirsPlayerPlayed())
            return reachableInMoves(controller.getGameManager().getCurrentRound().getCurrentPlayer().getPosition(), 4);
        else throw new IllegalArgumentException("I giocatori dopo il primo non possono fare azioni di movimento in questa fase");
    }

    @Override
    public ArrayList<Square> availableGrab(Controller controller) {
        int numberOfMoves;
        if (!controller.getGameManager().isFirsPlayerPlayed())
            numberOfMoves = 2;
        else
            numberOfMoves = 3;

        ArrayList<Square> grabbableSquare = reachableInMoves(controller.getGameManager().getCurrentRound().getCurrentPlayer().getPosition(), numberOfMoves);
        for (Square currentSquare: grabbableSquare) {
            if (!currentSquare.isGrabbable(controller.getGameManager().getCurrentRound().getCurrentPlayer()))
                grabbableSquare.remove(currentSquare);
        }
        return grabbableSquare;
    }

    @Override
    public boolean[] getUsableActions(Controller controller) {
        boolean[] usableActions = new boolean[]{true, true, true};
        if (controller.getGameManager().isFirsPlayerPlayed())
            usableActions[0] = false;
        return usableActions;
    }
}
