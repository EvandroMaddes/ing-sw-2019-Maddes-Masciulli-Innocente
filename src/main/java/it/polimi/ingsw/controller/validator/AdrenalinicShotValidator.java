package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.player.Player;

/**
 * Class that define the methods to get the action that players can perform and all the possible targets of those actions in the case of adrenalinic shot actions
 *
 * @author Federico Innocente
 */
public class AdrenalinicShotValidator extends AdrenalinicGrabValidator {

    /**
     * Calculate which actions the current player can perform in his round.
     * Move and grab are always allowed, while the shot can be performed only if the player has loaded weapon.
     * With an adrenalinic shot action the player will be allowed to perform a one square move before to shot: if after that the player wont be able to correctly fire, the action will be consider as a move action.zxzx
     * The usability of an action will be determinate by a boolean value of an array, with the following codification:
     * 0 - move actio
     * 1 - grab action
     * 2 - shot action
     *
     * @param controller is the controller of the game, with all the information about the map and the game state
     * @return a boolean vector, which true value define the usability of an action
     */
    @Override
    public boolean[] getUsableActions(Controller controller) {
        Player currentPlayer = controller.getGameManager().getCurrentRound().getCurrentPlayer();
        boolean hasLoadedWeapons = currentPlayer.getNumberOfWeapons() > 0;
        for (int i = 0; i < currentPlayer.getNumberOfWeapons(); i++) {
            if (currentPlayer.getWeapons()[i].isLoaded())
                hasLoadedWeapons = true;
        }
        return new boolean[]{true, true, hasLoadedWeapons};
    }
}
