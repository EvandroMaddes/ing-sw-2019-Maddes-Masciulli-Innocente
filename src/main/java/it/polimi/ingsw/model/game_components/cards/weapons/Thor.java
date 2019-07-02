package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.TwoOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;
import it.polimi.ingsw.utils.custom_exceptions.EffectIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;

/**
 * done
 */
public class Thor extends TwoOptionalEffectWeapon {
    private Player lastPlayerHit;

    public Thor() {
        super(CubeColour.Blue, "T.H.O.R.",
                new AmmoCube[]{new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Red)},
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)});
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        lastPlayerHit = null;
    }

    @Override
    protected void setUsableEffect() {
        updateUsableEffect(new boolean[]{true, false, false});
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && getUsableEffect()[0])
            updateUsableEffect(new boolean[]{false, true, false});
        else if (effectUsed == 1 && getUsableEffect()[1])
            updateUsableEffect(new boolean[]{false, false, true});
        else if (effectUsed == 2 && getUsableEffect()[2])
            updateUsableEffect(new boolean[]{false, false, false});
        else
            throw new EffectIllegalArgumentException();
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);
        damage(target, 2);
        getFirstEffectTarget().add(target);
        getDamagedPlayer().add(target);
        lastPlayerHit = target;
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().findVisiblePlayers();
        possibleTargets.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);
        damage(target, 1);
        getFirstEffectTarget().add(target);
        getDamagedPlayer().add(target);
        lastPlayerHit = target;
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Player> targettablePlayer = lastPlayerHit.getPosition().findVisiblePlayers();
        targettablePlayer.removeAll(getFirstEffectTarget());
        targettablePlayer.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(targettablePlayer), 1);
    }

    @Override
    public void performEffectThree(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);
        damage(target, 2);
        getDamagedPlayer().add(target);
        effectControlFlow(3);
    }

    @Override
    public ControllerViewEvent getTargetEffectThree() {
        return getTargetEffectTwo();
    }
}
