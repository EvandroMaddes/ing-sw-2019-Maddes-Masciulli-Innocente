package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the weapon Shotgun
 *
 * @author Federico Innocente
 */
public class Shotgun extends AlternateFireWeapon {

    /**
     * Flag to determinate if the first step of the first effect has been performed
     */
    private boolean intermediateStateEffect;

    /**
     * Constructor
     */
    public Shotgun() {
        super(CubeColour.Yellow, "SHOTGUN",
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Yellow)},
                new AmmoCube[]{});
    }

    /**
     * Set the weapon loaded. The intermediateEffectState flag is turned back to false.
     */
    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateStateEffect = false;
    }

    /**
     * Manage the flow of the effects after using one, setting the once that are usable or less.
     *
     * @param effectUsed is the last effect used
     */
    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && !intermediateStateEffect) {
            intermediateStateEffect = true;
            updateUsableEffect(new boolean[]{true, false, false});
        } else if (effectUsed == 0 || effectUsed == 1) {
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
        if (!intermediateStateEffect)
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
        Player target = (Player) targets.get(0);
        damage(target, 3);
        getDamagedPlayer().add(target);
        getFirstEffectTarget().add(target);
    }

    /**
     * Perform the second step of the base effect
     *
     * @param targets is a list of targets chosen by the player
     */
    private void performEffectOneSecondStep(List<Object> targets) {
        Square destination = (Square) targets.get(0);
        move(getFirstEffectTarget().get(0), destination);
    }

    /**
     * Calculate all the possible player or square targets of the basic effect and encode them into a message ready to be send to the player.
     * This methods call two sub-methods according with the flag intermediateEffectState
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectOne() {
        if (!intermediateStateEffect)
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
        ArrayList<Player> possibleTargets = getOwner().getPosition().getSquarePlayers();
        possibleTargets.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    /**
     * Calculate all the possible square targets of the basic effect (second step) and encode them into a message ready to be send to the player.
     *
     * @return a message with all teh information about teh possible targets
     */
    private ControllerViewEvent getTargetEffectOneSecondStep() {
        ArrayList<Square> possibleDestination = new ArrayList<>();
        for (int destination = 0; destination < 4; destination++) {
            if (getFirstEffectTarget().get(0).getPosition().checkDirection(destination))
                possibleDestination.add(getFirstEffectTarget().get(0).getPosition().getNextSquare(destination));
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
        if (!intermediateStateEffect)
            return getUsableEffect()[0] && !((TargetPlayerRequestEvent) getTargetEffectOne()).getPossibleTargets().isEmpty();
        else
            return getUsableEffect()[0];
    }

    /**
     * Perform the second effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        damage((Player) targets.get(0), 2);
        getDamagedPlayer().add((Player) targets.get(0));
        effectControlFlow(2);
    }

    /**
     * Calculate all the possible square targets of the second effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Player> possibleTargets = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++) {
            if (getOwner().getPosition().checkDirection(direction))
                possibleTargets.addAll(getOwner().getPosition().getNextSquare(direction).getSquarePlayers());
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    /**
     * Check if the second effect is usable in the current condition.
     * That means that teh effect must be enabled, the player can pay for it and that it has targets in range.
     *
     * @return true if the effect can be performed, false otherwise
     */
    @Override
    public boolean isUsableEffectTwo() {
        return getUsableEffect()[1] && !((TargetPlayerRequestEvent) getTargetEffectTwo()).getPossibleTargets().isEmpty();
    }
}
