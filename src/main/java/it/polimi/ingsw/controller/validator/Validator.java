package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;


/**
 * This interface define the methods to get the action that players can perform in different situation and all the possible targets of those actions
 *
 * @author Federico Innocente
 */
public interface Validator {

    /**
     * Calculate all possible destination of a move action performed by the current player
     *
     * @param controller is teh controller of the game, with all the information about the map and the game itself
     * @return is a list of the possible destination of a move action performed by the current player
     */
    List<Square> availableMoves(Controller controller);

    /**
     * Calculate all possible squares that can be grab by the current player in only one grab action, both basic and spawn square.
     * Basic square with no ammo tile and empty spawn square or with not correctly payable weapons will not be count
     *
     * @param controller is the controller of the game, with all the information about the map and the game itself
     * @return a list of all possible destination of a correct
     */
    List<Square> availableGrab(Controller controller);

    /**
     * Find all the weapons of the player that can correctly fire.
     * Weapons must be load and be able to perform correctly at least one payable effect.
     *
     * @param player is the player who's weapon are searched
     * @return a list of all the player's weapon that can correctly fire
     */
    static ArrayList<Weapon> availableToFireWeapons(Player player) {
        ArrayList<Weapon> possibleWeapons = new ArrayList<>();
        for (int i = 0; i < player.getNumberOfWeapons(); i++) {
            if (player.getWeapons()[i].isUsable())
                possibleWeapons.add(player.getWeapons()[i]);
        }
        return possibleWeapons;
    }

    /**
     * Calculate which actions the current player can perform in his round
     *
     * @param controller is the controller of the game, with all the information about the map and the game state
     * @return a boolean vector, which true value define the usability of an action according with the following codification:
     * 0 - move action
     * 1 - grab action
     * 2 - shot action
     */
    boolean[] getUsableActions(Controller controller);

}
