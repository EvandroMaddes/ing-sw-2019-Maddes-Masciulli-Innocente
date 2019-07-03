package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.Weapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the weapon Heatseeker
 *
 * @author Federico Innoeente
 */
public class Heatseeker extends Weapon {

    /**
     * Constructor
     */
    public Heatseeker() {
        super(CubeColour.Red, "HEATSEEKER",
                new AmmoCube[]{new AmmoCube(CubeColour.Red), new AmmoCube(CubeColour.Red), new AmmoCube(CubeColour.Yellow)});
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
        damage(target, 3);
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
        ArrayList<Player> notVisiblePlayer = new ArrayList<>();

        Square topRightSquare = getOwner().getPosition();

        while (!(topRightSquare.getRow() == 0 && ((topRightSquare.getColumn() == 2 && topRightSquare.getNextSquare(2) == null) || topRightSquare.getColumn() == 3))) {
            if (topRightSquare.getColumn() == 3)
                topRightSquare = topRightSquare.getNextSquare(3);
            if (topRightSquare.getRow() != 0)
                topRightSquare = topRightSquare.getNextSquare(0);
            else
                topRightSquare = topRightSquare.getNextSquare(2);
        }

        for (Player p : topRightSquare.getSquarePlayers()) {
            if (!visiblePlayer.contains(p))
                notVisiblePlayer.add(p);
        }
        while (!(topRightSquare.getRow() == 2 && (topRightSquare.getColumn() == 0 || (topRightSquare.getColumn() == 1 && topRightSquare.getNextSquare(3) == null)))) {
            if (topRightSquare.getRow() == 0 && topRightSquare.getColumn() == 0)
                topRightSquare = topRightSquare.getNextSquare(1);
            else if (topRightSquare.getRow() == 0)
                topRightSquare = topRightSquare.getNextSquare(3);
            else if (topRightSquare.getRow() == 1 && topRightSquare.getColumn() == 3)
                topRightSquare = topRightSquare.getNextSquare(1);
            else if (topRightSquare.getRow() == 1)
                topRightSquare = topRightSquare.getNextSquare(2);
            else
                topRightSquare = topRightSquare.getNextSquare(3);

            for (Player p : topRightSquare.getSquarePlayers()) {
                if (!visiblePlayer.contains(p))
                    notVisiblePlayer.add(p);
            }
        }

        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(notVisiblePlayer), 1);
    }

    /**
     * Check if the weapon is usable.
     * To be usable, the weapon must be loaded and at least one of its effects must be correctly usable
     *
     * @return true if the weapon is usable, false otherwise
     */
    @Override
    public boolean isUsable() {
        return isLoaded() && getUsableEffect()[0] && isUsableEffectOne();
    }
}
