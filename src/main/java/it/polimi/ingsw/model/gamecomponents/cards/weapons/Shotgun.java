package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

public class Shotgun extends AlternateFireWeapon {
    private boolean intermediateStateEffect;

    public Shotgun() {
        super(CubeColour.Yellow, "SHOTGUN",
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Yellow)},
                new AmmoCube[]{});
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateStateEffect = false;
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && !intermediateStateEffect){
            intermediateStateEffect = true;
            updateUsableEffect(new boolean[]{true, false, false});
        }
        else if (effectUsed == 0 || effectUsed == 1){
            updateUsableEffect(new boolean[]{false, false, false});
        }
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        if (!intermediateStateEffect)
            performEffectOneFirstStep(targets);
        else
            performEffectOneSecondStep(targets);
        effectControlFlow(1);
    }

    private void performEffectOneFirstStep(List<Object> targets){
        Player target = (Player)targets.get(0);
        damage(target, 3);
        getDamagedPlayer().add(target);
        getFirstEffectTarget().add(target);
    }

    private void performEffectOneSecondStep(List<Object> targets){
        Square destination = (Square)targets.get(0);
        move(getFirstEffectTarget().get(0), destination);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        if (!intermediateStateEffect)
            return getTargetEffectOneFirstStep();
        else
            return getTargetEffectOneSecondStep();
    }

    private ControllerViewEvent getTargetEffectOneFirstStep(){
        ArrayList<Player> possibleTargets = getOwner().getPosition().getSquarePlayers();
        possibleTargets.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    private ControllerViewEvent getTargetEffectOneSecondStep(){
        ArrayList<Square> possibleDestination = new ArrayList<>();
        for (int destination = 0; destination < 4; destination++){
            if (getFirstEffectTarget().get(0).getPosition().checkDirection(destination))
                possibleDestination.add(getFirstEffectTarget().get(0).getPosition().getNextSquare(destination));
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination));
    }

    @Override
    public boolean isUsableEffectOne() {
        if (!intermediateStateEffect)
            return getUsableEffect()[0] && !((TargetPlayerRequestEvent)getTargetEffectOne()).getPossibleTargets().isEmpty();
        else
            return getUsableEffect()[0];
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        damage((Player)targets.get(0), 2);
        getDamagedPlayer().add((Player)targets.get(0));
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Player> possibleTargets = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++){
            if (getOwner().getPosition().checkDirection(direction))
                possibleTargets.addAll(getOwner().getPosition().getNextSquare(direction).getSquarePlayers());
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    @Override
    public boolean isUsableEffectTwo() {
        return getUsableEffect()[1] && !((TargetPlayerRequestEvent)getTargetEffectTwo()).getPossibleTargets().isEmpty();
    }
}
