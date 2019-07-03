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
 * Class for the weapon Shock Wave
 *
 * @author Federico Inncente
 */
public class ShockWave extends AlternateFireWeapon {
    /**
     * Flag to know if one target has been already choose for the first effect
     */
    private boolean firstRequestDone;

    /**
     * Flag to know if two targets have been already choose for the first effect
     */
    private boolean secondRequestDone;

    /**
     * Constructor
     */
    public ShockWave() {
        super(CubeColour.Yellow, "SHOCKWAVE",
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow)},
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow)});
    }

    /**
     * Set the weapon loaded and reset the flag to false
     */
    @Override
    public void setLoaded() {
        super.setLoaded();
        firstRequestDone = false;
        secondRequestDone = false;
    }

    /**
     * Manage the flow of the effects after using one, setting the once that are usable or less.
     *
     * @param effectUsed is the last effect used
     */
    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 1)
            updateUsableEffect(new boolean[]{false, false, false});
        else if (effectUsed == 0) {
            if (!firstRequestDone) {
                firstRequestDone = true;
                updateUsableEffect(new boolean[]{true, false, false});
            } else if (!secondRequestDone)
                secondRequestDone = true;
            else
                updateUsableEffect(new boolean[]{false, false, false});
        }
    }

    /**
     * Perform the basic effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        damage((Player) targets.get(0), 1);
        getDamagedPlayer().add((Player) targets.get(0));
        getFirstEffectTarget().add((Player) targets.get(0));
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
        for (Player p : getFirstEffectTarget()) {
            possibleTargets.removeAll(p.getPosition().getSquarePlayers());
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    /**
     * Perform the second effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectTwo(List<Object> targets) {
        ArrayList<Player> nextSquarePlayer = getOwner().getPosition().getNextSquarePlayer();
        for (Player p : nextSquarePlayer) {
            damage(p, 1);
            getDamagedPlayer().add(p);
        }
        effectControlFlow(2);
    }

    /**
     * Calculate all the possible player targets of the second effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getOwner().getPosition().getNextSquarePlayer()), -1);
    }
}
