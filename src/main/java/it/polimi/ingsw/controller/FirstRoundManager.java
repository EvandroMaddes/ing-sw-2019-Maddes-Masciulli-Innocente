package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;

public class FirstRoundManager extends RoundManager {

    public FirstRoundManager(GameModel model, Player currentPlayer){
        super(model, currentPlayer);
    }

    @Override
    public void manageRound() {
        for (int i = 0; i < 2; i++){
            getCurrentPlayer().addPowerUp((PowerUp)model.getGameboard().getPowerUpDeck().draw());
        }
        super.manageRound();
    }
}
