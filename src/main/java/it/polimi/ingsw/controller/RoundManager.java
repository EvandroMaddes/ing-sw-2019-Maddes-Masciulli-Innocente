package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.player.Player;

import java.util.Iterator;


public class RoundManager {

    protected final GameModel model;
    private Player currentPlayer;

    public RoundManager(GameModel model, Player currentPlayer){
        this.currentPlayer = currentPlayer;
        this.model = model;
    }

    /**
     * A round is split in 6 phase: in 1,3,5 players can use their power up, in 2,4 they can perform actions and the 6th is used to reload
     */
    public void manageRound(){
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
}
