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
 * Class for the weapon Sledgehammer
 *
 * @author Federico Innocente
 */
public class Sledgehammer extends AlternateFireWeapon {

    /**
     * Flag to determinate if the first step of the second effect has been performed
     */
    private boolean intermediateEffect;

    /**
     * Constructor
     */
    public Sledgehammer() {
        super(CubeColour.Yellow, "SLEDGEHAMMER",
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow)},
                new AmmoCube[]{new AmmoCube(CubeColour.Red)});
    }

    /**
     * Set the weapon loaded and reset the flag to false
     */
    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateEffect = false;
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
        else if (effectUsed == 1 && !intermediateEffect) {
            updateUsableEffect(new boolean[]{false, true, false});
            intermediateEffect = true;
        } else if (effectUsed == 1)
            updateUsableEffect(new boolean[]{false, false, false});
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
     * Perform the second effect of the weapon. This effect is split in two phase, chosen according to teh flag intermediateEffectState
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        if (intermediateEffect)
            performEffectTwoSecondStep(targets);
        else
            performEffectTwoFirstStep(targets);

        effectControlFlow(2);
    }

    /**
     * Perform the first step of the second effect
     *
     * @param targets is a list of targets chosen by the player
     */
    private void performEffectTwoFirstStep(List<Object> targets) {
        damage((Player) targets.get(0), 3);
        getDamagedPlayer().add((Player) targets.get(0));
        getFirstEffectTarget().add((Player) targets.get(0));
    }

    /**
     * Perform the second step of the second effect
     *
     * @param targets is a list of targets chosen by the player
     */
    private void performEffectTwoSecondStep(List<Object> targets) {
        move(getFirstEffectTarget().get(0), (Square) targets.get(0));
    }

    /**
     * Calculate all the possible player targets of the second effect and encode them into a message ready to be send to the player.
     * This methods call two sub-methods according with the flag intermediateEffectState
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        if (!intermediateEffect)
            return getTargetEffectTwoFirstStep();
        else
            return getTargetEffectTwoSecondStep();
    }

    /**
     * Calculate all the possible player targets of the basic effect (first step) and encode them into a message ready to be send to the player.
     *
     * @return a message with all teh information about teh possible targets
     */
    private ControllerViewEvent getTargetEffectTwoFirstStep() {
        return getTargetEffectOne();
    }

    /**
     * Calculate all the possible square targets of the basic effect (second step) and encode them into a message ready to be send to the player.
     *
     * @return a message with all teh information about teh possible targets
     */
    private ControllerViewEvent getTargetEffectTwoSecondStep() {
        ArrayList<Square> possibleTargets = new ArrayList<>();
        possibleTargets.add(getFirstEffectTarget().get(0).getPosition());
        for (int direction = 0; direction < 4; direction++) {
            if (getFirstEffectTarget().get(0).getPosition().checkDirection(direction)) {
                possibleTargets.add(getFirstEffectTarget().get(0).getPosition().getNextSquare(direction));
                if (getFirstEffectTarget().get(0).getPosition().getNextSquare(direction).checkDirection(direction))
                    possibleTargets.add(getFirstEffectTarget().get(0).getPosition().getNextSquare(direction).getNextSquare(direction));
            }
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTargets), Encoder.encodeSquareTargetsY(possibleTargets));
    }

    /**
     * Check if the second effect is usable, according with the phase (intermediateEffect) and the map situation
     *
     * @return true if the effect is usable
     */
    @Override
    public boolean isUsableEffectTwo() {
        if (!intermediateEffect)
            return getEffectsEnable()[1] && getUsableEffect()[0] && getOwner().canAffortCost(getSecondEffectCost()) && !((TargetPlayerRequestEvent) getTargetEffectTwo()).getPossibleTargets().isEmpty();
        else
            return getEffectsEnable()[1] && getUsableEffect()[1] && ((TargetSquareRequestEvent) getTargetEffectTwo()).getPossibleTargetsY().length != 0;
    }
}



