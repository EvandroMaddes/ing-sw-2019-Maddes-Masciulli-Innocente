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
        givePoints();
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
        if (deadPlayer.getPlayerBoard().getDamageAmount() == 12) {
            gameTrack.getTokenSequence()[8 - gameTrack.getSkullBox()] = 2;
            gameTrack.evaluateDamage(deadPlayer.getPlayerBoard().getDamageReceived()[10], 2);
        }
        else {
            gameTrack.getTokenSequence()[8 - gameTrack.getSkullBox()] = 1;
            gameTrack.evaluateDamage(deadPlayer.getPlayerBoard().getDamageReceived()[10], 1);
        }
        gameTrack.removeSkull();
    }

    private void givePoints(){
        int[] damageDealed = new int[model.getPlayers().size()];
        Player[] damageDealer = new Player[model.getPlayers().size()];

        for (int i = 0; i < damageDealed.length; i++)
            damageDealed[i] = 0;

        if (deadPlayer.getPlayerBoard().getDamageAmount() == 0)
            return;
        //aggiunge il punto della prima kill
        deadPlayer.getPlayerBoard().getDamageReceived()[0].getPlayer().addPoints(1);

        //aggiunge i punti in base a chi ha fatto più danni e al numero di teschi
        for (DamageToken d: deadPlayer.getPlayerBoard().getDamageReceived()) {
            int i = 0;
            while (damageDealed[i] != 0 && damageDealer[i] != d.getPlayer())
                i++;
            if (damageDealed[i] == 0)
                damageDealer[i] = d.getPlayer();
            damageDealed[i]++;
        }

        int max = 0;
        for(int i = 0; i < damageDealed.length && damageDealed[i] > 0; i++){
            for(int j = 0; j < damageDealed.length && damageDealed[j] > 0; j++) {
                if (damageDealed[j] > max && damageDealed[j] <= 12)
                    max = damageDealed[j];
            }
            int currentMaxDamager = 0;
            while (damageDealed[currentMaxDamager] != max)
                currentMaxDamager++;
            damageDealed[currentMaxDamager] = 100;

            if (deadPlayer.getPlayerBoard().getSkullsNumber() + i < PlayerBoard.POINTS.length)
                damageDealer[currentMaxDamager].addPoints(PlayerBoard.POINTS[i + deadPlayer.getPlayerBoard().getSkullsNumber()]);
            else
                damageDealer[currentMaxDamager].addPoints(1);
        }
    }
}
