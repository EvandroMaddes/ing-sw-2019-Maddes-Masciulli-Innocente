package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.validator.*;
import it.polimi.ingsw.event.controllerviewevent.*;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.model.gamecomponents.cards.Weapon;
import it.polimi.ingsw.model.gamecomponents.cards.powerups.Newton;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Decoder;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to manage the possible actions in a round
 *
 * @author Federico Inncoente
 */
public class ActionManager {

    /**
     * Is the game controller
     */
    private Controller controller;
    /**
     * Is the game model
     */
    private GameModel model;
    /**
     * The manager of the current round
     */
    private RoundManager currentRoundManager;
    /**
     * When a player use a powerUp, it is saved to allowed to perform different requests to the client
     */
    private PowerUp chosenPowerUp;
    /**
     * When a player use a weapon, it is saved to allowed to perform different requests to the client
     */
    private Weapon chosenWeapon;
    /**
     * When a player use an effect, it is saved to allowed to perform different requests to the client
     */
    private int chosenEffect;
    /**
     * Flag to determinate the reload phase
     */
    private boolean reloadPhase;

    /**
     * Constructor
     *
     * @param controller is the controller of the game
     */
    ActionManager(Controller controller) {
        this.controller = controller;
        this.model = controller.getGameManager().getModel();
        this.currentRoundManager = controller.getGameManager().getCurrentRound();
        this.reloadPhase = false;
    }

    /*
     *
     * Manage of possible actions choice
     *
     */

    /**
     * Get the validator for the current player and the game situation
     *
     * @return the correct validator for the current player
     */
    private Validator getValidator() {
        Validator actionValidator;
        if (model.getGameboard().isFinalFrenzy()) {
            actionValidator = new FinalFrenzyValidator();
        } else {
            switch (currentRoundManager.getCurrentPlayer().getPlayerBoard().getAdrenalinicState()) {
                case 0:
                    actionValidator = new BaseActionValidator();
                    break;
                case 1:
                    actionValidator = new AdrenalinicGrabValidator();
                    break;
                case 2:
                    actionValidator = new AdrenalinicShotValidator();
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
        return actionValidator;
    }

    /**
     * Send to the player the request for the action to use
     */
    void askForAction() {
        controller.callView(new ActionRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), getValidator().getUsableActions(controller)));
    }

    /*
     *
     * Move action manage
     *
     */

    /**
     * Send to the player the possible destination for a move action
     */
    public void sendPossibleMoves() {
        ArrayList<Square> possibleSquare = (ArrayList<Square>) getValidator().availableMoves(controller);
        int[] possibleSquareX = Encoder.encodeSquareTargetsX(possibleSquare);
        int[] possibleSquareY = Encoder.encodeSquareTargetsY(possibleSquare);
        PositionMoveRequestEvent message = new PositionMoveRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), possibleSquareX, possibleSquareY);
        controller.callView(message);
    }

    /**
     * Perform the move action of a player
     *
     * @param positionX is the row of the destination
     * @param positionY is the column of the destination
     */
    public void performMove(int positionX, int positionY) {
        currentRoundManager.getCurrentPlayer().setPosition(model.getGameboard().getMap().getSquareMatrix()[positionX][positionY]);
    }

    /*
     *
     * Grab action manage
     *
     */

    /**
     * Send to the player the possible destination of a grab action
     */
    public void sendPossibleGrabs() {
        ArrayList<Square> possibleSquare = (ArrayList<Square>) getValidator().availableGrab(controller);
        int[] possibleSquareX = Encoder.encodeSquareTargetsX(possibleSquare);
        int[] possibleSquareY = Encoder.encodeSquareTargetsY(possibleSquare);
        PositionGrabRequestEvent message = new PositionGrabRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), possibleSquareX, possibleSquareY);
        controller.callView(message);
    }

    /**
     * Move the player on target location of the grab action.
     * If the destination is a basic square, grab the ammo tile.
     * If the destination is a spawn square, send a request to the player with the weapons he can grab
     *
     * @param positionX is the row of the destination
     * @param positionY is the column of the destination
     */
    public void performGrab(int positionX, int positionY) {
        currentRoundManager.getCurrentPlayer().setPosition(model.getGameboard().getMap().getSquareMatrix()[positionX][positionY]);
        if (model.getGameboard().getMap().getSpawnSquares().contains(currentRoundManager.getCurrentPlayer().getPosition())) {
            ArrayList<String> possibleGrabWeapons = new ArrayList<>();
            for (Weapon w : ((SpawnSquare) currentRoundManager.getCurrentPlayer().getPosition()).getWeapons()) {
                if (currentRoundManager.getCurrentPlayer().canAffortCost(w.getGrabCost())) {
                    possibleGrabWeapons.add(w.getName());
                }
            }
            WeaponGrabRequestEvent message = new WeaponGrabRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), possibleGrabWeapons);
            controller.callView(message);
        } else {
            ((BasicSquare) currentRoundManager.getCurrentPlayer().getPosition()).grabAmmoTile(currentRoundManager.getCurrentPlayer());
            currentRoundManager.nextPhase();
        }
    }

    /*
     *
     * Shot action manage
     *
     */

    /**
     * Manage the request of a shot action: if the player can perform a move, send the possible destination, otherwise send the possible weapon to shot with
     */
    public void manageShot() {
        if (controller.getGameManager().isFinalFrenzyPhase() || currentRoundManager.getCurrentPlayer().getPlayerBoard().getAdrenalinicState() == 2) {
            ArrayList<Square> possibleDestination = currentRoundManager.getCurrentPlayer().getPosition().reachableInMoves(1);
            controller.callView(new ShotMoveRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination)));
        } else
            sendPossibleWeapons();
    }

    /**
     * Perform the move before a shot action
     *
     * @param x is the destination row
     * @param y is the destination column
     */
    public void managePreEffectShot(int x, int y) {
        performMove(x, y);
        if (controller.getGameManager().isFinalFrenzyPhase() && !getUnloadedWeapon().isEmpty()) {
            askForReload();
        } else {
            if (!Validator.availableToFireWeapons(currentRoundManager.getCurrentPlayer()).isEmpty()) {
                sendPossibleWeapons();
            } else
                controller.getGameManager().getCurrentRound().nextPhase();
        }
    }

    /**
     * Send a request with the possible weapons to shot with
     */
    private void sendPossibleWeapons() {
        controller.callView(new WeaponRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), Encoder.encodeWeaponsList(Validator.availableToFireWeapons(currentRoundManager.getCurrentPlayer()))));
    }

    /**
     * Save the weapons chosen for the shot action in the round manager
     *
     * @param weapon is the name of the chosen weapon
     */
    public void saveWeapon(String weapon) {
        for (int i = 0; i < currentRoundManager.getCurrentPlayer().getNumberOfWeapons(); i++) {
            if (currentRoundManager.getCurrentPlayer().getWeapons()[i].getName().equals(weapon))
                chosenWeapon = currentRoundManager.getCurrentPlayer().getWeapons()[i];
        }
        sendPossibleEffects();
    }

    /**
     * Send a request to the player with the possible effect to perform with the chosen weapon
     */
    public void sendPossibleEffects() {
        boolean[] usableEffects = new boolean[3];
        for (int i = 0; i < 3; i++)
            usableEffects[i] = chosenWeapon.isUsableEffect(i + 1);
        if (Arrays.equals(usableEffects, new boolean[]{false, false, false})) {
            setWeaponUnloaded();
        } else
            controller.callView(new WeaponEffectRequest(currentRoundManager.getCurrentPlayer().getUsername(), usableEffects));
    }

    /**
     * Set the weapon unloaded an go to the next phase
     */
    public void setWeaponUnloaded(){
        chosenWeapon.setUnloaded();
        currentRoundManager.nextPhase();
    }

    /**
     * Send a request with the possible payment modes to pay the chosen effect
     *
     * @param chosenEffect is the number of the effect chosen by the player
     */
    public void askForEffectPay(int chosenEffect) {
        this.chosenEffect = chosenEffect;
        if (chosenWeapon.hasToPay(chosenEffect)) {
            int[] effectCost = AmmoCube.getColoursByAmmoCubeArrayRYB(chosenWeapon.getEffectCost(chosenEffect));
            if (effectCost[0] + effectCost[1] + effectCost[2] == 0)
                askForTargets();
            else
                askForPowerUpAsAmmo(effectCost, PaymentRequestEvent.Context.WEAPON_EFFECT);
        } else
            askForTargets();
    }

    /**
     * Perform the effect payment with the powerUp chosen by the player
     *
     * @param powerUpsType   is an array with the type of the powerUps chosen by the player
     * @param powerUpsColour is the colour of the powerUp chosen by the player
     */
    public void payEffect(String[] powerUpsType, CubeColour[] powerUpsColour) {
        int[] effectCost = AmmoCube.getColoursByAmmoCubeArrayRYB(chosenWeapon.getEffectCost(chosenEffect));
        payCost(effectCost, Decoder.decodePowerUpsList(currentRoundManager.getCurrentPlayer(), powerUpsType, powerUpsColour));
        askForTargets();
    }

    /**
     * Send to the player a request with the possible targets of the chosen effect of the chosen weapon
     */
    private void askForTargets() {
        ControllerViewEvent message = chosenWeapon.getTargetEffect(chosenEffect);
        if (message instanceof TargetPlayerRequestEvent && ((TargetPlayerRequestEvent) message).getMaxTarget() < 0)
            performWeaponEffect(new ArrayList<>());
        else
            controller.callView(message);
    }

    /**
     * Perform the effect on the chosen target in case of those are other players
     *
     * @param targetsLite is a list characters that represent the target players
     */
    public void performWeaponEffect(List<Character> targetsLite) {
        ArrayList<Object> targets = Decoder.decodePlayerListAsObject(targetsLite, controller.getGameManager().getModel().getPlayers());
        chosenWeapon.performEffect(chosenEffect, targets);
        checkForWhileActionPowerUp();
    }

    /**
     * Perform the effect on the chosen target in case of this is a square
     *
     * @param squareX is the target's row
     * @param squareY is the target's column
     */
    public void performWeaponEffect(int squareX, int squareY) {
        ArrayList<Object> targets = new ArrayList<>();
        targets.add(Decoder.decodeSquare(squareX, squareY, controller.getGameManager().getModel().getGameboard().getMap()));
        chosenWeapon.performEffect(chosenEffect, targets);
        checkForWhileActionPowerUp();
    }

    /**
     * Check if the player can use while-action powerUps. In case he can, send a request to the player about their use
     */
    private void checkForWhileActionPowerUp() {
        ArrayList<PowerUp> usablePowerUps = currentRoundManager.getCurrentPlayer().getWhileActionPowerUp();
        if (!usablePowerUps.isEmpty() && !chosenWeapon.getDamagedPlayer().isEmpty() && !(currentRoundManager.getCurrentPlayer().getAmmo().isEmpty() && currentRoundManager.getCurrentPlayer().getPowerUps().isEmpty())) {
            controller.callView(new WhileActionPowerUpRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), Encoder.encodePowerUpsType(usablePowerUps), Encoder.encodePowerUpColour(usablePowerUps)));
        } else
            sendPossibleEffects();
    }


    /*
     *
     * Weapon grab manage
     *
     */

    /**
     * Grab a weapon chosen by the player.
     * If the grab is free, perform it, otherwise check for the possible grab modality
     *
     * @param weaponChoice is the name of the chosen weapon
     */
    public void grabWeapon(String weaponChoice) {
        SpawnSquare grabSquare = (SpawnSquare) currentRoundManager.getCurrentPlayer().getPosition();
        for (Weapon w : grabSquare.getWeapons()) {
            if (w.getName().equals(weaponChoice))
                chosenWeapon = w;
        }
        int[] grabCost = AmmoCube.getColoursByAmmoCubeArrayRYB(chosenWeapon.getGrabCost());

        if (grabCost[0] + grabCost[1] + grabCost[2] == 0) {
            ((SpawnSquare) currentRoundManager.getCurrentPlayer().getPosition()).grabWeapon(chosenWeapon, currentRoundManager.getCurrentPlayer());
            manageWeaponLimit();
        } else
            askForPowerUpAsAmmo(grabCost, PaymentRequestEvent.Context.WEAPON_GRAB);
    }

    /**
     * Perform the payment of the weapon grab with the chosen powerUps.
     *
     * @param powerUpsType   are the chosen powerUps type
     * @param powerUpsColour are the colour of the chosen powerUps
     */
    public void payWeaponGrab(String[] powerUpsType, CubeColour[] powerUpsColour) {
        int[] grabCost = AmmoCube.getColoursByAmmoCubeArrayRYB(chosenWeapon.getGrabCost());
        payCost(grabCost, Decoder.decodePowerUpsList(currentRoundManager.getCurrentPlayer(), powerUpsType, powerUpsColour));
        ((SpawnSquare) currentRoundManager.getCurrentPlayer().getPosition()).grabWeapon(chosenWeapon, currentRoundManager.getCurrentPlayer());
        manageWeaponLimit();
    }

    /**
     * Check if the player grabbed his fourth weapon.
     * In case he did, send a request of which weapon he want to discard
     */
    private void manageWeaponLimit() {
        if (currentRoundManager.getCurrentPlayer().getNumberOfWeapons() > 3) {
            ArrayList<String> playerWeapons = new ArrayList<>();
            for (int i = 0; i < currentRoundManager.getCurrentPlayer().getNumberOfWeapons(); i++) {
                playerWeapons.add(currentRoundManager.getCurrentPlayer().getWeapons()[i].getName());
                controller.callView(new WeaponDiscardRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), playerWeapons));
            }
        } else
            currentRoundManager.nextPhase();
    }

    /**
     * Discard the extra weapon chosen by the player, putting it back in the player position spawn square, and setting it loaded
     *
     * @param weapon is the name of the chosen weapon
     */
    public void discardWeapon(String weapon) {
        Weapon discardWeapon = Decoder.decodePlayerWeapon(currentRoundManager.getCurrentPlayer(), weapon);
        currentRoundManager.getCurrentPlayer().discardWeapon(discardWeapon);
        currentRoundManager.nextPhase();
    }

    /*
     *
     * Reload manage
     *
     */

    /**
     * Get all the unloaded weapon of the current player
     *
     * @return a list of all the player's unloaded weapons
     */
    private ArrayList<Weapon> getUnloadedWeapon() {
        ArrayList<Weapon> possibleReload = new ArrayList<>();
        for (int i = 0; i < currentRoundManager.getCurrentPlayer().getNumberOfWeapons(); i++) {
            Weapon currentWeapon = currentRoundManager.getCurrentPlayer().getWeapons()[i];
            if (!currentWeapon.isLoaded() && currentRoundManager.getCurrentPlayer().canAffortCost(currentWeapon.getReloadCost()))
                possibleReload.add(currentRoundManager.getCurrentPlayer().getWeapons()[i]);
        }
        return possibleReload;
    }

    /**
     * If the player can perform at leas one weapon reload, send all reloadable and payable weapon to the player to ask him for a reload action.
     */
    void askForReload() {
        ArrayList<Weapon> possibleReload = getUnloadedWeapon();
        if (!possibleReload.isEmpty()) {
            controller.callView(new WeaponReloadRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), Encoder.encodeWeaponsList(possibleReload)));
            reloadPhase = true;
        } else if (controller.getGameManager().getCurrentRound().getPhase() == 6 || controller.getGameManager().getCurrentRound().getCurrentPlayer().getNumberOfWeapons() == 0)
            currentRoundManager.nextPhase();
        else
            sendPossibleWeapons();
    }

    /**
     * Send a request for the reload pay modality
     *
     * @param weapon is the name of the weapon that the player want to reload
     */
    public void askForWeaponReloadPay(String weapon) {
        chosenWeapon = Decoder.decodePlayerWeapon(currentRoundManager.getCurrentPlayer(), weapon);
        int[] reloadCost = AmmoCube.getColoursByAmmoCubeArrayRYB(chosenWeapon.getReloadCost());
        askForPowerUpAsAmmo(reloadCost, PaymentRequestEvent.Context.WEAPON_RELOAD);
    }

    /**
     * Pay the weapon reload, using the chosen powerUp if the player chose some
     *
     * @param powerUpType   are the type of the powerUp chosen by the player
     * @param powerUpColour are the colour of the powerUp chosen by the player
     */
    public void payWeaponReload(String[] powerUpType, CubeColour[] powerUpColour) {
        int[] reloadCost = AmmoCube.getColoursByAmmoCubeArrayRYB(chosenWeapon.getReloadCost());
        payCost(reloadCost, Decoder.decodePowerUpsList(currentRoundManager.getCurrentPlayer(), powerUpType, powerUpColour));
        reloadWeapon();
    }

    /**
     * Reload the weapon chosen and, if it is the reload phase, send again the reload request with the unloaded weapons
     */
    private void reloadWeapon() {
        chosenWeapon.setLoaded();
        if (reloadPhase)
            askForReload();
        else
            sendPossibleWeapons();
    }


    /*
     *
     * Payment manage
     *
     */

    /**
     * Send to the player a request for which powerUp use to make a pay
     *
     * @param cost    is the pay cost as an integer array in the format Red - Yellow - Blue
     * @param context is teh context of the pay (WEAPON_EFFECT, WEAPON_RELOAD, WEAPON_GRAB)
     */
    private void askForPowerUpAsAmmo(int[] cost, PaymentRequestEvent.Context context) {
        int[] playerAmmo = AmmoCube.getColoursByListRYB(currentRoundManager.getCurrentPlayer().getAmmo());
        ArrayList<PowerUp> possiblePowerUp = new ArrayList<>();
        for (PowerUp p : currentRoundManager.getCurrentPlayer().getPowerUps()) {
            if ((p.getColour() == CubeColour.Red && cost[0] > 0) ||
                    (p.getColour() == CubeColour.Yellow && cost[1] > 0) ||
                    (p.getColour() == CubeColour.Blue && cost[2] > 0))
                possiblePowerUp.add(p);


        }
        CubeColour[] powerUpColoursLite = Encoder.encodePowerUpColour(possiblePowerUp);
        int[] powerUpsColours = AmmoCube.getColoursByCubeColourArrayRYB(powerUpColoursLite);
        int[] minimalPowerUpNumberToUse;
        if (Arrays.equals(playerAmmo, AmmoCube.cubeDifference(playerAmmo, powerUpsColours))) {
            if (context == PaymentRequestEvent.Context.WEAPON_EFFECT)
                payEffect(new String[]{}, new CubeColour[]{});
            else if (context == PaymentRequestEvent.Context.WEAPON_RELOAD)
                payWeaponReload(new String[]{}, new CubeColour[]{});
            else if (context == PaymentRequestEvent.Context.WEAPON_GRAB)
                payWeaponGrab(new String[]{}, new CubeColour[]{});
        } else {
            minimalPowerUpNumberToUse = AmmoCube.cubeDifference(cost, playerAmmo);
            if (context == PaymentRequestEvent.Context.WEAPON_EFFECT) {
                EffectPaymentRequestEvent message = new EffectPaymentRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(),
                        Encoder.encodePowerUpsType(possiblePowerUp),
                        powerUpColoursLite, minimalPowerUpNumberToUse, cost);
                controller.callView(message);
            } else if (context == PaymentRequestEvent.Context.WEAPON_RELOAD) {
                WeaponReloadPaymentRequestEvent message = new WeaponReloadPaymentRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(),
                        Encoder.encodePowerUpsType(possiblePowerUp),
                        powerUpColoursLite, minimalPowerUpNumberToUse, cost);
                controller.callView(message);
            } else if (context == PaymentRequestEvent.Context.WEAPON_GRAB) {
                WeaponGrabPaymentRequestEvent message = new WeaponGrabPaymentRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(),
                        Encoder.encodePowerUpsType(possiblePowerUp),
                        powerUpColoursLite, minimalPowerUpNumberToUse, cost);
                controller.callView(message);
            }
        }
    }

    /**
     * Pay a cost, using a list of powerUps chosen by the player and the remaining with the player's ammo
     *
     * @param cost     is the pay cost as an integer array in the format Red - Yellow - Blue
     * @param powerUps is a list of powerUp chosen to pay the effect
     */
    public void payCost(int[] cost, List<PowerUp> powerUps) {
        for (PowerUp p : powerUps) {
            currentRoundManager.getCurrentPlayer().discardPowerUp(p);
            model.getGameboard().getPowerUpDeck().discardCard(p);
            if (p.getColour() == CubeColour.Red)
                cost[0]--;
            else if (p.getColour() == CubeColour.Yellow)
                cost[1]--;
            else
                cost[2]--;
        }
        ArrayList<AmmoCube> playersAmmo = new ArrayList<>(currentRoundManager.getCurrentPlayer().getAmmo());
        for (AmmoCube a : playersAmmo) {
            if (a.getColour() == CubeColour.Red && cost[0] > 0) {
                currentRoundManager.getCurrentPlayer().discardAmmo(a);
                cost[0]--;
            } else if (a.getColour() == CubeColour.Yellow && cost[1] > 0) {
                currentRoundManager.getCurrentPlayer().discardAmmo(a);
                cost[1]--;
            } else if (a.getColour() == CubeColour.Blue && cost[2] > 0) {
                currentRoundManager.getCurrentPlayer().discardAmmo(a);
                cost[2]--;
            }
        }
    }

    /**
     * Send a request for a payment with no condition, that mean that every colour of ammo can be used to perform it.
     * This method is used to play only while-action powerUps
     *
     * @param powerUpType   is the powerUp type to pay
     * @param powerUpColour is the powerUp colour to pay
     */
    public void askForGenericPay(String powerUpType, CubeColour powerUpColour) {
        PowerUp choice = Decoder.decodePowerUp(currentRoundManager.getCurrentPlayer(), powerUpType, powerUpColour);
        chosenPowerUp = choice;
        ArrayList<PowerUp> usablePowerUp = currentRoundManager.getCurrentPlayer().getPowerUps();
        usablePowerUp.remove(choice);
        boolean[] usableAmmo = new boolean[3];
        usableAmmo[0] = currentRoundManager.getCurrentPlayer().getCubeColourNumber(CubeColour.Red) > 0;
        usableAmmo[1] = currentRoundManager.getCurrentPlayer().getCubeColourNumber(CubeColour.Yellow) > 0;
        usableAmmo[2] = currentRoundManager.getCurrentPlayer().getCubeColourNumber(CubeColour.Blue) > 0;
        controller.callView(new GenericPayRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), usableAmmo, Encoder.encodePowerUpsType(usablePowerUp), Encoder.encodePowerUpColour(usablePowerUp)));
    }


    /*
     *
     * PowerUp request manage
     *
     */

    /**
     * If the player can use powerUps as action, send him a request with all the usable powerUps
     */
    void askForPowerUpAsAction() {
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        for (PowerUp p : currentRoundManager.getCurrentPlayer().getPowerUps()) {
            if (p.whenToUse() == PowerUp.Usability.AS_ACTION && !(controller.getGameManager().isFirstRoundPhase() && currentRoundManager.getCurrentPlayer() == controller.getGameManager().getModel().getPlayers().get(0) && p.getName().equals("Newton")))
                powerUps.add(p);
        }
        if (!powerUps.isEmpty()) {
            PowerUpRequestEvent message = new AsActionPowerUpRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), Encoder.encodePowerUpsType(powerUps), Encoder.encodePowerUpColour(powerUps));
            controller.callView(message);
        } else
            currentRoundManager.nextPhase();
    }

    /**
     * Send a request to get the target of the chosen powerUps
     *
     * @param powerUpChoice is the chosen powerUp type
     * @param powerUpColour is the chosen powerUp colour
     */
    public void usePowerUp(String powerUpChoice, CubeColour powerUpColour) {
        chosenPowerUp = Decoder.decodePowerUp(currentRoundManager.getCurrentPlayer(), powerUpChoice, powerUpColour);
        if (chosenPowerUp.getName().equals("Teleporter")) {
            askForTargetsTeleporter();
        } else if (chosenPowerUp.getName().equals("Newton")) {
            askForPlayerTargetsNewton();
        }
    }

    /*
     *
     * PowerUp manage
     *
     */

    /**
     * Send a request for the square target of a Teleporter powerUp
     */
    private void askForTargetsTeleporter() {
        ArrayList<Square> possibleDestination = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix()[i][j] != null)
                    possibleDestination.add(controller.getGameManager().getModel().getGameboard().getMap().getSquareMatrix()[i][j]);
            }
        }
        controller.callView(new TeleporterTargetRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination)));
    }

    /**
     * Send a request for the player target of a Newton powerUp
     */
    private void askForPlayerTargetsNewton() {
        ArrayList<Player> possiblePlayers = controller.getGameManager().getModel().getPlayers();
        possiblePlayers.remove(currentRoundManager.getCurrentPlayer());
        ArrayList<Player> onBoardPlayer = new ArrayList<>();
        for (Player p : possiblePlayers) {
            if (p.getPosition() != null)
                onBoardPlayer.add(p);
        }
        controller.callView(new NewtonPlayerTargetRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), Encoder.encodePlayerTargets(onBoardPlayer), 1));
    }

    /**
     * Send a request for the square target of a Newton powerUp
     */
    public void askForSquareTargetsNewton() {
        controller.callView(((Newton) chosenPowerUp).getTargets());
    }

    /**
     * Perform the effect of the chosen powerUp
     *
     * @param target is the chosen target, which could be both a character or a square (depends of the chosen powerUp)
     */
    public void performPowerUp(Object target) {
        chosenPowerUp.performEffect(target);
    }

    /**
     * If the player has some, send the request of using end-round powerUps
     */
    public void endPowerUpPhase() {
        currentRoundManager.getCurrentPlayer().discardPowerUp(chosenPowerUp);
        model.getGameboard().getPowerUpDeck().discardCard(chosenPowerUp);
        controller.getGameManager().getCurrentRound().nextPhase();
    }

    /**
     * Send a request for the target of the targeting scope powerUp
     */
    public void askTargetTargetingScope() {
        controller.callView(new TargetingScopeTargetRequestEvent(currentRoundManager.getCurrentPlayer().getUsername(), Encoder.encodePlayerTargets(chosenWeapon.getDamagedPlayer())));
    }

    /**
     * Perform the effect of the targeting scope on the chosen target
     *
     * @param target is the character of the chosen target
     */
    public void performTargetingScopeEffect(Character target) {
        Player decodedTarget = Decoder.decodePlayerFromCharacter(target, controller.getGameManager().getModel().getPlayers());
        chosenPowerUp.performEffect(decodedTarget);
        decodedTarget.removeOneTimesGetDamaged();
        sendPossibleEffects();
    }

    /**
     * Getter method
     * @return the chosen weapon
     */
    public Weapon getChosenWeapon() {
        return chosenWeapon;
    }
}
