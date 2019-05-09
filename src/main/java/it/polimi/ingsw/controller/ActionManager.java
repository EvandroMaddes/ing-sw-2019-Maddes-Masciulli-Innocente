package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.validator.*;
import it.polimi.ingsw.event.view_controller_event.CardChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.PositionChoiceEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;

public class ActionManager {

    private GameModel model;
    private Player currentPlayer;
    private boolean actionUsed;
    private Weapon choosenWeapon;
    private boolean[] activatedEffects;

    public ActionManager(GameModel model, Player currentPlayer) {
        this.model = model;
        this.currentPlayer = currentPlayer;
        actionUsed = false;
    }

    public void askForAction(){
       // xxxxx chiede quale azione usare
    }

    public Validator getValidator(){
        Validator actionValidator;
        if (model.getGameboard().isFinalFrenzy()){
           actionValidator = new FinalFrenzyValidator();
        }
        else {
            switch (currentPlayer.getPlayerBoard().getAdrenalinicState()) {
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
        /*notifica*/getValidator().avaibleMoves(currentPlayer);
    }

    public void sendPossibleGrabs(){
        /*notifica*/getValidator().avaibleGrab(currentPlayer);
    }

    public void sendPossibleWeapons(){
        /*notifica*/getValidator().aviableToFireWeapons(currentPlayer);
    }

    /*public void weaponChoice(Weapon weapon){
        choosenWeapon = weapon;
        if (choosenWeapon.canAttivateDifferentEffect()){
            sendPossibleOtherEffect();
        }
        else{
            choosenWeapon.fire();
        }
    }*/

    public void performMove(PositionChoiceEvent msg){
        currentPlayer.setPosition( model.getGameboard().getMap().getSquareMatrix()[msg.getPositionX()][msg.getPositionY()] );
        /*notifica*/
    }

    public void performGrab(PositionChoiceEvent msg){
        currentPlayer.setPosition( model.getGameboard().getMap().getSquareMatrix()[msg.getPositionX()][msg.getPositionY()] );
        if (model.getGameboard().getMap().getSpawnSquares().contains(currentPlayer.getPosition())) {
            sendWeaponGrabRequest();
            /*notifica*/
        }
        else {
            ((BasicSquare) currentPlayer.getPosition()).grabAmmoTile(currentPlayer);
            /*notifica*/
        }
    }

    public void grabWeapon(CardChoiceEvent msg){
        SpawnSquare grabSquare = (SpawnSquare) currentPlayer.getPosition();
        for (Weapon w: grabSquare.getWeapons()) {
            if (w.getName() == msg.getCard())
                currentPlayer.addWeapon(w);
        }
        if (currentPlayer.getNumberOfWeapons() > 3)
            sendWeaponDiscardRequest();
        else
            /*notifica*/
    }

    public void discardWeapon(CardChoiceEvent msg){
        SpawnSquare discardSquare = (SpawnSquare) currentPlayer.getPosition();
        for (int i = 0; i < 4 && currentPlayer.getNumberOfWeapons() > 3; i++){
            if (currentPlayer.getWeapons()[i].getName() == msg.getCard())
                currentPlayer.discardWeapon(currentPlayer.getWeapons()[i]);
            /*notifica*/
        }
    }



    public boolean isActionUsed() {
        return actionUsed;
    }

}
