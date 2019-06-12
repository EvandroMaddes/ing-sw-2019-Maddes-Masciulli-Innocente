package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controller_view_event.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.OneOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

public class VortexCannon extends OneOptionalEffectWeapon {
    private boolean intermediateEffectState;
    private Square vortex;

    public VortexCannon() {
        super(CubeColour.Red, "VORTEX CANNON",
                new AmmoCube[]{new AmmoCube(CubeColour.Red), new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{new AmmoCube(CubeColour.Red)});
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateEffectState = false;
    }

    @Override
    protected void setUsableEffect() {
        updateUsableEffect(new boolean[]{true, false, false});
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && !intermediateEffectState) {
            intermediateEffectState = true;
            updateUsableEffect(new boolean[]{true, false, false});
        }
        else if (effectUsed == 0)
            updateUsableEffect(new boolean[]{false, true, false});
        else if (effectUsed == 1)
            updateUsableEffect(new boolean[]{false, false, false});
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("no targets");
        if (!intermediateEffectState)
            performEffectOneFirstStep(targets);
        else
            performEffectOneSecondStep(targets);
        effectControlFlow(1);
    }

    private void performEffectOneFirstStep(List<Object> targets){
        vortex = (Square)targets.get(0);
    }

    private void performEffectOneSecondStep(List<Object> targets){
        Player target = (Player)targets.get(0);
        move(target, vortex);
        damage(target, 2);
        getDamagedPlayer().add(target);
        getFirstEffectTarget().add(target);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        if (!intermediateEffectState)
            return getTargetEffectOneFirstStep();
        else
            return getTargetEffectOneSecondStep();
    }

    private ControllerViewEvent getTargetEffectOneFirstStep(){
        ArrayList<Square> possibleVortex = getOwner().getPosition().findVisibleSquare();
        possibleVortex.remove(getOwner().getPosition());
        boolean isLegal;
        for (Square s:possibleVortex){
            isLegal = false;
            for (int direction = 0; direction < 4; direction++){
                if ( !s.getSquarePlayers().isEmpty() || (s.checkDirection(direction) && !s.getNextSquare(direction).getSquarePlayers().isEmpty()))
                    isLegal = true;
            }
            if (!isLegal)
                possibleVortex.remove(s);
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleVortex), Encoder.encodeSquareTargetsY(possibleVortex));
    }

    private ControllerViewEvent getTargetEffectOneSecondStep(){
        ArrayList<Player> possibleTargets = vortex.getNextSquarePlayer();
        possibleTargets.addAll(vortex.getSquarePlayers());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    @Override
    public boolean isUsableEffectOne() {
        if (!intermediateEffectState)
            return getUsableEffect()[0] && ((TargetSquareRequestEvent)getTargetEffectOneFirstStep()).getPossibleTargetsX().length != 0;
        else
            return getUsableEffect()[0] && !((TargetPlayerRequestEvent)getTargetEffectOneSecondStep()).getPossibleTargets().isEmpty();
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("no targets");
        int i = 0;
        while (i < 2 && i < targets.size()){
            move((Player)targets.get(i), vortex);
            damage((Player)targets.get(i), 1);
            getDamagedPlayer().add((Player)targets.get(0));
        }
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Player> possibleTargets = vortex.getSquarePlayers();
        possibleTargets.addAll(vortex.getNextSquarePlayer());
        possibleTargets.remove(getFirstEffectTarget().get(0));
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 2);
    }
}
