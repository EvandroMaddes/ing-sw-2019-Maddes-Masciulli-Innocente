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
 * Class for the Flamethrower weapon
 *
 * @author Federico Inncente
 */
public class Flamethrower extends AlternateFireWeapon {
    /**
     * Flag to determinate if the first step of the first effect has been performed
     */
    private boolean intermediateEffectState;

    /**
     * Save the direction of the first effect to allowed the owner to hit multiple target
     */
    private int firstEffectDirection;

    /**
     * Constructor
     */
    public Flamethrower() {
        super(CubeColour.Red, "FLAMETHROWER",
                new AmmoCube[]{new AmmoCube(CubeColour.Red)},
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Yellow)});
    }

    /**
     * Set the weapon loaded. The intermediateEffectState flag is turned back to false.
     */
    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateEffectState = false;
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
            updateUsableEffect(new boolean[]{true, false, false});
        } else if (effectUsed == 0)
            getUsableEffect()[0] = false;
        else if (effectUsed == 1)
            updateUsableEffect(new boolean[]{false, false, false});
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
        checkEmptyTargets(targets);
        damage((Player) targets.get(0), 1);
        getDamagedPlayer().add((Player) targets.get(0));
        for (int direction = 0; direction < 4; direction++)
            if (getOwner().getPosition().getNextSquare(direction) == ((Player) targets.get(0)).getPosition())
                firstEffectDirection = direction;
    }

    /**
     * Perform the second step of the base effect
     *
     * @param targets is a list of targets chosen by the player
     */
    private void performEffectOneSecondStep(List<Object> targets) {
        damage((Player) targets.get(0), 1);
        getDamagedPlayer().add((Player) targets.get(0));
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
        ArrayList<Player> possibleTargets = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++) {
            if (getOwner().getPosition().checkDirection(direction))
                possibleTargets.addAll(getOwner().getPosition().getNextSquare(direction).getSquarePlayers());
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    /**
     * Calculate all the possible player targets of the basic effect (second step) and encode them into a message ready to be send to the player.
     *
     * @return a message with all teh information about teh possible targets
     */
    private ControllerViewEvent getTargetEffectOneSecondStep() {
        ArrayList<Player> possibleTargets = new ArrayList<>();
        if (getOwner().getPosition().getNextSquare(firstEffectDirection).checkDirection(firstEffectDirection))
            possibleTargets.addAll(getOwner().getPosition().getNextSquare(firstEffectDirection).getNextSquare(firstEffectDirection).getSquarePlayers());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
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
            return getUsableEffect()[0] && getOwner().canAffortCost(getSecondEffectCost()) && !((TargetPlayerRequestEvent) getTargetEffectOne()).getPossibleTargets().isEmpty();
    }

    /**
     * Perform the second effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        int direction = 0;
        Square target = (Square) targets.get(0);
        for (int i = 0; i < 4; i++) {
            if (getOwner().getPosition().getNextSquare(i) == target)
                direction = i;
        }
        for (Player p : target.getSquarePlayers()) {
            damage(p, 2);
            getDamagedPlayer().add(p);
        }
        if (target.checkDirection(direction))
            target = target.getNextSquare(direction);
        for (Player p : target.getSquarePlayers()) {
            damage(p, 1);
            getDamagedPlayer().add(p);
        }
        effectControlFlow(2);
    }

    /**
     * Calculate all the possible square targets of the second effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Square> possibleTargets = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++) {
            if (getOwner().getPosition().checkDirection(direction) && (
                    !getOwner().getPosition().getNextSquare(direction).getSquarePlayers().isEmpty() ||
                    (getOwner().getPosition().getNextSquare(direction).checkDirection(direction) && !getOwner().getPosition().getNextSquare(direction).getNextSquare(direction).getSquarePlayers().isEmpty())))
                possibleTargets.add(getOwner().getPosition().getNextSquare(direction));
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTargets), Encoder.encodeSquareTargetsY(possibleTargets));
    }

    /**
     * Check if the second effect is usable in the current condition.
     * That means that teh effect must be enabled, the player can pay for it and that it has targets in range.
     *
     * @return true if the effect can be performed, false otherwise
     */
    @Override
    public boolean isUsableEffectTwo() {
        return getUsableEffect()[1] && getOwner().canAffortCost(getSecondEffectCost()) && ((TargetSquareRequestEvent) getTargetEffectTwo()).getPossibleTargetsY().length != 0;
    }
}
