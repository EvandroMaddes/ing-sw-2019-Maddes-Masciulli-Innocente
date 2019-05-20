package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.player.Player;

public class FrenzyRoundManager extends RoundManager {

    public FrenzyRoundManager(GameModel model, GameManager gameManager, Player currentPlayer, boolean afterFirstPlayer){
        super(model, gameManager, currentPlayer);
    }
}
