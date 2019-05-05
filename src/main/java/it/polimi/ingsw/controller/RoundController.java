package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.action.Action;
import it.polimi.ingsw.model.player.Player;

public class RoundController {

    private Player currentPlayer;
    private Action currentAction;


    public RoundController(Player currentPlayer){
        this.currentPlayer = currentPlayer;
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
