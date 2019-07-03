package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.OneOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the weapon Vortex Cannon
 *
 * @author Federico Innocente
 */
public class VortexCannon extends OneOptionalEffectWeapon {
    /**
     * Flag to determinate if the first step of the first effect has been performed
     */
    private boolean intermediateEffectState;

    /**
     * Save the position of the vortex
     */
    private Square vortex;

    /**
     * Constructor
     */
    public VortexCannon() {
        super(CubeColour.Red, "VORTEX CANNON",
                new AmmoCube[]{new AmmoCube(CubeColour.Red), new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{new AmmoCube(CubeColour.Red)});
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
        if (effectUsed == 0 && !intermediateEffectState) {
            intermediateEffectState = true;
            updateUsableEffect(new boolean[]{true, false, false});
        } else if (effectUsed == 0)
            updateUsableEffect(new boolean[]{false, true, false});
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
        vortex = (Square) targets.get(0);
    }

    /**
     * Perform the second step of the base effect
     *
     * @param targets is a list of targets chosen by the player
     */
    private void performEffectOneSecondStep(List<Object> targets) {
        Player target = (Player) targets.get(0);
        move(target, vortex);
        damage(target, 2);
        getDamagedPlayer().add(target);
        getFirstEffectTarget().add(target);
    }

    /**
     * Calculate all the possible square or player targets of the basic effect and encode them into a message ready to be send to the player.
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
     * Calculate all the possible square targets of the basic effect (first step) and encode them into a message ready to be send to the player.
     *
     * @return a message with all teh information about teh possible targets
     */
    private ControllerViewEvent getTargetEffectOneFirstStep() {
        ArrayList<Square> visibleSquare = getOwner().getPosition().findVisibleSquare();
        ArrayList<Square> possibleVortex = new ArrayList<>();
        visibleSquare.remove(getOwner().getPosition());
        for (Square s : visibleSquare) {
            if (!s.getSquarePlayers().isEmpty() || hasOtherPlayersNext(s))
                possibleVortex.add(s);
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleVortex), Encoder.encodeSquareTargetsY(possibleVortex));
    }

    /**
     * Calculate all the possible player targets of the basic effect (second step) and encode them into a message ready to be send to the player.
     *
     * @return a message with all teh information about teh possible targets
     */
    private ControllerViewEvent getTargetEffectOneSecondStep() {
        ArrayList<Player> possibleTargets = vortex.getNextSquarePlayer();
        possibleTargets.addAll(vortex.getSquarePlayers());
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
            return getUsableEffect()[0] && ((TargetSquareRequestEvent) getTargetEffectOneFirstStep()).getPossibleTargetsX().length != 0;
        else
            return getUsableEffect()[0] && !((TargetPlayerRequestEvent) getTargetEffectOneSecondStep()).getPossibleTargets().isEmpty();
    }

    /**
     * Perform the second effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        int i = 0;
        while (i < 2 && i < targets.size()) {
            move((Player) targets.get(i), vortex);
            damage((Player) targets.get(i), 1);
            getDamagedPlayer().add((Player) targets.get(0));
            i++;
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
        ArrayList<Player> possibleTargets = new ArrayList<>();
        possibleTargets.addAll(vortex.getSquarePlayers());
        possibleTargets.addAll(vortex.getNextSquarePlayer());
        possibleTargets.remove(getFirstEffectTarget().get(0));
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 2);
    }

    /**
     * Check if a square has any player on it's next square
     *
     * @param square is the checked square
     * @return true if square's next cardinal squares have at least one player
     */
    private boolean hasOtherPlayersNext(Square square) {
        ArrayList<Player> nextPlayers = square.getNextSquarePlayer();
        nextPlayers.remove(getOwner());
        return !nextPlayers.isEmpty();
    }
}
