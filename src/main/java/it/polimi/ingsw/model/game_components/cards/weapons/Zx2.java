package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.List;

public class Zx2 extends AlternateFireWeapon {

    public Zx2(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost, secondEffectCost);
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("no targets");
        damage(((Player)targets.get(0)),1);
        getDamagedPlayer().add((Player)targets.get(0));
        mark(((Player)targets.get(0)),2);
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getOwner().getPosition().findVisiblePlayers()), 1);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("no targets");
        int targetNumber = 1;
        for (Object p: targets) {
            if (targetNumber <= 3){
                mark((Player)p, 1);
                targetNumber++;
            }
        }
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getOwner().getPosition().findVisiblePlayers()), 3);
    }
}
