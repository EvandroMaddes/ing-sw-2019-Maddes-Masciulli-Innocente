package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.RespawnRequestEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.GameTrack;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;

public class DeathManager {
    private Controller controller;
    private GameModel model;
    private Player deadPlayer;
    private RoundManager roundManager;

    public DeathManager(Controller controller, GameModel model, Player deadPlayer, RoundManager roundManager) {
        this.controller = controller;
        this.model = model;
        this.deadPlayer = deadPlayer;
        this.roundManager = roundManager;
    }

    public void manageKill(){
        controller.getGameManager().collectGameBoardPoints(deadPlayer);
        updateGameTrack();
        deadPlayer.getPlayerBoard().addSkull();
        deadPlayer.getPlayerBoard().resetDamages();
        respawnPlayer();
    }

    /**
     *
     *                   when the player send the square choice, controller call spawn()
     */
    public void respawnPlayer() {
        deadPlayer.addPowerUp((PowerUp) model.getGameboard().getPowerUpDeck().draw());
        controller.callView(new RespawnRequestEvent(deadPlayer.getUsername(), Encoder.encodePowerUpsType(deadPlayer.getPowerUps()), Encoder.encodePowerUpColour(deadPlayer.getPowerUps())));
    }

    public void spawn(String powerUp, CubeColour cardColour){
        PowerUp chosenPowerUp = null;
        for (PowerUp p: deadPlayer.getPowerUps()) {
            if (powerUp.equals(p.getName()) && cardColour == p.getColour() ){
                chosenPowerUp = p;
                break;
            }
        }
        for (SpawnSquare possibleSpawnSquare: model.getGameboard().getMap().getSpawnSquares()) {
            if (possibleSpawnSquare.getSquareColour().equals(chosenPowerUp.getColour().toString() ) ){
                deadPlayer.setPosition(possibleSpawnSquare);
                deadPlayer.discardPowerUp(chosenPowerUp);
            }
        }

        if (deadPlayer.isDead()) {
            deadPlayer.invertDeathState();
            roundManager.manageDeadPlayers();
        }
    }

    private void updateGameTrack(){
        GameTrack gameTrack = controller.getGameManager().getModel().getGameboard().getGameTrack();
        if (gameTrack.getSkullBox() > 0) {
            if (deadPlayer.getPlayerBoard().getDamageAmount() == 12) {
                gameTrack.getTokenSequence()[8 - gameTrack.getSkullBox()] = 2;
                gameTrack.evaluateDamage(deadPlayer.getPlayerBoard().getDamageReceived()[10], 2);
            } else {
                gameTrack.getTokenSequence()[8 - gameTrack.getSkullBox()] = 1;
                gameTrack.evaluateDamage(deadPlayer.getPlayerBoard().getDamageReceived()[10], 1);
            }
            gameTrack.removeSkull();
        }
    }

}
