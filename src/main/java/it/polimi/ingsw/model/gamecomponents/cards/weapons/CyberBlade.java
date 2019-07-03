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
 * Class for the Cyber Blade weapon
 *
 * @author Federico Innocente
 */
public class CyberBlade extends TwoOptionalEffectWeapon {

    /**
     * Constructor
     */
    public CyberBlade() {
        super(CubeColour.Yellow, "CYBER BLADE",
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Red)},
                new AmmoCube[]{},
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow)});
    }

    /**
     * Set the weapon loaded
     */
    @Override
    public void setLoaded() {
        super.setLoaded();
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
        if (effectUsed == 0) {
            getUsableEffect()[0] = false;
            getUsableEffect()[2] = true;
        }
        else if (effectUsed == 1 || effectUsed == 2)
            getUsableEffect()[effectUsed] = false;
    }

    /**
     * Perform the basic effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);
        damage(target, 2);
        getFirstEffectTarget().add(target);
        getDamagedPlayer().add(target);
        effectControlFlow(1);
    }

    /**
     * Calculate all the possible player targets of the basic effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all the information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().getSquarePlayers();
        possibleTargets.remove(getOwner());
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
        Square target = (Square) targets.get(0);
        move(getOwner(), target);
        effectControlFlow(2);
    }


    /**
     * Calculate all the possible square targets of the second effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Square> possibleTargets = getOwner().getPosition().reachableInMoves(1);
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
        return getUsableEffect()[1];
    }

    /**
     * Perform the third effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectThree(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);
        damage(target, 2);
        getDamagedPlayer().add(target);
        effectControlFlow(3);
    }

    /**
     * Calculate all the possible player targets of the third effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectThree() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().getSquarePlayers();
        possibleTargets.remove(getOwner());
        possibleTargets.remove(getFirstEffectTarget().get(0));
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    /**
     * Check if the weapon is usable.
     * To be usable, the weapon must be loaded and at least one of its effects must be correctly usable
     *
     * @return true if the weapon is usable, false otherwise
     */
    @Override
    public boolean isUsable() {
        return isLoaded() && ( isUsableEffectTwo() || isUsableEffectOne() || isUsableEffectThree() );
    }
}
