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
 * Class for the weapon Tractor Beam
 *
 * @author Federico Innocente
 */
public class TractorBeam extends AlternateFireWeapon {
    /**
     * Flag to determinate if the first step of the first effect has been performed
     */
    private boolean intermediateEffectState;

    /**
     * Constructor
     */
    public TractorBeam() {
        super(CubeColour.Blue, "TRACTOR BEAM",
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{new AmmoCube(CubeColour.Red), new AmmoCube(CubeColour.Yellow)});
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
        } else if (effectUsed == 0 || effectUsed == 1)
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
        getFirstEffectTarget().add((Player) targets.get(0));
    }

    /**
     * Perform the second step of the base effect
     *
     * @param targets is a list of targets chosen by the player
     */
    private void performEffectOneSecondStep(List<Object> targets) {
        move(getFirstEffectTarget().get(0), (Square) targets.get(0));
        damage(getFirstEffectTarget().get(0), 1);
        getDamagedPlayer().add(getFirstEffectTarget().get(0));
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
        ArrayList<Square> possibleStartingSquare = getOwner().getPosition().findVisibleSquare();
        ArrayList<Square> notVisibleStartingSquare = new ArrayList<>();
        for (Square visibleSquare : possibleStartingSquare) {
            for (Square notVisibleSquare : visibleSquare.reachableInMoves(2)) {
                if (!possibleStartingSquare.contains(notVisibleSquare) && !notVisibleStartingSquare.contains(notVisibleSquare))
                    notVisibleStartingSquare.add(notVisibleSquare);
            }
        }
        possibleStartingSquare.addAll(notVisibleStartingSquare);
        for (Square s : possibleStartingSquare) {
            possibleTargets.addAll(s.getSquarePlayers());
        }
        possibleTargets.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    /**
     * Calculate all the possible square targets of the basic effect (second step) and encode them into a message ready to be send to the player.
     *
     * @return a message with all teh information about teh possible targets
     */
    private ControllerViewEvent getTargetEffectOneSecondStep() {
        ArrayList<Square> possibleDestination = getFirstEffectTarget().get(0).getPosition().reachableInMoves(2);
        ArrayList<Square> visibleSquare = getOwner().getPosition().findVisibleSquare();
        ArrayList<Square> possibleTargets = new ArrayList<>();
        for (Square s : possibleDestination) {
            if (visibleSquare.contains(s))
                possibleTargets.add(s);
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTargets), Encoder.encodeSquareTargetsY(possibleTargets));
    }

    /**
     * Check if the fist effect is usable, according with the phase (intermediateEffectState) and the map situation
     *
     * @return true if the effect is usable
     */
    @Override
    public boolean isUsableEffectOne() {
        if (!intermediateEffectState)
            return getUsableEffect()[0] && !((TargetPlayerRequestEvent) getTargetEffectOneFirstStep()).getPossibleTargets().isEmpty();
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
        move((Player) targets.get(0), getOwner().getPosition());
        damage((Player) targets.get(0), 3);
        getDamagedPlayer().add((Player) targets.get(0));
        effectControlFlow(2);
    }

    /**
     * Calculate all the possible player targets of the second effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Player> possibleTargets = new ArrayList<>();
        ArrayList<Square> reachablePosition = getOwner().getPosition().reachableInMoves(2);
        for (Square s : reachablePosition) {
            ArrayList<Player> squarePlayers = s.getSquarePlayers();
            for (Player currTarget : squarePlayers) {
                if (!possibleTargets.contains(currTarget)) {
                    possibleTargets.add(currTarget);
                }
            }
        }
        while (possibleTargets.contains(getOwner())) {
            possibleTargets.remove(getOwner());
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

}

