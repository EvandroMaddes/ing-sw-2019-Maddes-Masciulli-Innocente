package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.validator.*;
import it.polimi.ingsw.event.controller_view_event.PowerUpRequestEvent;
import it.polimi.ingsw.event.view_controller_event.CardChoiceEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Newton;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Teleporter;
import it.polimi.ingsw.model.game_components.cards.Weapon;

import java.util.HashMap;
import java.util.Map;

public class ActionManager {

    private GameModel model;
    private RoundManager currentRoundManager;
    private PowerUp choosenPowerUp;
    private Weapon choosenWeapon;

    public ActionManager(GameModel model, RoundManager currentRoundManager) {
        this.model = model;
        this.currentRoundManager = currentRoundManager;
    }

    public Validator getValidator(){
        Validator actionValidator;
        if (model.getGameboard().isFinalFrenzy()){
           actionValidator = new FinalFrenzyValidator();
        }
        else {
            switch (currentRoundManager.getCurrentPlayer().getPlayerBoard().getAdrenalinicState()) {
                case 0: {
                    actionValidator = new BaseActionValidator();
                    break;
                }
                case 1: {
                    actionValidator = new AdrenalinicGrabValidator();
                    break;
                }
                case 2: {
                    actionValidator = new AdrenalinicShotValidator();
                    break;
                }
                default:{
                    throw new RuntimeException();
                }
            }
        }
        return actionValidator;
    }

    //todo
    public void weaponChoice(CardChoiceEvent msg){
        /* choosenWeapon = weapon;
        if (choosenWeapon.canAttivateDifferentEffect()){
            sendPossibleOtherEffect();
        }
        else{
            choosenWeapon.fire();
        }*/
    }

    public void performMove(int positionX, int positionY){
        currentRoundManager.getCurrentPlayer().setPosition( model.getGameboard().getMap().getSquareMatrix()[positionX][positionY] );
        currentRoundManager.nextPhase();
    }

    public void performGrab(int positionX, int positionY){
        currentRoundManager.getCurrentPlayer().setPosition( model.getGameboard().getMap().getSquareMatrix()[positionX][positionY] );
        if (model.getGameboard().getMap().getSpawnSquares().contains(currentRoundManager.getCurrentPlayer().getPosition())) {
            //sendWeaponGrabRequest();
        }
        else {
            ((BasicSquare) currentRoundManager.getCurrentPlayer().getPosition()).grabAmmoTile(currentRoundManager.getCurrentPlayer());
        }
    }

    public void grabWeapon(String weaponChoice){
        SpawnSquare grabSquare = (SpawnSquare) currentRoundManager.getCurrentPlayer().getPosition();
        for (Weapon w: grabSquare.getWeapons()) {
            if (w.getName().equals(weaponChoice))
                currentRoundManager.getCurrentPlayer().addWeapon(w);
        }
        if (currentRoundManager.getCurrentPlayer().getNumberOfWeapons() > 3) {
            // todo sendWeaponDiscardRequest();
        }
    }

    public void discardWeapon(String weapon){
        for (int i = 0; i < 4 && currentRoundManager.getCurrentPlayer().getNumberOfWeapons() > 3; i++){
            if (currentRoundManager.getCurrentPlayer().getWeapons()[i].getName().equals(weapon))
                currentRoundManager.getCurrentPlayer().discardWeapon(currentRoundManager.getCurrentPlayer().getWeapons()[i]);
        }
    }

    public void sendPossibleMoves(){
        /*todo notifica*/
        getValidator().avaibleMoves(currentRoundManager.getCurrentPlayer());
    }

    public void sendPossibleGrabs(){
        //todo notifica
        getValidator().avaibleGrab(currentRoundManager.getCurrentPlayer());
    }

    public void sendPossibleWeapons(){
        //todo notifica
        getValidator().aviableToFireWeapons(currentRoundManager.getCurrentPlayer());
    }

    public void askForAction(){
        // todo chiede quale azione usare
    }

    public void askForReload(){
        //todo chiede la ricarica
    }

    /**
     * if the player has at least one Newton or Teleporter, send a PowerUpRequest message to ask if the player want to use it
     */
    public void askForPowerUp(){
        Map<String, CubeColour> usablePowerUps = new HashMap<>();
        for (PowerUp p: currentRoundManager.getCurrentPlayer().getPowerUps()) {
            if (p instanceof Newton || p instanceof Teleporter)
                usablePowerUps.put(p.getName(),p.getColour());
        }
        if (!usablePowerUps.isEmpty()) {
            PowerUpRequestEvent message = new PowerUpRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), usablePowerUps);
            Controller.callView(message);
        }
        else
            currentRoundManager.nextPhase();
    }

    public void usePowerUp(String powerUpChoice, String powerUpColour) {
        // TODO: 2019-05-18
        for (PowerUp p : currentRoundManager.getCurrentPlayer().getPowerUps()) {
            if (p.getName().equals(powerUpChoice) && p.getColour().toString().equals(powerUpColour))
                choosenPowerUp = p;
        }
        Object targetList = choosenPowerUp.getTarget();
    }
}
