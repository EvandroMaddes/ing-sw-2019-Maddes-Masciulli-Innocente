package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
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
public class Whisper extends Weapon {
    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     */
    public Whisper(CubeColour colour, String name, AmmoCube[] reloadCost) {
        super(colour, name, new boolean[1], reloadCost);
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("Nessun target passato");
        Player target = (Player)targets.get(0);

        damage(target, 3);
        mark(target, 1);

        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().findVisiblePlayers();

        for (Player p: possibleTargets) {
            if ( Math.abs(p.getPosition().getRow() - getOwner().getPosition().getRow() ) + Math.abs(p.getPosition().getColumn() - getOwner().getPosition().getColumn() ) < 2 )
                possibleTargets.remove(p);
        }

        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }
}
