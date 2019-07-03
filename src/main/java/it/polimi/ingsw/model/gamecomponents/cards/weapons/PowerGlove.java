package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the weapon Power Glove
 */
public class PowerGlove extends AlternateFireWeapon {

    /**
     * Flag to see if the second effect has performed the first step
     */
    private boolean intermediateEffectState;

    /**
     * Save the direction of teh first step of hte second effect
     */
    private int firstStepDirection;

    /**
     * Constructor
     */
    public PowerGlove() {
        super(CubeColour.Yellow, "POWER GLOVE",
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)});
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
        if (effectUsed == 0)
            updateUsableEffect(new boolean[]{false, false, false});
        else if (effectUsed == 1 && !intermediateEffectState) {
            intermediateEffectState = true;
            updateUsableEffect(new boolean[]{false, true, false});
        } else if (effectUsed == 1)
            updateUsableEffect(new boolean[]{false, false, false});
    }

    /**
     * Perform the first step of the base effect
     *
     * @param targets is a list of targets chosen by the player
     */
    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player) targets.get(0);
        move(getOwner(), target.getPosition());
        damage(target, 1);
        getDamagedPlayer().add(target);
        mark(target, 2);
        effectControlFlow(1);
    }

    /**
     * Calculate all the possible player targets of the basic effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().getNextSquarePlayer();
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    /**
     * Perform the second effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player) targets.get(0);
        for (int direction = 0; direction < 4; direction++) {
            if (getOwner().getPosition().getNextSquare(direction) == target.getPosition())
                firstStepDirection = direction;
        }
        move(getOwner(), target.getPosition());
        damage(target, 2);
        getDamagedPlayer().add(target);
        effectControlFlow(2);
    }

    /**
     * Calculate all the possible player targets of the second effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        if (!intermediateEffectState)
            return getTargetEffectOne();
        else {
            ArrayList<Player> possibleTargets = new ArrayList<>();
            if (getOwner().getPosition().checkDirection(firstStepDirection))
                possibleTargets.addAll(getOwner().getPosition().getNextSquare(firstStepDirection).getSquarePlayers());
            return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
        }
    }

    /**
     * Check if the second effect is usable in the current condition.
     * That means that teh effect must be enabled, the player can pay for it and that it has targets in range.
     *
     * @return true if the effect can be performed, false otherwise
     */
    @Override
    public boolean isUsableEffectTwo() {
        if (!intermediateEffectState)
            return getUsableEffect()[1] && getOwner().canAffortCost(getSecondEffectCost()) && !((TargetPlayerRequestEvent) getTargetEffectTwo()).getPossibleTargets().isEmpty();
        else
            return getUsableEffect()[1] && !((TargetPlayerRequestEvent) getTargetEffectTwo()).getPossibleTargets().isEmpty();
    }
}

