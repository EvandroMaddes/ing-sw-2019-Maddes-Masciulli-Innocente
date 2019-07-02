package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;

public class FirstRoundManager extends RoundManager {

    public FirstRoundManager(Controller controller, Player currentPlayer){
        super(controller, currentPlayer);
        setPhase(0);
    }

    @Override
    public void manageRound() {
        if (getPhase() == 0) {
            getCurrentPlayer().addPowerUp((PowerUp) model.getGameboard().getPowerUpDeck().draw());
            createDeathManager(model, getCurrentPlayer());
            getDeathManager().respawnPlayer();
        }
        else
            super.manageRound();
    }
}
