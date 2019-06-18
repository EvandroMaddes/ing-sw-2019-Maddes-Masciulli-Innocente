package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;

public class FirstRoundManager extends RoundManager {

    public FirstRoundManager(Controller controller, GameModel model, Player currentPlayer){
        super(controller, model, currentPlayer);
        setPhase(0);
    }

    @Override
    public void manageRound() {
        if (getPhase() == 0) {
            getCurrentPlayer().addPowerUp((PowerUp) model.getGameboard().getPowerUpDeck().draw());
            createDeathManager(model, getCurrentPlayer(), this);
            getDeathManager().respawnPlayer();
        }
        else
            super.manageRound();
    }
}
