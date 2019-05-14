package it.polimi.ingsw.controller;

import it.polimi.ingsw.event.view_controller_event.CardChoiceEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.cards.Newton;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Teleporter;
import it.polimi.ingsw.model.player.Player;



public class RoundManager {

    protected final GameModel model;
    private Player currentPlayer;
    private int phase;
    protected boolean firstRoundOfTheGame = false;
    private ActionManager actionManager;

    public RoundManager(GameModel model, Player currentPlayer){
        this.currentPlayer = currentPlayer;
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
                askForPowerUp();
                break;
            }
            case 2:
            case 4:{
                actionManager = new ActionManager(model, this);
                actionManager.askForAction();
                break;
            }
            case 6:{
                actionManager = new ActionManager(model, this);
                actionManager.askForReload();
            }
        }
    }

    public void nextPhase(){
        phase++;
        manageRound();
    }


    public void askForPowerUp(){
        boolean canUsePowerUp = false;
        for (PowerUp p: currentPlayer.getPowerUps()) {
            if (p instanceof Newton || p instanceof Teleporter)
                canUsePowerUp = true;
        }
        phase++;
        //todo if (canUsePowerUp)
         //todo   xxxxx lancia messaggio che chiede se vuole, usare un powerup
    }

    public void selectAction()
    {
        //todo
    }

    public void managePoints()
    {
        //todo
    }

    public void manageKills() {
        //todo
    }

    public void respawnPlayer(Player deadPlayer) {
        deadPlayer.addPowerUp((PowerUp) model.getGameboard().getPowerUpDeck().draw());
        //todo notifica richista per il powerUp di respawn
    }

    //todo rivedere
    public void spawn(CardChoiceEvent msg){
        Player deadPlayer = null;
        for (Player p: model.getPlayers()) {
            if (p.getUsername().equals(msg.getUser())){
                deadPlayer = p;
            }
        }

        PowerUp choosenPowerUp = null;
        for (PowerUp p: deadPlayer.getPowerUps()) {
            if (msg.getCard().equals(p.getName()) && msg.getCardColour().equals(p.getColour().toString()) ){
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
        //todo notifica lo spown
    }

    public void endRound(){
        //todo
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void spawnDeadPlayers(){
        //todo
    }

    public ActionManager getActionManager() {
        return actionManager;
    }
}
