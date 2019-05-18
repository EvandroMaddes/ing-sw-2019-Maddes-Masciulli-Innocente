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
    protected boolean firstRoundOfTheGame = false;
    private ActionManager actionManager;
    private DeathManager deathManager;

    public RoundManager(GameModel model, Player currentPlayer){
        this.currentPlayer = currentPlayer;
        this.model = model;
        phase = 1;
    }

    /**
     * A round is split in 6 phase: in 1,3,5 players can use their power up, in 2,4 they can perform actions and the 6th is used to reload
     */
    public void manageRound(){
        switch (phase){
            case 1:
            case 3:
            case 5:{
                actionManager = new ActionManager(model, this);
                actionManager.askForPowerUp();
                break;
            }
            case 2:
            case 4:{
                actionManager = new ActionManager(model, this);
                actionManager.askForAction();
                break;
            }
            case 6:{
                actionManager = new ActionManager(model, this);
                actionManager.askForReload();
                break;
            }
            case 7:{
                manageDeadPlayers();
                break;
            }
            default:
                endRound();
        }
    }

    public void nextPhase(){
        phase++;
        manageRound();
    }



    public void endRound(){
        //todo
    }

    /**
     * scroll every player and respown the first dead that he find
     */
    public void manageDeadPlayers(){
        for (Player p: model.getPlayers()) {
            if (p.isDead()){
                createDeathManager(model, p, this);
                deathManager.manageKill();
                break;
            }
        }
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public DeathManager getDeathManager() {
        return deathManager;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void createDeathManager(GameModel model, Player deadPlayer, RoundManager roundManager){
        deathManager = new DeathManager(model, deadPlayer, this);
    }
}
