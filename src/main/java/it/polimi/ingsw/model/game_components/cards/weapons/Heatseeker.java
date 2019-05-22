package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

/**
 * done
 */
public class Heatseeker extends Weapon {
    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     */
    public Heatseeker(CubeColour colour, String name, AmmoCube[] reloadCost) {
        super(colour, name,new boolean[1], reloadCost);
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("Nessun target selezionato");
        Player target = (Player) targets.get(0);
        damage(target, 3);

        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> visiblePlayer = getOwner().getPosition().findVisiblePlayers();
        ArrayList<Player> notVisiblePlayer = new ArrayList<>();

        Square topRightSquare = getOwner().getPosition();

        while(topRightSquare.getRow() == 0 && ( topRightSquare.getColumn() == 3 || ( topRightSquare.getColumn() == 2 && topRightSquare.getNextSquare(2) == null ) ) ){
            if (topRightSquare.getColumn() == 3)
                topRightSquare = topRightSquare.getNextSquare(3);
            if (topRightSquare.getRow() != 0)
                topRightSquare = topRightSquare.getNextSquare(0);
            else
                topRightSquare = topRightSquare.getNextSquare(2);
        }

        while (topRightSquare.getRow() == 2 && ( topRightSquare.getColumn() == 0 || ( topRightSquare.getColumn() == 1 && topRightSquare.getNextSquare(3) == null ) ) ){
            for (Player p: topRightSquare.getSquarePlayers()) {
                if (!visiblePlayer.contains(p))
                    notVisiblePlayer.add(p);
            }
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
        }

        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(notVisiblePlayer), 1);
    }

    @Override
    public boolean isUsable() {
        return isLoaded() && getUsableEffect()[0] && isUsableEffectOne();
    }
}
