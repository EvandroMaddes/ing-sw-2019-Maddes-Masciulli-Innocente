package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;

import java.util.ArrayList;


public abstract class Validator {


    /**
     * @param start         is the starting position
     * @param numberOfMoves is the numder of single move action between two squares
     * @return an arreyList of all the square reachable from the start, start included
     */
    public ArrayList<Square> reachbleInMoves(Square start, int numberOfMoves) {
        ArrayList<Square> reachableSquare = new ArrayList<Square>();
        ArrayList<Square> checkedSquare = new ArrayList<Square>();
        reachableSquare.add(start);

        for (int i = 1; i < numberOfMoves; i++) {
            for (Square currentSquare : reachableSquare) {
                for (int direction = 0; direction < currentSquare.getNearSquares().length && !checkedSquare.contains(currentSquare.getNearSquares()[direction]); direction++) {
                    if (currentSquare.getReachable()[i]) {
                        reachableSquare.add(currentSquare.getNearSquares()[i]);
                    }
                }
                checkedSquare.add(currentSquare);
            }
        }

        return reachableSquare;
    }


}
