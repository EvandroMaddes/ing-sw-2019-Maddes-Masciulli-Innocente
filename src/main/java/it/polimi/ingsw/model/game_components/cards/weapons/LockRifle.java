package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.OneOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

/**
 * done
 */
public class LockRifle extends OneOptionalEffectWeapon {

    public LockRifle(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost) {
        super(colour, name, reloadCost, firstOptionalEffectCost);
    }

    @Override
    protected void setUsableEffect() {
        updateUsableEffect(new boolean[]{true, false, false});
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && getUsableEffect()[0] )
            updateUsableEffect(new boolean[]{false, true, false});
        else if ( effectUsed == 1 && getUsableEffect()[1] )
            updateUsableEffect(new boolean[]{false, false, false});
        else
            throw new IllegalArgumentException("Something wrong in effectControlFlow");
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("No targets");
        Player target = (Player)targets.get(0);
        damage(target, 2);
        mark(target, 1);
        getFirstEffectTarget().add(target);
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getOwner().getPosition().findVisiblePlayers()),1 );
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("No targets");
        mark((Player)targets.get(0), 1);
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().findVisiblePlayers();
        possibleTargets.remove(getFirstEffectTarget().get(0));
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

}
