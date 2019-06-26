package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.EndRoundPowerUpRequestEvent;
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

    public RoundManager(Controller controller, Player currentPlayer){
        this.controller = controller;
        this.currentPlayer = currentPlayer;
        this.gameManager = controller.getGameManager();
        this.model = controller.getGameManager().getModel();
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
                actionManager = new ActionManager(controller);
                actionManager.askForPowerUpAsAction();
                break;
            }
            case 2:
            case 4:{
                actionManager = new ActionManager(controller);
                actionManager.askForAction();
                break;
            }
            case 6:{
                actionManager = new ActionManager(controller);
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


    private void endRound(){
        int deadPlayers = 0;
        for (Player p: controller.getGameManager().getModel().getPlayers()) {
            if(p.isDead())
                deadPlayers++;
        }
        if (deadPlayers > 1)
            currentPlayer.addPoints(1);
        if (gameManager.isFinalFrenzyPhase() && gameManager.getPlayerTurn() == gameManager.getLastPlayer())
            gameManager.endGame();
        else
            gameManager.newRound();
    }

    private void markDeadPlayer(){
        for (Player p: controller.getGameManager().getModel().getPlayers()){
            if (p.getPlayerBoard().getDamageAmount() > 10)
                p.setDead();
        }
    }

    /**
     * scroll every player and respown the first dead that he find
     */
    public void manageDeadPlayers(){
        boolean deadPlayerFound = false;
        for (Player p: model.getPlayers()) {
            if (p.isDead()){
                createDeathManager(model, p, this);
                deathManager.manageKill();
                deadPlayerFound = true;
                break;
            }
        }
        if (!deadPlayerFound)
            nextPhase();
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
        Player actualPlayer = null;
        while (iterator.hasNext() && usablePowerUp.isEmpty()){
            actualPlayer = (Player)iterator.next();
            if (actualPlayer.getTimesGetDamaged() > 0)
                for (PowerUp p: actualPlayer.getPowerUps()){
                    if (p.whenToUse() == PowerUp.Usability.END_TURN)
                        usablePowerUp.add(p);
            }
        }
        if (!usablePowerUp.isEmpty())
            askForEndRoundPowerUp(actualPlayer, usablePowerUp);
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

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getPhase() {
        return phase;
    }
}
