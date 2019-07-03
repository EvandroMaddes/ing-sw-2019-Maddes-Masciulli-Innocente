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
 * Class for the weapon Plasma Gun
 */
public class PlasmaGun extends TwoOptionalEffectWeapon {

    /**
     * Constructor
     */
    public PlasmaGun() {
        super(CubeColour.Blue, "PLASMA GUN",
                new AmmoCube[]{new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Yellow)},
                new AmmoCube[]{},
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)});
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
        if (effectUsed == 0) {
            getUsableEffect()[0] = false;
            getUsableEffect()[2] = true;
        } else if (effectUsed == 1 && !getUsableEffect()[0])
            updateUsableEffect(new boolean[]{false, false, false});
        else if (effectUsed == 1)
            getUsableEffect()[1] = false;
        else if (effectUsed == 2)
            getUsableEffect()[2] = false;
    }

    /**
     * Check if the weapon is usable.
     * To be usable, the weapon must be loaded and at least one of its effects must be correctly usable
     *
     * @return true if the weapon is usable, false otherwise
     */
    @Override
    public boolean isUsable() {
        return isLoaded() && ((getUsableEffect()[0] && isUsableEffectOne()) || (getUsableEffect()[1] && isUsableEffectTwo()) || (getUsableEffect()[2] && isUsableEffectThree()));
    }

    /**
     * Perform the basic effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player) targets.get(0);
        damage(target, 2);
        getFirstEffectTarget().add(target);
        getDamagedPlayer().add(target);
        effectControlFlow(1);
    }

    /**
     * Calculate all the possible player targets of the basic effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().findVisiblePlayers();
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
        getOwner().setPosition(target);
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
        possibleDestination.remove(getOwner().getPosition());
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
        return getUsableEffect()[1] && ((TargetSquareRequestEvent) getTargetEffectTwo()).getPossibleTargetsY().length != 0;
    }

    /**
     * Perform the third effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectThree(List<Object> targets) {
        damage(getFirstEffectTarget().get(0), 1);
        getDamagedPlayer().add(getFirstEffectTarget().get(0));
        effectControlFlow(3);
    }

    /**
     * Calculate all the possible player targets of the third effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectThree() {
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getFirstEffectTarget()), -1);
    }
}
