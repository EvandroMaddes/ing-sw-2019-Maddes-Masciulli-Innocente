package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;


/**
 * @author federicoinnocente
 */
public abstract class Validator {


    /**
     * @param start         is the starting position
     * @param numberOfMoves is the numder of single move action between two squares
     * @return an arreyList of all the square reachable from the start, start included
     */
    public ArrayList<Square> reachbleInMoves(Square start, int numberOfMoves) {
        ArrayList<Square> reachableSquare = new ArrayList<>();
        ArrayList<Square> checkedSquare = new ArrayList<>();
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

    /**
     *
     * @param player is the player who move
     * @return all possible destination square
     */
    public abstract ArrayList<Square> avaibleMoves (Player player);

    /**
     *
     * @param player is the player who grab
     * @return all the square in which the player can grab
     */
    public abstract ArrayList<Square> avaibleGrab (Player player);

    /**
     *
     * @param weapon is the weapon with a player want to fire
     * @return a list of all possible target
     */
    public abstract ArrayList<Player> aviableShot (Weapon weapon);

}
