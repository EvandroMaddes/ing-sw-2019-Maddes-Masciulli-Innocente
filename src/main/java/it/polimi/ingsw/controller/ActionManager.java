package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.validator.*;
import it.polimi.ingsw.event.view_controller_event.CardChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.PositionChoiceEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;

public class ActionManager {

    private GameModel model;
    private RoundManager currentRoundManager;
    private boolean actionUsed;
    private PowerUp choosenPowerUp;
    private Weapon choosenWeapon;
    private boolean[] activatedEffects;

    public ActionManager(GameModel model, RoundManager currentRoundManager) {
        this.model = model;
        this.currentRoundManager = currentRoundManager;
        actionUsed = false;
    }

    public void askForAction(){
       // todo chiede quale azione usare
    }

    public void askForReload(){
        //todo chiede la ricarica
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

    public void sendPossibleMoves(){
        /*todo notifica*/
        getValidator().avaibleMoves(currentRoundManager.getCurrentPlayer());
    }

    public void sendPossibleGrabs(){
        /*todo notifica*/getValidator().avaibleGrab(currentRoundManager.getCurrentPlayer());
    }

    public void sendPossibleWeapons(){
        /*todo notifica*/getValidator().aviableToFireWeapons(currentRoundManager.getCurrentPlayer());
    }

    public void weaponChoice(CardChoiceEvent msg){
        /* choosenWeapon = weapon;
        if (choosenWeapon.canAttivateDifferentEffect()){
            sendPossibleOtherEffect();
        }
        else{
            choosenWeapon.fire();
        }*/
    }

    public void performMove(PositionChoiceEvent msg){
        currentRoundManager.getCurrentPlayer().setPosition( model.getGameboard().getMap().getSquareMatrix()[msg.getPositionX()][msg.getPositionY()] );
        /*todo notifica*/
        currentRoundManager.nextPhase();

    }

    public void performGrab(PositionChoiceEvent msg){
        currentRoundManager.getCurrentPlayer().setPosition( model.getGameboard().getMap().getSquareMatrix()[msg.getPositionX()][msg.getPositionY()] );
        if (model.getGameboard().getMap().getSpawnSquares().contains(currentRoundManager.getCurrentPlayer().getPosition())) {
            // sendWeaponGrabRequest();
            /*todo notifica*/
        }
        else {
            ((BasicSquare) currentRoundManager.getCurrentPlayer().getPosition()).grabAmmoTile(currentRoundManager.getCurrentPlayer());
            /*todo notifica*/
        }
    }

    public void grabWeapon(CardChoiceEvent msg){
        SpawnSquare grabSquare = (SpawnSquare) currentRoundManager.getCurrentPlayer().getPosition();
        for (Weapon w: grabSquare.getWeapons()) {
            if (w.getName() == msg.getCard())
                currentRoundManager.getCurrentPlayer().addWeapon(w);
        }
        if (currentRoundManager.getCurrentPlayer().getNumberOfWeapons() > 3) {
            // todo sendWeaponDiscardRequest();
        }
        else {
            /*todo notifica*/
        }
    }

    public void discardWeapon(CardChoiceEvent msg){
        for (int i = 0; i < 4 && currentRoundManager.getCurrentPlayer().getNumberOfWeapons() > 3; i++){
            if (currentRoundManager.getCurrentPlayer().getWeapons()[i].getName() == msg.getCard())
                currentRoundManager.getCurrentPlayer().discardWeapon(currentRoundManager.getCurrentPlayer().getWeapons()[i]);
            /*todo notifica*/
        }
    }



    public boolean isActionUsed() {
        return actionUsed;
    }

}
