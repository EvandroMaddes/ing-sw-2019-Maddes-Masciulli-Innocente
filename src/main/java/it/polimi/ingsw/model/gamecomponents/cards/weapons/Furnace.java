package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
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
 * Class for the weapon Furnace
 *
 * @author Federico Inncente
 */
public class Furnace extends AlternateFireWeapon {

    /**
     * Constructor
     */
    public Furnace() {
        super(CubeColour.Red, "FURNACE",
                new AmmoCube[]{new AmmoCube(CubeColour.Red), new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{});
    }

    /**
     * Perform the basic effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        ArrayList<Player> roomPlayers = ((Square) targets.get(0)).findRoomPlayers();
        for (Player p : roomPlayers) {
            damage(p, 1);
            getDamagedPlayer().add(p);
        }
        effectControlFlow(1);
    }

    /**
     * Calculate all the possible square targets of the basic effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Square> possibleTarget = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (getOwner().getPosition().checkDirection(i) && !getOwner().getPosition().getNextSquare(i).getSquareColour().equals(getOwner().getPosition().getSquareColour()) && !getOwner().getPosition().getNextSquare(i).findRoomPlayers().isEmpty())
                possibleTarget.add(getOwner().getPosition().getNextSquare(i));
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTarget), Encoder.encodeSquareTargetsY(possibleTarget));
    }

    /**
     * Check if the base effect is usable in the current condition.
     * That means that teh effect must be enabled, the player can pay for it and that it has targets in range.
     *
     * @return true if the effect can be performed, false otherwise
     */
    @Override
    public boolean isUsableEffectOne() {
        return getEffectsEnable()[0] && getUsableEffect()[0] && ((TargetSquareRequestEvent) getTargetEffectOne()).getPossibleTargetsX().length != 0;
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
        for (Player p : target.getSquarePlayers()) {
            damage(p, 1);
            mark(p, 1);
            getDamagedPlayer().add(p);
        }
        effectControlFlow(1);
    }

    /**
     * Calculate all the possible square targets of the second effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Square> possibleTargets = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (getOwner().getPosition().checkDirection(i) && !getOwner().getPosition().getNextSquare(i).getSquarePlayers().isEmpty())
                possibleTargets.add(getOwner().getPosition().getNextSquare(i));
        }
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
        return getEffectsEnable()[1] && getUsableEffect()[1] && ((TargetSquareRequestEvent) getTargetEffectOne()).getPossibleTargetsX().length != 0 && getOwner().canAffortCost(getSecondEffectCost());
    }
}
