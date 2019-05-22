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
     * @return an arrayList of all the square reachable from the start, start included
     */
    public ArrayList<Square> reachableInMoves(Square start, int numberOfMoves) {
        ArrayList<Square> reachAtPreviousStep = new ArrayList<>();
        ArrayList<Square> reachInThatStep = new ArrayList<>();
        ArrayList<Square> reachableSquares = new ArrayList<>();
        reachAtPreviousStep.add(start);
        reachableSquares.add(start);
        for (int i = 0; i < numberOfMoves; i ++){
            for (Square currentSquare: reachAtPreviousStep) {
                for (int direction = 0; direction < 4; direction++){
                    if(currentSquare.checkDirection(direction) && !reachAtPreviousStep.contains(currentSquare) && !reachInThatStep.contains(currentSquare)){
                        reachInThatStep.add(currentSquare.getNextSquare(direction));
                    }
                }
            }
            reachableSquares.addAll(reachInThatStep);
            reachAtPreviousStep.clear();
            reachAtPreviousStep.addAll(reachInThatStep);
            reachInThatStep.clear();
        }
        return reachableSquares;
    }


    /**
     *
     * @param player is the player who move
     * @return all possible destination square
     */
    public abstract ArrayList<Square> aviableMoves(Player player);

    /**
     *
     * @param player is the player who grab
     * @return all the square in which the player can grab
     */
    public abstract ArrayList<Square> aviableGrab(Player player);

    /**
     *
     * @param weapon is the weapon with a player want to fire
     * @return a list of all possible target
     */
    public abstract ArrayList<Player> aviableShot (Weapon weapon);


    /**
     *
     * @param player is the weapons owner
     * @return a list of all loaded weapons that can be used in that moment for a shot action
     */
    public ArrayList<Weapon> aviableToFireWeapons (Player player){
        // TODO: 2019-05-22
        return null;
    }

}
