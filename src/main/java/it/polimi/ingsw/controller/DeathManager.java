package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.RespawnRequestEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.GameTrack;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Decoder;
import it.polimi.ingsw.utils.Encoder;

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
        controller.getGameManager().collectPlayerBoardPoints(deadPlayer);
        updateGameTrack();
        deadPlayer.getPlayerBoard().addSkull();
        deadPlayer.getPlayerBoard().resetDamages();
        respawnPlayer();
    }

    /**
     *
     * when the player send the square choice, controller call spawn()
     */
    public void respawnPlayer() {
        deadPlayer.getPowerUps().add((PowerUp) model.getGameboard().getPowerUpDeck().draw());
        controller.callView(new RespawnRequestEvent(deadPlayer.getUsername(), Encoder.encodePowerUpsType(deadPlayer.getPowerUps()), Encoder.encodePowerUpColour(deadPlayer.getPowerUps())));
    }

    public void spawn(String powerUp, CubeColour cardColour){
        PowerUp chosenPowerUp = Decoder.decodePowerUp(deadPlayer, powerUp, cardColour);
        for (SpawnSquare possibleSpawnSquare: model.getGameboard().getMap().getSpawnSquares()) {
            if (possibleSpawnSquare.getSquareColour().equals(chosenPowerUp.getColour().toString() ) ){
                deadPlayer.setPosition(possibleSpawnSquare);
                deadPlayer.discardPowerUp(chosenPowerUp);
            }
        }
        deadPlayer.discardPowerUp(chosenPowerUp);

        if (deadPlayer.isDead()) {
            deadPlayer.invertDeathState();
            roundManager.manageDeadPlayers();
        }
        else{
            controller.getGameManager().getCurrentRound().nextPhase();
        }
    }

    private void updateGameTrack(){
        GameTrack gameTrack = controller.getGameManager().getModel().getGameboard().getGameTrack();
        if (gameTrack.getSkullBox() > 0) {
            if (deadPlayer.getPlayerBoard().getDamageAmount() == 11)
                gameTrack.evaluateDamage(deadPlayer.getPlayerBoard().getDamageReceived()[9], 2);
            else
                gameTrack.evaluateDamage(deadPlayer.getPlayerBoard().getDamageReceived()[9], 1);
        }
    }

}
