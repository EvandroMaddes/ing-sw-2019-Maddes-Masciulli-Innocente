package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.controller.Controller;
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
     * @return all possible destination square
     */
    public abstract ArrayList<Square> availableMoves(Controller controller);

    /**
     * @return all the square in which the player can grab
     */
    public abstract ArrayList<Square> availableGrab(Controller controller);

    /**
     *
     * @return a list of all loaded weapons that can be used in that moment for a shot action
     */
    public static ArrayList<Weapon> availableToFireWeapons(Player player){
        ArrayList<Weapon> possibleWeapons = new ArrayList<>();
        for (int i = 0; i < player.getNumberOfWeapons(); i++){
            if (player.getWeapons()[i].isUsable())
                possibleWeapons.add(player.getWeapons()[i]);
        }
        return possibleWeapons;
    }

    public abstract boolean[] getUsableActions(Controller controller);

}
