package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

public class PowerGlove extends AlternateFireWeapon {
    private boolean intermediateEffectState;
    private int firstStepDirection;

    public PowerGlove() {
        super(CubeColour.Yellow, "POWER GLOVE",
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)});
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateEffectState = false;
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0)
            updateUsableEffect(new boolean[]{false, false, false});
        else if (effectUsed == 1 && !intermediateEffectState){
            intermediateEffectState = true;
            updateUsableEffect(new boolean[]{false, true, false});
        }
        else if (effectUsed == 1)
            updateUsableEffect(new boolean[]{false, false, false});
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);
        move(getOwner(), target.getPosition());
        damage(target, 1);
        getDamagedPlayer().add(target);
        mark(target, 2);
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().getNextSquarePlayer();
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);
        for (int direction = 0 ; direction < 4; direction++){
            if (getOwner().getPosition().getNextSquare(direction) == target.getPosition())
                firstStepDirection = direction;
        }
        move(getOwner(), target.getPosition());
        damage(target, 2);
        getDamagedPlayer().add(target);
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        if (!intermediateEffectState)
            return getTargetEffectOne();
        else {
            ArrayList<Player> possibleTargets = new ArrayList<>();
            if (getOwner().getPosition().checkDirection(firstStepDirection))
                possibleTargets.addAll(getOwner().getPosition().getNextSquare(firstStepDirection).getSquarePlayers());
            return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
        }
    }

    @Override
    public boolean isUsableEffectTwo() {
        if (!intermediateEffectState)
            return getUsableEffect()[1] && getOwner().canAffortCost(getSecondEffectCost()) && !((TargetPlayerRequestEvent)getTargetEffectTwo()).getPossibleTargets().isEmpty();
        else
            return getUsableEffect()[1] && !((TargetPlayerRequestEvent)getTargetEffectTwo()).getPossibleTargets().isEmpty();
    }
}

