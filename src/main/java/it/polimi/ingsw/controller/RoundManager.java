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

/**
 * The class that manage the flow of the rounds
 *
 * @author Federico Innocente
 */
public class RoundManager {

    private final Controller controller;
    protected final GameModel model;
    private final GameManager gameManager;
    private final Player currentPlayer;
    private ActionManager actionManager;
    private DeathManager deathManager;
    private int phase;

    public RoundManager(Controller controller, Player currentPlayer) {
        this.controller = controller;
        this.currentPlayer = currentPlayer;
        this.gameManager = controller.getGameManager();
        this.model = controller.getGameManager().getModel();
        phase = 1;
    }



    /*
     * Methods for the round flow manage
     */

    /**
     * Manage the round flow, based on the current phase.
     * A round is split in 8 phase:
     * - in 1,3,5 players can use their power up
     * - in 2,4 they can perform actions
     * - the 6th is used to reload weapons
     * - the 7th allowed all the players to use their end-round powerUP
     * - the 8th manage the dead players
     *
     */
    public void manageRound() {
        switch (phase) {
            case 1:
            case 3:
            case 5:
                actionManager = new ActionManager(controller);
                actionManager.askForPowerUpAsAction();
                break;
            case 2:
            case 4:
                actionManager = new ActionManager(controller);
                actionManager.askForAction();
                break;
            case 6:
                actionManager = new ActionManager(controller);
                actionManager.askForReload();
                break;
            case 7:
                endRoundPowerUpCheck();
                break;
            case 8:
                resetRoundDamageCounter();
                markDeadPlayer();
                manageDeadPlayers();
                break;
            default:
                endRound();
        }
    }

    /**
     * Pass to ne next round phase
     */
    public void nextPhase() {
        phase++;
        manageRound();
    }

    /**
     * End the current round.
     * If the game is over, manage it's closing; if it's not, start a new round
     */
    private void endRound() {
        int deadPlayers = 0;
        for (Player p : controller.getGameManager().getModel().getPlayers()) {
            if (p.isDead())
                deadPlayers++;
        }
        if (deadPlayers > 1)
            currentPlayer.addPoints(1);
        if (gameManager.isFinalFrenzyPhase() && gameManager.getPlayerTurn() == gameManager.getLastPlayer())
            gameManager.endGame();
        else
            gameManager.newRound();
    }



    /*
     * Methods for the dead-players respawn manage
     */

    /**
     * Mark the dead players for the respawn phase
     */
    private void markDeadPlayer() {
        for (Player p : controller.getGameManager().getModel().getPlayers()) {
            if (p.getPlayerBoard().getDamageAmount() >= 10)
                p.setDead();
        }
    }

    /**
     * Manage dead players to respawn them
     */
    void manageDeadPlayers() {
        boolean deadPlayerFound = false;
        for (Player p : model.getPlayers()) {
            if (p.isDead()) {
                createDeathManager(model, p);
                deathManager.manageKill();
                deadPlayerFound = true;
                break;
            }
        }
        if (!deadPlayerFound)
            nextPhase();
    }

    /**
     * Create a new deathManager to respawn a dead player
     * @param model is the GameModel of the game
     * @param deadPlayer is the dead player to respawn
     */
    void createDeathManager(GameModel model, Player deadPlayer) {
        deathManager = new DeathManager(controller, model, deadPlayer, this);
    }



    /*
     * Methods to manage the end-round powerUps
     */

    /**
     * Check for the players which can use end-game powerUps and ask them if they want to use them
     */
    private void endRoundPowerUpCheck() {
        Iterator<Player> iterator = controller.getGameManager().getModel().getPlayers().iterator();
        ArrayList<PowerUp> usablePowerUp = new ArrayList<>();
        Player actualPlayer = null;
        while (iterator.hasNext() && usablePowerUp.isEmpty()) {
            actualPlayer = iterator.next();
            if (actualPlayer.getTimesGetDamaged() > 0 && !gameManager.getDisconnectionManager().getDisconnectingQueue().contains(actualPlayer))
                for (PowerUp p : actualPlayer.getPowerUps()) {
                    if (p.whenToUse() == PowerUp.Usability.END_TURN)
                        usablePowerUp.add(p);
                }
        }
        if (actualPlayer != null && !usablePowerUp.isEmpty())
            askForEndRoundPowerUp(actualPlayer, usablePowerUp);
        else
            nextPhase();
    }

    /**
     * Send the end-round powerUps usage request to the player
     * @param player is the player who receive the request
     * @param usablePowerUp is a list of all powerUps usable in that phase
     */
    private void askForEndRoundPowerUp(Player player, List<PowerUp> usablePowerUp) {
        controller.callView(new EndRoundPowerUpRequestEvent(player.getUsername(), Encoder.encodePowerUpsType(usablePowerUp), Encoder.encodePowerUpColour(usablePowerUp), player.getTimesGetDamaged()));
    }

    /**
     * Perform the end-round powerUps effects
     * @param powerUpOwner is the username of the player who want to use the powerUps
     * @param powerUpType is a list of all the types of powerUps that the player want to use
     * @param powerUpColour is a list of colours associated with the types of powerUpType
     */
    public void performEndRoundPowerUpEffect(String powerUpOwner, String[] powerUpType, CubeColour[] powerUpColour) {
        Player powerUpUser = Decoder.decodePlayerFromUsername(powerUpOwner, controller.getGameManager().getModel().getPlayers());
        ArrayList<PowerUp> toUsePowerUp = Decoder.decodePowerUpsList(powerUpUser, powerUpType, powerUpColour);
        for (PowerUp p : toUsePowerUp) {
            p.performEffect(currentPlayer);
            powerUpUser.discardPowerUp(p);
        }
        endRoundPowerUpCheck();
    }

    /**
     * Reset the damage that every player received in this round
     */
    private void resetRoundDamageCounter() {
        for (Player p : controller.getGameManager().getModel().getPlayers()) {
            p.resetTimesGetDamaged();
        }
    }


    /*
     * Getter methods
     */

    /**
     * Getter method
     * @return the actual actionManager associated with the round
     */
    public ActionManager getActionManager() {
        return actionManager;
    }

    /**
     * Getter method
     * @return the deadManager associated with the current round
     */
    public DeathManager getDeathManager() {
        return deathManager;
    }

    /**
     * Getter method
     * @return the round's player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Getter method
     * @return the current phase
     */
    public int getPhase() {
        return phase;
    }

    /*
     * Setter Methods
     */

    /**
     * Setter method
     * @param phase is the phase to which is set the round
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }
}
