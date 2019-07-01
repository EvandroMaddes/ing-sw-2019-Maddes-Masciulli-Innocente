package it.polimi.ingsw.controller.validator;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.player.Player;

public class AdrenalinicShotValidator extends AdrenalinicGrabValidator {

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
