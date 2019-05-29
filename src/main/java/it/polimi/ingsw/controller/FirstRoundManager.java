package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;

public class FirstRoundManager extends RoundManager {

    public FirstRoundManager(Controller controller, GameModel model, GameManager gameManager, Player currentPlayer){
        super(controller, model, gameManager, currentPlayer);
    }

    @Override
    public void manageRound() {
        getCurrentPlayer().addPowerUp((PowerUp)model.getGameboard().getPowerUpDeck().draw());
        createDeathManager(model, getCurrentPlayer(), this);
        getDeathManager().respawnPlayer();
        super.manageRound();
    }
}
