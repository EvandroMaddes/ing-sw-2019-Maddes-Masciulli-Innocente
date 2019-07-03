package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.TwoOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;
import it.polimi.ingsw.utils.custom_exceptions.EffectIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the weapon T.H.O.R.
 *
 * @author Federico Innocente
 */
public class Thor extends TwoOptionalEffectWeapon {
    /**
     * Save the last player hit by the weapon
     */
    private Player lastPlayerHit;

    /**
     * Constructor
     */
    public Thor() {
        super(CubeColour.Blue, "T.H.O.R.",
                new AmmoCube[]{new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Red)},
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)});
    }

    /**
     * Set the weapon loaded and reset the flag to false
     */
    @Override
    public void setLoaded() {
        super.setLoaded();
        lastPlayerHit = null;
    }

    /**
     * Set the usable effects. This method is called in the reloading phase of the weapon to set the effects that the player can use at teh start of the shot action
     */
    @Override
    protected void setUsableEffect() {
        updateUsableEffect(new boolean[]{true, false, false});
    }

    /**
     * Manage the flow of the effects after using one, setting the once that are usable or less.
     *
     * @param effectUsed is the last effect used
     */
    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && getUsableEffect()[0])
            updateUsableEffect(new boolean[]{false, true, false});
        else if (effectUsed == 1 && getUsableEffect()[1])
            updateUsableEffect(new boolean[]{false, false, true});
        else if (effectUsed == 2 && getUsableEffect()[2])
            updateUsableEffect(new boolean[]{false, false, false});
        else
            throw new EffectIllegalArgumentException();
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
        lastPlayerHit = target;
        effectControlFlow(1);
    }

    /**
     * Calculate all the possible player targets of the basic effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all the information about teh possible targets
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
        Player target = (Player) targets.get(0);
        damage(target, 1);
        getFirstEffectTarget().add(target);
        getDamagedPlayer().add(target);
        lastPlayerHit = target;
        effectControlFlow(2);
    }

    /**
     * Calculate all the possible player targets of the second effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Player> targettablePlayer = lastPlayerHit.getPosition().findVisiblePlayers();
        targettablePlayer.removeAll(getFirstEffectTarget());
        targettablePlayer.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(targettablePlayer), 1);
    }

    /**
     * Perform the third effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectThree(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player) targets.get(0);
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
        return getTargetEffectTwo();
    }
}
