package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;

public class FirstRoundManager extends RoundManager {

    public FirstRoundManager(GameModel model, Player currentPlayer){
        super(model, currentPlayer);
        if (currentPlayer.isFirstPlayer())
            firstRoundOfTheGame = true;
    }

    @Override
    public void startRound() {
        getCurrentPlayer().addPowerUp((PowerUp)model.getGameboard().getPowerUpDeck().draw());
        respawn(getCurrentPlayer());
        super.manageRound();
    }
}
