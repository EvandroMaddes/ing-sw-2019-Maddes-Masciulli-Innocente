package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controller_view_event.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

public class Sledgehammer extends AlternateFireWeapon {
    private boolean intermediateEffect;

    public Sledgehammer() {
        super(CubeColour.Yellow, "SLDGEHAMMER",
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow)},
                new AmmoCube[]{new AmmoCube(CubeColour.Red)});
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateEffect = false;
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0)
            updateUsableEffect(new boolean[]{false, false, false});
        else if (effectUsed == 1 && !intermediateEffect){
            updateUsableEffect(new boolean[]{false, true, false});
            intermediateEffect = true;
        }
        else if (effectUsed == 1)
            updateUsableEffect(new boolean[]{false, false, false});
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);
        damage(target, 2);
        getDamagedPlayer().add(target);
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().getSquarePlayers();
        possibleTargets.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        if (intermediateEffect)
            performEffectTwoSecondStep(targets);
        else
            performEffectTwoFirstStep(targets);

        effectControlFlow(2);
    }

    private void performEffectTwoFirstStep(List<Object> targets){
        damage((Player)targets.get(0), 3);
        getDamagedPlayer().add((Player)targets.get(0));
        getFirstEffectTarget().add((Player)targets.get(0));
    }

    private void performEffectTwoSecondStep(List<Object> targets){
        move(getFirstEffectTarget().get(0), (Square)targets.get(0));
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        if (!intermediateEffect)
            return getTargetEffectTwoFirstStep();
        else
            return getTargetEffectTwoSecondStep();
    }

    private ControllerViewEvent getTargetEffectTwoFirstStep(){
        ArrayList<Player> possibleTargets = getOwner().getPosition().getSquarePlayers();
        possibleTargets.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    private ControllerViewEvent getTargetEffectTwoSecondStep(){
        ArrayList<Square> possibleTargets = new ArrayList<>();
        possibleTargets.add(getFirstEffectTarget().get(0).getPosition());
        for (int direction = 0; direction < 4; direction++){
            if (getFirstEffectTarget().get(0).getPosition().checkDirection(direction)){
                possibleTargets.add(getFirstEffectTarget().get(0).getPosition().getNextSquare(direction));
                if (getFirstEffectTarget().get(0).getPosition().getNextSquare(direction).checkDirection(direction))
                    possibleTargets.add(getFirstEffectTarget().get(0).getPosition().getNextSquare(direction).getNextSquare(direction));
            }
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTargets), Encoder.encodeSquareTargetsY(possibleTargets));
    }

    @Override
    public boolean isUsableEffectTwo() {
        if (!intermediateEffect)
            return getEffectsEnable()[1] && getUsableEffect()[0] && getOwner().canAffortCost(getSecondEffectCost()) && !((TargetPlayerRequestEvent)getTargetEffectTwo()).getPossibleTargets().isEmpty();
        else
            return getEffectsEnable()[1] && getUsableEffect()[1] && ((TargetSquareRequestEvent)getTargetEffectTwo()).getPossibleTargetsY().length != 0;
    }
}



