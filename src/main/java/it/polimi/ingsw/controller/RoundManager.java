package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.player.Player;

public class RoundManager {

    private Player currentPlayer;


    public RoundManager(Player currentPlayer){
        this.currentPlayer = currentPlayer;
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

    /**
     * todo su currPlayer, calcola le MacroAzioni(ex puòMove, puòGrab), setta codedMacroAction[5]=true perchè indica Fineturno
     * può essere comodo inserire String macroType in Action per trovare subito damageContext e macroAction nella view
     * @return the boolean Array that will be incapsulated in ActionRequestEvent(la codifica è in quest'ultima classe)
     */
    public boolean[] checkAction()
    {
        boolean codedMacroAction[] = new boolean[5];
        codedMacroAction[5]=true;

        return codedMacroAction;
    }

    public void endRound()
    {

    }
}
