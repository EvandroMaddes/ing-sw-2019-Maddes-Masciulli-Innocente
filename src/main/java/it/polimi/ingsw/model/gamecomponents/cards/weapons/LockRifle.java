package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.OneOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;
import it.polimi.ingsw.utils.custom_exceptions.EffectIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the weapon Lock Rifle
 */
public class LockRifle extends OneOptionalEffectWeapon {

    /**
     * Constructor
     */
    public LockRifle() {
        super(CubeColour.Blue, "LOCK RIFLE",
                new AmmoCube[]{new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{new AmmoCube(CubeColour.Red)});
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
        mark(target, 1);
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
        ArrayList<Player> visiblePlayer = getOwner().getPosition().findVisiblePlayers();
        visiblePlayer.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(visiblePlayer), 1);
    }

    /**
     * Perform the second effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        mark((Player) targets.get(0), 1);
        effectControlFlow(2);
    }

    /**
     * Calculate all the possible player targets of the second effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().findVisiblePlayers();
        possibleTargets.remove(getFirstEffectTarget().get(0));
        possibleTargets.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

}
