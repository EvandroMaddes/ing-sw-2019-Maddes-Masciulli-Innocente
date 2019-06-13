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
public class Whisper extends Weapon {
    /**
     *
     */
    public Whisper() {
        super(CubeColour.Blue, "WHISPER",
                new AmmoCube[]{new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Yellow)});
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);

        damage(target, 3);
        mark(target, 1);
        getDamagedPlayer().add(target);

        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().findVisiblePlayers();
        ArrayList<Square> invalidTargets = getOwner().getPosition().reachableInMoves(1);
        for (Square s:invalidTargets) {
            possibleTargets.removeAll(s.getSquarePlayers());
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }
}
