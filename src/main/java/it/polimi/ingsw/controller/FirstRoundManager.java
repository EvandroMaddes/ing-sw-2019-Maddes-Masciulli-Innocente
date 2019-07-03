package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;

/**
 * Extension of the RoundManager to manage the first round of the players
 *
 * @author Federico Innocente
 */
public class FirstRoundManager extends RoundManager {

    /**
     * Constructor
     *
     * @param controller    is the controller of the game
     * @param currentPlayer is the player who play the round
     */
    FirstRoundManager(Controller controller, Player currentPlayer) {
        super(controller, currentPlayer);
        setPhase(0);
    }

    /**
     * Manage the round flow, adding a new starting phase to spawn the player
     */
    @Override
    public void manageRound() {
        if (getPhase() == 0) {
            getCurrentPlayer().addPowerUp((PowerUp) model.getGameboard().getPowerUpDeck().draw());
            createDeathManager(model, getCurrentPlayer());
            getDeathManager().respawnPlayer();
        } else
            super.manageRound();
    }
}
