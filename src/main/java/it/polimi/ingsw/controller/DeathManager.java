package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.controller_view_event.RespawnRequestEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerBoard;

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
        deadPlayer.getPlayerBoard().addSkull();
        deadPlayer.getPlayerBoard().resetDamages();
        respawnPlayer();
    }

    /**
     *
     *                   when the player send the square choice, controller call spawn()
     */
    public void respawnPlayer() {
        ArrayList<String> powerUps = new ArrayList<>();
        ArrayList<CubeColour> colours = new ArrayList<>();
        deadPlayer.addPowerUp((PowerUp) model.getGameboard().getPowerUpDeck().draw());
        for (PowerUp p: deadPlayer.getPowerUps()){
            powerUps.add(p.getName());
            colours.add(p.getColour());
        }
    //    controller.callView(new RespawnRequestEvent(deadPlayer.getUsername(), powerUps, colours));
    }

    public void spawn(String powerUp, CubeColour cardColour){
        PowerUp choosenPowerUp = null;
        for (PowerUp p: deadPlayer.getPowerUps()) {
            if (powerUp.equals(p.getName()) && cardColour == p.getColour() ){
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

        if (deadPlayer.isDead()) {
            deadPlayer.invertDeathState();
            //todo bisogna fare in modo che quando un giocatore arriva a >10 danni si inverta lo stato di morte a true
            roundManager.manageDeadPlayers();
        }
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

            if (deadPlayer.getPlayerBoard().getSkullsNumber() + i < PlayerBoard.points.length)
                damageDealer[currentMaxDamager].addPoints(PlayerBoard.points[i + deadPlayer.getPlayerBoard().getSkullsNumber()]);
            else
                damageDealer[currentMaxDamager].addPoints(1);
        }
    }
}
