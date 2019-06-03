package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.validator.*;
import it.polimi.ingsw.event.controller_view_event.*;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.utils.Decoder;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionManager {

    private Controller controller;
    private GameModel model;
    private RoundManager currentRoundManager;
    private PowerUp chosenPowerUp;
    private Weapon chosenWeapon;
    private int chosenEffect;

    public ActionManager(Controller controller, GameModel model, RoundManager currentRoundManager) {
        this.controller = controller;
        this.model = model;
        this.currentRoundManager = currentRoundManager;
    }

    /*
    *
    * GESTIONE POSSIBILI AZIONI
    *
     */

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

    public void askForAction(){
        boolean ableToFire = currentRoundManager.getCurrentPlayer().canShot();
        controller.callView(new ActionRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), ableToFire));
    }

    /*
    *
    * GESTIONE DEL MOVIMENTO
    *
     */

    /**
     * send a message with all possible destination
     */
    public void sendPossibleMoves(){
        ArrayList<Square> possibleSquare = getValidator().aviableMoves(currentRoundManager.getCurrentPlayer());
        int[] possibleSquareX = Encoder.encodeSquareTargetsX(possibleSquare);
        int[] possibleSquareY = Encoder.encodeSquareTargetsY(possibleSquare);
        PositionMoveRequestEvent message = new PositionMoveRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), possibleSquareX, possibleSquareY);
        controller.callView(message);
    }

    public void performMove(int positionX, int positionY){
        currentRoundManager.getCurrentPlayer().setPosition( model.getGameboard().getMap().getSquareMatrix()[positionX][positionY] );
        currentRoundManager.nextPhase();
    }

    /*
    *
    * GESTIONE GRAB
    *
     */

    /**
     * send a message with possible grab square
     */
    public void sendPossibleGrabs(){
        ArrayList<Square> possibleSquare = getValidator().aviableGrab(currentRoundManager.getCurrentPlayer());
        int[] possibleSquareX = Encoder.encodeSquareTargetsX(possibleSquare);
        int[] possibleSquareY = Encoder.encodeSquareTargetsY(possibleSquare);
        PositionGrabRequestEvent message = new PositionGrabRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), possibleSquareX, possibleSquareY);
        controller.callView(message);
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
            controller.callView(message);
        }
        else {
            ((BasicSquare) currentRoundManager.getCurrentPlayer().getPosition()).grabAmmoTile(currentRoundManager.getCurrentPlayer());
            currentRoundManager.nextPhase();
        }
    }

    /*
    *
    * GESTIONE AZIONE COMBATTIMENTO
    *
     */

    /**
     * Manda le possibili armi
     */
    public void sendPossibleWeapons(){
        controller.callView( new WeaponRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), Encoder.encodeWeaponsList(getValidator().aviableToFireWeapons(currentRoundManager.getCurrentPlayer()))));
    }

    /**
     * Salva l'arma scelta
     */
    public void saveWeapon(String weapon){
        for (int i = 0; i < currentRoundManager.getCurrentPlayer().getNumberOfWeapons(); i++){
            if (currentRoundManager.getCurrentPlayer().getWeapons()[i].getName().equals(weapon))
                chosenWeapon = currentRoundManager.getCurrentPlayer().getWeapons()[i];
        }
        sendPossibleEffects();
    }

    /**
     * manda i possibili effetti
     */
    public void sendPossibleEffects() {
        boolean[] usableEffects = new boolean[3];
        for (int i = 0; i < 3; i++)
            usableEffects[i] = chosenWeapon.isUsableEffect(i);
        if (Arrays.equals(usableEffects, new boolean[]{false, false, false}))
            currentRoundManager.nextPhase();
        else
            controller.callView(new WeaponEffectRequest(currentRoundManager.getCurrentPlayer().getUsername(), usableEffects));
    }

    /**
     * Richiede le modalità di pagamento
     */
    public void askForEffectPay(int chosenEffect){
        this.chosenEffect = chosenEffect;
        if (chosenWeapon.hasToPay(chosenEffect)) {
            int[] effectCost = AmmoCube.getColoursByAmmoCubeArrayRYB(chosenWeapon.getEffectCost(chosenEffect));
            if (effectCost[0] + effectCost[1] + effectCost[2] == 0)
                askForTargets();
            else
                askForPowerUpAsAmmo(effectCost, PaymentRequestEvent.Context.WEAPON_EFFECT);
        }
        else
            askForTargets();
    }

    /**
     * Paga l'effetto scelto
     */
    public void payEffect(String[] powerUpsType, CubeColour[] powerUpsColour){
        int[] effectCost = AmmoCube.getColoursByAmmoCubeArrayRYB(chosenWeapon.getEffectCost(chosenEffect));
        payCost(effectCost, Decoder.decodePowerUps(currentRoundManager.getCurrentPlayer(), powerUpsType, powerUpsColour));
        askForTargets();
    }

    /**
     * richiede i target per l'effetto
     */
    public void askForTargets(){
        ControllerViewEvent message = chosenWeapon.getTargetEffect(chosenEffect);
        if (message instanceof TargetPlayerRequestEvent && ((TargetPlayerRequestEvent)message).getMaxTarget() < 0)
            performWeaponEffect(new ArrayList<>());
        else
            controller.callView(message);
    }

    /**
     * Esegue l'effetto scelto
     */
    public void performWeaponEffect(List<Character> targetsLite){
        ArrayList<Object> targets = Decoder.decodePlayerListAsObject(targetsLite, controller.getGameManager().getModel().getPlayers());
        chosenWeapon.performEffect(chosenEffect, targets);
        sendPossibleEffects();
    }

    public void performWeaponEffect(int squareX, int squareY){
        ArrayList<Object> targets = new ArrayList<>();
        targets.add(Decoder.decodeSquare(squareX,squareY, controller.getGameManager().getModel().getGameboard().getMap()));
        chosenWeapon.performEffect(chosenEffect, targets);
        sendPossibleEffects();
    }


    /*
    *
    * GESTIONE RACCOLTA ARMI
    *
     */

    /**
     * Raccoglie l'arma
     */
    public void grabWeapon(String weaponChoice){
        SpawnSquare grabSquare = (SpawnSquare) currentRoundManager.getCurrentPlayer().getPosition();
        for (Weapon w: grabSquare.getWeapons()) {
            if (w.getName().equals(weaponChoice))
                chosenWeapon = w;
        }
        int[] grabCost = AmmoCube.getColoursByAmmoCubeArrayRYB(chosenWeapon.getReloadCost());
        if (chosenWeapon.getReloadCost()[0].getColour() == CubeColour.Red)
            grabCost[0]--;
        else if (chosenWeapon.getReloadCost()[0].getColour() == CubeColour.Yellow)
            grabCost[1]--;
        else
            grabCost[2]--;

        if (grabCost[0] + grabCost[1] + grabCost[2] == 0)
            manageWeaponLimit();
        else
            askForPowerUpAsAmmo(grabCost, PaymentRequestEvent.Context.WEAPON_GRAB);
    }

    /**
     * Paga l'effetto scelto
     */
    private void payWeaponGrab(String[] powerUpsType, CubeColour[] powerUpsColour){
        int[] grabCost = AmmoCube.getColoursByAmmoCubeArrayRYB(chosenWeapon.getEffectCost(chosenEffect));
        if (chosenWeapon.getReloadCost()[0].getColour() == CubeColour.Red)
            grabCost[0]--;
        else if (chosenWeapon.getReloadCost()[0].getColour() == CubeColour.Yellow)
            grabCost[1]--;
        else
            grabCost[2]--;
        payCost(grabCost, Decoder.decodePowerUps(currentRoundManager.getCurrentPlayer(), powerUpsType, powerUpsColour));
        manageWeaponLimit();
    }

    private void manageWeaponLimit(){
        if (currentRoundManager.getCurrentPlayer().getNumberOfWeapons() > 3) {
            ArrayList<String> playerWeapons = new ArrayList<>();
            for (int i = 0; i < currentRoundManager.getCurrentPlayer().getNumberOfWeapons(); i++) {
                playerWeapons.add(currentRoundManager.getCurrentPlayer().getWeapons()[i].getName());
                controller.callView(new WeaponDiscardRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), playerWeapons));
            }
        }
        else currentRoundManager.nextPhase();
    }

    /**
     * Scarta un arma
     */
    public void discardWeapon(String weapon){
        Weapon discardWeapon = Decoder.decodePlayerWeapon(currentRoundManager.getCurrentPlayer(), weapon);
        currentRoundManager.getCurrentPlayer().discardWeapon(discardWeapon);
        currentRoundManager.nextPhase();
    }

    /*
    *
    * GESTIONE RICARICA
    *
     */

    /**
     * reload phase: if the player has weapon unloaded which cost can be payed, pass them to the player. If the player can't do it, go to the next phase
     */
    public void askForReload(){
        ArrayList<String> possibleReload = new ArrayList<>();
        for (int i = 0 ; i < currentRoundManager.getCurrentPlayer().getNumberOfWeapons(); i++){
            Weapon currentWeapon = currentRoundManager.getCurrentPlayer().getWeapons()[i];
            if ( !currentWeapon.isLoaded() && currentRoundManager.getCurrentPlayer().canAffortCost(currentWeapon.getReloadCost()) )
                possibleReload.add(currentRoundManager.getCurrentPlayer().getWeapons()[i].getName());
        }
        if ( !possibleReload.isEmpty() )
            controller.callView(new WeaponReloadRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), possibleReload));
        else
            currentRoundManager.nextPhase();
    }

    /**
     * Richiede la modalità di pagamento dell'arma raccolta
     */
    public void askForWeaponReloadPay(String weapon){
        chosenWeapon = Decoder.decodePlayerWeapon(currentRoundManager.getCurrentPlayer(), weapon);
        int[] reloadCost = AmmoCube.getColoursByAmmoCubeArrayRYB(chosenWeapon.getReloadCost());
        if (reloadCost[0] + reloadCost[1] + reloadCost[2] == 0)
            reloadWeapon();
        else
            askForPowerUpAsAmmo(reloadCost, PaymentRequestEvent.Context.WEAPON_RELOAD);
    }

    /**
     * Chiama la ricarica dell'arma
     */
    private void payWeaponReload(String[] powerUpType, CubeColour[] powerUpColour){
        int[] reloadCost = AmmoCube.getColoursByAmmoCubeArrayRYB(chosenWeapon.getReloadCost());
        payCost(reloadCost, Decoder.decodePowerUps(currentRoundManager.getCurrentPlayer(), powerUpType, powerUpColour));
        reloadWeapon();
    }

    /**
     *
     *
     */
    private void reloadWeapon(){
        chosenWeapon.setLoaded();
        askForReload();
    }


    /*
    *
    * METODI PER GESTIRE I PAGAMENTI
    *
     */

    private void askForPowerUpAsAmmo(int[] cost, PaymentRequestEvent.Context context) {
        int[] playerAmmo = AmmoCube.getColoursByListRYB(currentRoundManager.getCurrentPlayer().getAmmo());
        CubeColour[] powerUpColoursLite = Encoder.encodePowerUpColour(currentRoundManager.getCurrentPlayer().getPowerUps());
        int[] powerUpsColours = AmmoCube.getColoursByCubeColourArrayRYB(powerUpColoursLite);
        int[] minimalPowerUpNumberToUse;
        if (!Arrays.equals(playerAmmo, AmmoCube.cubeDifference(playerAmmo, powerUpsColours)))
            if (context == PaymentRequestEvent.Context.WEAPON_EFFECT)
                payEffect(new String[]{}, new CubeColour[]{});
            else if (context == PaymentRequestEvent.Context.WEAPON_RELOAD)
                payWeaponReload(new String[]{}, new CubeColour[]{});
            else if (context == PaymentRequestEvent.Context.WEAPON_GRAB)
                payWeaponGrab(new String[]{}, new CubeColour[]{});
        else {
            minimalPowerUpNumberToUse = AmmoCube.cubeDifference(cost, playerAmmo);
            for (int i = 0; i < 3; i++) {
                if (minimalPowerUpNumberToUse[i] < 0)
                    minimalPowerUpNumberToUse[i] = 0;
            }
            if (context == PaymentRequestEvent.Context.WEAPON_EFFECT) {
                EffectPaymentRequestEvent message = new EffectPaymentRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(),
                        Encoder.encodePowerUpsType(currentRoundManager.getCurrentPlayer().getPowerUps()),
                        powerUpColoursLite, minimalPowerUpNumberToUse, cost);
                controller.callView(message);
            }
            else if (context == PaymentRequestEvent.Context.WEAPON_RELOAD) {
                WeaponReloadPaymentRequestEvent message = new WeaponReloadPaymentRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(),
                        Encoder.encodePowerUpsType(currentRoundManager.getCurrentPlayer().getPowerUps()),
                        powerUpColoursLite, minimalPowerUpNumberToUse, cost);
                controller.callView(message);
            }
            else if (context == PaymentRequestEvent.Context.WEAPON_GRAB) {
                WeaponGrabPaymentRequestEvent message = new WeaponGrabPaymentRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(),
                        Encoder.encodePowerUpsType(currentRoundManager.getCurrentPlayer().getPowerUps()),
                        powerUpColoursLite, minimalPowerUpNumberToUse, cost);
                controller.callView(message);
            }
        }
    }

    private void payCost(int[] cost, List<PowerUp> powerUps){
        for (PowerUp p: powerUps) {
            currentRoundManager.getCurrentPlayer().discardPowerUp(p);
            if (p.getColour() == CubeColour.Red)
                cost[0]--;
            else if (p.getColour() == CubeColour.Yellow)
                cost[1]--;
            else
                cost[2]--;
        }
        for (AmmoCube a:currentRoundManager.getCurrentPlayer().getAmmo()){
            if (a.getColour() == CubeColour.Red && cost[0] > 0){
                currentRoundManager.getCurrentPlayer().discardAmmo(a);
                cost[0]--;
            }
            else if (a.getColour() == CubeColour.Yellow && cost[1] > 0){
                currentRoundManager.getCurrentPlayer().discardAmmo(a);
                cost[1]--;
            }
            else if (a.getColour() == CubeColour.Blue && cost[2] > 0){
                currentRoundManager.getCurrentPlayer().discardAmmo(a);
                cost[2]--;
            }
        }
    }

























    /**
     * if the player has at least one Newton or Teleporter, send a PowerUpRequest message to ask if the player want to use it
     */
    public void askForPowerUp(){
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        for (PowerUp p: currentRoundManager.getCurrentPlayer().getPowerUps()) {
            if (p.getName().equals("Newton") || p.getName().equals("Teleporter"))
                powerUps.add(p);
        }
        if (!powerUps.isEmpty()) {
            PowerUpRequestEvent message = new PowerUpRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), Encoder.encodePowerUpsType(powerUps), Encoder.encodePowerUpColour(powerUps));
            controller.callView(message);
        }
        else
            currentRoundManager.nextPhase();
    }














    public void usePowerUp(String powerUpChoice, String powerUpColour) {
        // TODO: 2019-05-18
        for (PowerUp p : currentRoundManager.getCurrentPlayer().getPowerUps()) {
            if (p.getName().equals(powerUpChoice) && p.getColour().toString().equals(powerUpColour))
                chosenPowerUp = p;
        }
        Object targetList = chosenPowerUp.getTarget();
    }
}
