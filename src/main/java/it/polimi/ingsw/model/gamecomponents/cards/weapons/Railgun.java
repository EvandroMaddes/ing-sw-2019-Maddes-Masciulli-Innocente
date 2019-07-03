package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the weapon Railgun
 */
public class Railgun extends AlternateFireWeapon {

    /**
     * Flag to determinate if the first step of the second effect has been performed
     */
    private boolean intermediateEffectState;

    /**
     * Constructor
     */
    public Railgun() {
        super(CubeColour.Yellow, "RAILGUN",
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{});
    }

    /**
     * Set the weapon loaded and reset the flag to false
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
            getUsableEffect()[1] = false;
    }

    /**
     * Perform the basic effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        damage((Player) targets.get(0), 3);
        getDamagedPlayer().add((Player) targets.get(0));
        effectControlFlow(1);
    }

    /**
     * Calculate all the possible player targets of the basic effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getCardinalTargets();
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
        damage((Player) targets.get(0), 2);
        getFirstEffectTarget().add((Player) targets.get(0));
        getDamagedPlayer().add((Player) targets.get(0));
        effectControlFlow(2);
    }

    /**
     * Calculate all the possible player targets of the second effect and encode them into a message ready to be send to the player.
     * This methods call two sub-methods according with the flag intermediateEffectState
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        if (!intermediateEffectState)
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
     * Calculate all the possible player targets of the basic effect (second step) and encode them into a message ready to be send to the player.
     *
     * @return a message with all teh information about teh possible targets
     */
    private ControllerViewEvent getTargetEffectTwoSecondStep() {
        Player firstTarget = getFirstEffectTarget().get(0);
        ArrayList<Player> possibleTargets;
        if (firstTarget.getPosition() == getOwner().getPosition()) {
            possibleTargets = getCardinalTargets();
            possibleTargets.remove(firstTarget);
        } else {
            int direction = 1;
            if (firstTarget.getPosition().getRow() == getOwner().getPosition().getRow()) {
                if (firstTarget.getPosition().getColumn() < getOwner().getPosition().getColumn())
                    direction = 3;
                else
                    direction = 4;
            } else if (firstTarget.getPosition().getColumn() == getOwner().getPosition().getColumn() && firstTarget.getPosition().getRow() < getOwner().getPosition().getRow()) {
                direction = 0;
            }
            Square checkedSquare = getOwner().getPosition();
            possibleTargets = checkedSquare.getSquarePlayers();
            possibleTargets.remove(getOwner());
            while (checkedSquare.getNextSquare(direction) != null) {
                checkedSquare = checkedSquare.getNextSquare(direction);
                possibleTargets.addAll(checkedSquare.getSquarePlayers());
            }
            possibleTargets.remove(firstTarget);
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    /**
     * Method to get all the player targets in the cardinal direction from the weapon's owner position. Walls are ignored.
     *
     * @return all the players in cardinal direction form the owner
     */
    private ArrayList<Player> getCardinalTargets() {
        ArrayList<Player> targets = new ArrayList<>(getOwner().getPosition().getSquarePlayers());
        targets.remove(getOwner());
        for (int direction = 0; direction < 4; direction++) {
            Square actualPosition = getOwner().getPosition();
            while (actualPosition.getNextSquare(direction) != null) {
                actualPosition = actualPosition.getNextSquare(direction);
                targets.addAll(actualPosition.getSquarePlayers());
            }
        }
        return targets;
    }
}



