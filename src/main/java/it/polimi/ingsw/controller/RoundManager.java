package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.game_components.cards.Newton;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Teleporter;
import it.polimi.ingsw.model.player.Player;



public class RoundManager {

    protected final GameModel model;
    private Player currentPlayer;
    private int phase;
    private boolean actionUsed;
    protected boolean firstRoundOfTheGame = false;
    private ActionManager actionManager;

    public RoundManager(GameModel model, Player currentPlayer){
        this.currentPlayer = currentPlayer;
        this.model = model;
        phase = 1;
    }

    public void startRound(){
    }

    /**
     * A round is split in 6 phase: in 1,3,5 players can use their power up, in 2,4 they can perform actions and the 6th is used to reload
     */
    public void manageRound(){
        switch (phase){
            case 1: {
                askForPowerUp();
                break;
            }
            case 2:
            case 4:{
                phase++;
                actionManager = new ActionManager(model, currentPlayer);
            }
        }
    }


    public void askForPowerUp(){
        boolean canUsePowerUp = false;
        for (PowerUp p: currentPlayer.getPowerUps()) {
            if (p instanceof Newton || p instanceof Teleporter)
                canUsePowerUp = true;
        }
        phase++;
        if (canUsePowerUp)
            xxxxx lancia messaggio che chiede se vuole, usare un powerup
    }

    public void selectAction()
    {

    }

    public void managePoints()
    {

    }

    public void manageKill()
    {

    }

    public void respawn()
    {

    }

    public boolean[] checkAction()
    {
        boolean codedMacroAction[] = new boolean[5];
        codedMacroAction[5]=true;

        return codedMacroAction;
    }

    public void endRound()
    {

    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void spawnDeadPlayers(){

    }

    public ActionManager getActionManager() {
        return actionManager;
    }
}
