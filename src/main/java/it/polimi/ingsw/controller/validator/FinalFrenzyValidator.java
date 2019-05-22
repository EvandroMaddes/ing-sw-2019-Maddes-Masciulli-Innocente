package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class FinalFrenzyValidator extends Validator{

    @Override
    public ArrayList<Square> aviableMoves(Player player) {
        /* todo ********** ancora non c'è un modo per capire qual'è l'ordine dei giocatori, ma sarà implemetnato nel controller ******* */
        if (/*isBeforeFirstPlayer(player)*/ true)
            return reachableInMoves(player.getPosition(), 4);
        else throw new IllegalArgumentException("I giocatori dopo il primo non possono fare azioni di movimento in questa fase");
    }

    @Override
    public ArrayList<Square> aviableGrab(Player player) {
        int numberOfMoves;
        if (/*todo isBeforeFirstPlayer(player)*/ true)
            numberOfMoves = 2;
        else
            numberOfMoves = 3;

        ArrayList<Square> grabbableSquare = reachableInMoves(player.getPosition(), numberOfMoves);
        for (Square currentSquare: grabbableSquare) {
            if (!currentSquare.isGrabbable(player))
                grabbableSquare.remove(currentSquare);
        }
        return grabbableSquare;
    }

    @Override
    /* todo da implementare */
    public ArrayList<Player> aviableShot(Weapon weapon) {
        return null;
    }
}
