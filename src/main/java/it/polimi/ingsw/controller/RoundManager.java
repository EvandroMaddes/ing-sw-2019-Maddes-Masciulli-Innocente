package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.EndRoundPowerUpRequestEvent;
import it.polimi.ingsw.event.controller_view_event.WinnerEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Decoder;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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
                endRoundPowerUpCheck();
                break;
            }
            case 8:{
                resetRoundDamageCounter();
                markDeadPlayer();
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

    private void markDeadPlayer(){
        for (Player p: controller.getGameManager().getModel().getPlayers()){
            if (p.getPlayerBoard().getDamageAmount() > 10)
                p.invertDeathState();
        }
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

    private void endRoundPowerUpCheck(){
        Iterator iterator = controller.getGameManager().getModel().getPlayers().iterator();
        ArrayList<PowerUp> usablePowerUp = new ArrayList<>();
        Player currentPlayer = null;
        while (iterator.hasNext() && usablePowerUp.isEmpty()){
            currentPlayer = (Player)iterator.next();
            if (currentPlayer.getTimesGetDamaged() > 0)
                for (PowerUp p: currentPlayer.getPowerUps()){
                    if (p.whenToUse() == PowerUp.Usability.END_TURN)
                        usablePowerUp.add(p);
            }
        }
        if (!usablePowerUp.isEmpty())
            askForEndRoundPowerUp(currentPlayer, usablePowerUp);
        else
            nextPhase();
    }

    private void askForEndRoundPowerUp(Player player, List<PowerUp> usablePowerUp){
        controller.callView(new EndRoundPowerUpRequestEvent(player.getUsername(), Encoder.encodePowerUpsType(usablePowerUp), Encoder.encodePowerUpColour(usablePowerUp), player.getTimesGetDamaged()));
    }

    public void performEndRoundPowerUpEffect(String powerUpOwner, String[] powerUpType, CubeColour[] powerUpColour){
        Player powerUpUser = Decoder.decodePlayerFromUsername(powerUpOwner, controller.getGameManager().getModel().getPlayers());
        ArrayList<PowerUp> toUsePowerUp = Decoder.decodePowerUpsList(powerUpUser, powerUpType, powerUpColour);
        for (PowerUp p:toUsePowerUp) {
            p.performEffect(currentPlayer);
            powerUpUser.discardPowerUp(p);
        }
        endRoundPowerUpCheck();
    }

    private void resetRoundDamageCounter(){
        for (Player p: controller.getGameManager().getModel().getPlayers()) {
            p.resetTimesGetDamaged();
        }
    }
}
