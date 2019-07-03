package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.TwoOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the weapon Rocket Launcher
 *
 * @author Federico Innocente
 */
public class RocketLauncher extends TwoOptionalEffectWeapon {
    /**
     * Flag to determinate if the first step of the first effect has been performed
     */
    private boolean intermediateEffectState;

    /**
     * Save the target square of the first effect to use it in the third one
     */
    private Square targetSquare;

    /**
     * Constructor
     */
    public RocketLauncher() {
        super(CubeColour.Red, "ROCKET LAUNCHER",
                new AmmoCube[]{new AmmoCube(CubeColour.Red), new AmmoCube(CubeColour.Red)},
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow)});
    }

    /**
     * Set the weapon loaded and reset the flag to false
     */
    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateEffectState = false;
        targetSquare = null;
    }

    /**
     * Set the usable effects. This method is called in the reloading phase of the weapon to set the effects that the player can use at teh start of the shot action
     */
    @Override
    protected void setUsableEffect() {
        updateUsableEffect(new boolean[]{true, true, false});
    }

    /**
     * Manage the flow of the effects after using one, setting the once that are usable or less.
     *
     * @param effectUsed is the last effect used
     */
    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && !intermediateEffectState) {
            intermediateEffectState = true;
            getUsableEffect()[2] = true;
        } else if (effectUsed == 0) {
            getUsableEffect()[0] = false;
        } else if (effectUsed == 1)
            getUsableEffect()[1] = false;
        else if (effectUsed == 2) {
            updateUsableEffect(new boolean[]{false, false, false});
        }
    }

    /**
     * Perform the basic effect of the weapon. This effect is split in two phase, chosen according to teh flag intermediateEffectState
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        if (!intermediateEffectState)
            performEffectOneFirstStep(targets);
        else
            performEffectOneSecondStep(targets);
        effectControlFlow(1);
    }

    /**
     * Perform the first step of the base effect
     *
     * @param targets is a list of targets chosen by the player
     */
    private void performEffectOneFirstStep(List<Object> targets) {
        damage((Player) targets.get(0), 2);
        getDamagedPlayer().add((Player) targets.get(0));
        targetSquare = ((Player) targets.get(0)).getPosition();
        getFirstEffectTarget().add((Player) targets.get(0));
    }

    /**
     * Perform the second step of the base effect
     *
     * @param targets is a list of targets chosen by the player
     */
    private void performEffectOneSecondStep(List<Object> targets) {
        move(getFirstEffectTarget().get(0), (Square) targets.get(0));
    }

    /**
     * Calculate all the possible player targets of the basic effect and encode them into a message ready to be send to the player.
     * This methods call two sub-methods according with the flag intermediateEffectState
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectOne() {
        if (!intermediateEffectState)
            return getTargetEffectOneFirstStep();
        else
            return getTargetEffectOneSecondStep();
    }

    /**
     * Calculate all the possible player targets of the basic effect (first step) and encode them into a message ready to be send to the player.
     *
     * @return a message with all teh information about teh possible targets
     */
    private ControllerViewEvent getTargetEffectOneFirstStep() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().findVisiblePlayers();
        possibleTargets.removeAll(getOwner().getPosition().getSquarePlayers());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    /**
     * Calculate all the possible square targets of the basic effect (second step) and encode them into a message ready to be send to the player.
     *
     * @return a message with all teh information about teh possible targets
     */
    private ControllerViewEvent getTargetEffectOneSecondStep() {
        ArrayList<Square> possibleDestination = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++) {
            if (getFirstEffectTarget().get(0).getPosition().checkDirection(direction))
                possibleDestination.add(getFirstEffectTarget().get(0).getPosition().getNextSquare(direction));
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination));
    }

    /**
     * Check if the fist effect is usable, according with the phase (intermediateEffectState) and the map situation
     *
     * @return true if the effect is usable
     */
    @Override
    public boolean isUsableEffectOne() {
        if (!intermediateEffectState)
            return getUsableEffect()[0] && !((TargetPlayerRequestEvent) getTargetEffectOne()).getPossibleTargets().isEmpty();
        else
            return getUsableEffect()[0] && ((TargetSquareRequestEvent) getTargetEffectOne()).getPossibleTargetsX().length != 0;
    }

    /**
     * Perform the second effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        move(getOwner(), (Square) targets.get(0));
        effectControlFlow(2);
    }

    /**
     * Calculate all the possible square targets of the second effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Square> possibleDestination = getOwner().getPosition().reachableInMoves(2);
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination));
    }

    /**
     * Check if the second effect is usable in the current condition.
     * That means that teh effect must be enabled, the player can pay for it and that it has targets in range.
     *
     * @return true if the effect can be performed, false otherwise
     */
    @Override
    public boolean isUsableEffectTwo() {
        return getUsableEffect()[1] && getOwner().canAffortCost(getSecondEffectCost());
    }

    /**
     * Perform the third effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectThree(List<Object> targets) {
        for (Player p : targetSquare.getSquarePlayers()) {
            damage(p, 1);
            getDamagedPlayer().add(p);
        }
        if (getFirstEffectTarget().get(0).getPosition() != targetSquare) {
            damage(getFirstEffectTarget().get(0), 1);
            getDamagedPlayer().add(getFirstEffectTarget().get(0));
        }
        effectControlFlow(3);
    }

    /**
     * Calculate all the possible player targets of the third effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectThree() {
        ArrayList<Player> targets = targetSquare.getSquarePlayers();
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(targets), -1);
    }

    /**
     * Check if the second effect is usable in the current condition.
     *
     * @return true if the effect can be performed, false otherwise
     */
    @Override
    public boolean isUsableEffectThree() {
        return getUsableEffect()[2];
    }
}
