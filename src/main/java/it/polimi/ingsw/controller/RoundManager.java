package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.WinnerEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.player.Player;



public class RoundManager {

    private final Controller controller;
    protected final GameModel model;
    private final GameManager gameManager;
    private final Player currentPlayer;
    private ActionManager actionManager;
    private DeathManager deathManager;
    private int phase;

    public RoundManager(Controller controller, GameModel model, GameManager gameManager, Player currentPlayer){
        this.controller = controller;
        this.currentPlayer = currentPlayer;
        this.gameManager = gameManager;
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
                actionManager = new ActionManager(controller, model, this);
                actionManager.askForPowerUpAsAction();
                break;
            }
            case 2:
            case 4:{
                actionManager = new ActionManager(controller, model, this);
                actionManager.askForAction();
                break;
            }
            case 6:{
                actionManager = new ActionManager(controller, model, this);
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
        if (gameManager.isFinalFrenzyPhase() && gameManager.getPlayerTurn() == gameManager.getLastPlayer())
            controller.callView(new WinnerEvent(gameManager.calculateWinner().getUsername()));
        else
            gameManager.newRound();
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
        deathManager = new DeathManager(controller, model, deadPlayer, this);
    }
}
