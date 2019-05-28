package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.validator.*;
import it.polimi.ingsw.event.controller_view_event.*;
import it.polimi.ingsw.event.view_controller_event.CardChoiceEvent;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
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

    /**
     * move the player on target location. If it is a SpownSquare, send the possible grab weapon to let player choose one
     *
     * @param positionX column destination
     * @param positionY row destination
     */
    public void performGrab(int positionX, int positionY){
        currentRoundManager.getCurrentPlayer().setPosition( model.getGameboard().getMap().getSquareMatrix()[positionX][positionY] );
        if (model.getGameboard().getMap().getSpawnSquares().contains(currentRoundManager.getCurrentPlayer().getPosition())) {
            ArrayList<String> possibleGrabWeapons = new ArrayList<>();
            for (Weapon w:((SpawnSquare)currentRoundManager.getCurrentPlayer().getPosition()).getWeapons()) {
                if (currentRoundManager.getCurrentPlayer().canAffortCost(w.getReloadCost())){
                    possibleGrabWeapons.add(w.getName());
                }
            }
            WeaponGrabRequestEvent message = new WeaponGrabRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), possibleGrabWeapons);
            Controller.callView(message);
        }
        else {
            ((BasicSquare) currentRoundManager.getCurrentPlayer().getPosition()).grabAmmoTile(currentRoundManager.getCurrentPlayer());
            currentRoundManager.nextPhase();
        }
    }

    public void grabWeapon(String weaponChoice){
        SpawnSquare grabSquare = (SpawnSquare) currentRoundManager.getCurrentPlayer().getPosition();
        for (Weapon w: grabSquare.getWeapons()) {
            if (w.getName().equals(weaponChoice))
                currentRoundManager.getCurrentPlayer().addWeapon(w);
        }
        if (currentRoundManager.getCurrentPlayer().getNumberOfWeapons() > 3) {
            ArrayList<String> playerWeapons = new ArrayList<>();
            for (int i = 0; i < currentRoundManager.getCurrentPlayer().getNumberOfWeapons(); i++){
                playerWeapons.add(currentRoundManager.getCurrentPlayer().getWeapons()[i].getName());
                Controller.callView(new WeaponDiscardRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), playerWeapons));
            }
        }
        else
            currentRoundManager.nextPhase();
    }

    public void discardWeapon(String weapon){
        for (int i = 0; i < 4 && currentRoundManager.getCurrentPlayer().getNumberOfWeapons() > 3; i++){
            if (currentRoundManager.getCurrentPlayer().getWeapons()[i].getName().equals(weapon))
                currentRoundManager.getCurrentPlayer().discardWeapon(currentRoundManager.getCurrentPlayer().getWeapons()[i]);
        }
        currentRoundManager.nextPhase();
    }

    /**
     * send a message with all possible destination
     */
    public void sendPossibleMoves(){
        ArrayList<Square> possibleSquare = getValidator().aviableMoves(currentRoundManager.getCurrentPlayer());
        int[] possibleSquareX = new int[possibleSquare.size()];
        int[] possibleSquareY = new int[possibleSquare.size()];
        int i = 0;
        for (Square s:possibleSquare){
            possibleSquareX[i] = s.getColumn();
            possibleSquareY[i] = s.getRow();
            i++;
        }
        PositionMoveRequestEvent message = new PositionMoveRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), possibleSquareX, possibleSquareY);
        Controller.callView(message);
    }

    /**
     * send a message with possible grab square
     */
    public void sendPossibleGrabs(){
        ArrayList<Square> possibleSquare = getValidator().aviableGrab(currentRoundManager.getCurrentPlayer());
        int[] possibleSquareX = new int[possibleSquare.size()];
        int[] possibleSquareY = new int[possibleSquare.size()];
        int i = 0;
        for (Square s:possibleSquare){
            possibleSquareX[i] = s.getColumn();
            possibleSquareY[i] = s.getRow();
            i++;
        }
        PositionGrabRequestEvent message = new PositionGrabRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), possibleSquareX, possibleSquareY);
        Controller.callView(message);
    }

    public void sendPossibleWeapons(){
        Controller.callView( new WeaponRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), Encoder.encodeWeaponsList(getValidator().aviableToFireWeapons(currentRoundManager.getCurrentPlayer()))));
    }

    public void askForAction(){
        boolean ableToFire = currentRoundManager.getCurrentPlayer().canShot();
        Controller.callView(new ActionRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), ableToFire));
    }

    /**
     * reload phase: if the player has weapon unloaded which cost can be payed, pass them to the player. If the player can't do it, go to the next phase
     */
    public void askForReload(){
        ArrayList<String> possibleReload = new ArrayList<>();
        for (int i = 0 ; i < currentRoundManager.getCurrentPlayer().getNumberOfWeapons(); i++){
            Weapon currentWeapon = currentRoundManager.getCurrentPlayer().getWeapons()[i];
            if ( currentWeapon.isLoaded() && currentRoundManager.getCurrentPlayer().canAffortCost(currentWeapon.getReloadCost()) )
                possibleReload.add(currentRoundManager.getCurrentPlayer().getWeapons()[i].getName());
        }
        if ( !possibleReload.isEmpty() )
            Controller.callView(new WeaponReloadRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), possibleReload));
        else
            currentRoundManager.nextPhase();
    }

    /**
     * if the player has at least one Newton or Teleporter, send a PowerUpRequest message to ask if the player want to use it
     */
    public void askForPowerUp(){
        Map<String, CubeColour> usablePowerUps = new HashMap<>();
        for (PowerUp p: currentRoundManager.getCurrentPlayer().getPowerUps()) {
            if (p.getName().equals("Newton") || p.getName().equals("Teleporter"))
                usablePowerUps.put(p.getName(),p.getColour());
        }
        if (!usablePowerUps.isEmpty()) {
            // TODO: 2019-05-22 aggiornare il metodo qua sotto che era stato sminchiato dalla map
            //PowerUpRequestEvent message = new PowerUpRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), usablePowerUps);
            //Controller.callView(message);
        }
        else
            currentRoundManager.nextPhase();
    }

    /**
     *
     * @param weapon is the weapon that the player want to reload. After the reload, call again askFotReload().
     */
    public void reloadWeapon(String weapon){
        for (int i = 0; i < currentRoundManager.getCurrentPlayer().getNumberOfWeapons(); i++){
            if (currentRoundManager.getCurrentPlayer().getWeapons()[i].getName().equals(weapon))
                currentRoundManager.getCurrentPlayer().getWeapons()[i].setLoaded();
        }
        askForReload();
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
