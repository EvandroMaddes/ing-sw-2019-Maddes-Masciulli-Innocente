package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;

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
    }

    public void manageKill(){
        //todo
    }
}
