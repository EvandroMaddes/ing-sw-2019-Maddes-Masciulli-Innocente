package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;

import java.util.HashMap;
import java.util.Map;

public class DeathManager {
    private GameModel model;
    private Player deadPlayer;

    public DeathManager(GameModel model, Player deadPlayer) {
        this.model = model;
        this.deadPlayer = deadPlayer;
    }

    /**
     *
     * @param deadPlayer is the dead player that need to respawn
     *                   when the player send the square choice, controller call spawn()
     */
    public void respawnPlayer() {
        deadPlayer.addPowerUp((PowerUp) model.getGameboard().getPowerUpDeck().draw());
        //todo richiedi powerUp
    }

    public void spawn(String powerUp, String cardColour){
        Player deadPlayer = null;
        for (Player p: model.getPlayers()) {
            if (p.getUsername().equals(deadPlayer.getUsername())){
                deadPlayer = p;
                break;
            }
        }

        PowerUp choosenPowerUp = null;
        for (PowerUp p: deadPlayer.getPowerUps()) {
            if (powerUp.equals(p.getName()) && cardColour.equals(p.getColour().toString()) ){
                choosenPowerUp = p;
                break;
            }
        }
        for (SpawnSquare possibleSpawnSquare: model.getGameboard().getMap().getSpawnSquares()) {
            if (possibleSpawnSquare.getSquareColour().equals(choosenPowerUp.getColour().toString() ) ){
                deadPlayer.setPosition(possibleSpawnSquare);
                deadPlayer.discardPowerUp(choosenPowerUp);
            }
        }

        deadPlayer.invertDeathState();
        //todo bisogna fare in modo che quando un giocatore arriva a >10 danni si inverta lo stato di morte a true
    }

    public void manageKill(){
        givePoints();
        deadPlayer.getPlayerBoard().addSkull();
        deadPlayer.getPlayerBoard().resetDamages();
        respawnPlayer();
    }

    private void givePoints(){
        int[] damageDealed = {0,0,0,0,0};
        Player[] damageDealer = new Player[5];

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

            if (deadPlayer.getPlayerBoard().getSkullsNumber() + i < PlayerBoard.points.length)
                damageDealer[currentMaxDamager].addPoints(PlayerBoard.points[i + deadPlayer.getPlayerBoard().getSkullsNumber()]);
            else
                damageDealer[currentMaxDamager].addPoints(1);
        }
    }
}
