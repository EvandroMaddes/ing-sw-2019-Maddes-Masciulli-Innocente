package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.validator.*;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.player.Player;

public class ActionManager {

    private GameModel model;
    private Player currentPlayer;
    private boolean actionUsed;
    private Validator actionValidator;

    public ActionManager(GameModel model, Player currentPlayer) {
        this.model = model;
        this.currentPlayer = currentPlayer;
        actionUsed = false;
    }

    public void askForAction(){
       //todo  xxxxx chiede quale azione usare
    }

    public void sendPossibleMove(){
        if (model.getGameboard().isFinalFrenzy()){
            actionValidator = new FinalFrenzyValidator();
        }
        else {
            switch (currentPlayer.getPlayerBoard().getAdrenalinicState()) {
                case 0: {
                    actionValidator = new BaseActionValidator();
                    break;
                }
                case 1: {
                    actionValidator = new AdrenalinicGrabValidator();
                    break;
                }
                case 2: {
                    actionValidator = new AdrenalinicShotValidator();
                    break;
                }
            }
        }
        /*notifica*/ actionValidator.avaibleMoves(currentPlayer);
    }

    public boolean isActionUsed() {
        return actionUsed;
    }
}
