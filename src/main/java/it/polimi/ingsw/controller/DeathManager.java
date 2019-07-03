package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controllerviewevent.RespawnRequestEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.GameTrack;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Decoder;
import it.polimi.ingsw.utils.Encoder;

/**
 * Class to manage the death and the respawn of the players
 *
 * @author Federico Innocente
 */
public class DeathManager {
    private Controller controller;
    private GameModel model;
    private Player deadPlayer;
    private RoundManager roundManager;

    /**
     * Constructor
     *
     * @param controller   il the controller of the game
     * @param deadPlayer   is the player that need to respawn
     * @param roundManager is the current round
     */
    DeathManager(Controller controller, Player deadPlayer, RoundManager roundManager) {
        this.controller = controller;
        this.model = controller.getGameManager().getModel();
        this.deadPlayer = deadPlayer;
        this.roundManager = roundManager;
    }

    /**
     * Method to manage the death of a player
     */
    void manageKill() {
        controller.getGameManager().collectPlayerBoardPoints(deadPlayer);
        updateGameTrack();
        deadPlayer.getPlayerBoard().addSkull();
        deadPlayer.getPlayerBoard().resetDamages();
        respawnPlayer();
    }

    /**
     * Give to the player a new powerUp and send him the respawn request
     */
    void respawnPlayer() {
        deadPlayer.addSpawnPowerUp((PowerUp) model.getGameboard().getPowerUpDeck().draw());
        controller.callView(new RespawnRequestEvent(deadPlayer.getUsername(), Encoder.encodePowerUpsType(deadPlayer.getPowerUps()), Encoder.encodePowerUpColour(deadPlayer.getPowerUps())));
    }

    /**
     * Spawn the player in the chosen spawn square
     *
     * @param powerUp    is the type of the powerUp chosen for the respawn
     * @param cardColour is the colour of the powerUp chosen for the respawn and of the chosen spawn square
     */
    public void spawn(String powerUp, CubeColour cardColour) {
        PowerUp chosenPowerUp = Decoder.decodePowerUp(deadPlayer, powerUp, cardColour);
        for (SpawnSquare possibleSpawnSquare : model.getGameboard().getMap().getSpawnSquares()) {
            if (possibleSpawnSquare.getSquareColour().equals(chosenPowerUp.getColour().toString())) {
                deadPlayer.setPosition(possibleSpawnSquare);
                deadPlayer.discardPowerUp(chosenPowerUp);
                model.getGameboard().getPowerUpDeck().discardCard(chosenPowerUp);
            }
        }
        if (deadPlayer.isDead()) {
            deadPlayer.invertDeathState();
            roundManager.manageDeadPlayers();
        } else
            controller.getGameManager().getCurrentRound().nextPhase();
    }

    /**
     * Update the game track, removing a skull and adding the killer tokens
     */
    private void updateGameTrack() {
        GameTrack gameTrack = controller.getGameManager().getModel().getGameboard().getGameTrack();
        if (gameTrack.getSkullBox() > 0) {
            if (deadPlayer.getPlayerBoard().getDamageAmount() == 11)
                gameTrack.evaluateDamage(deadPlayer.getPlayerBoard().getDamageReceived()[9], 2);
            else
                gameTrack.evaluateDamage(deadPlayer.getPlayerBoard().getDamageReceived()[9], 1);
        }
    }

}
