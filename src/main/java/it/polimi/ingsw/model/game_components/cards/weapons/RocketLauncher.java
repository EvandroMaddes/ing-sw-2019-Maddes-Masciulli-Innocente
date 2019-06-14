package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controller_view_event.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.TwoOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

public class RocketLauncher extends TwoOptionalEffectWeapon {
    private boolean intermediateEffectState;
    private Square targetSquare;

    public RocketLauncher() {
        super(CubeColour.Red, "ROCKET LAUNCHER",
                new AmmoCube[]{new AmmoCube(CubeColour.Red), new AmmoCube(CubeColour.Red)},
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow)});
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateEffectState = false;
        targetSquare = null;
    }

    @Override
    protected void setUsableEffect() {
        updateUsableEffect(new boolean[]{true, true, false});
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && !intermediateEffectState) {
            intermediateEffectState = true;
            getUsableEffect()[2] = true;
        }
        else if (effectUsed == 0){
            getUsableEffect()[0] = false;
        }
        else if (effectUsed == 1)
            getUsableEffect()[1] = false;
        else if (effectUsed == 2) {
            updateUsableEffect(new boolean[]{false, false, false});
        }
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        if (!intermediateEffectState)
            performEffectOneFirstStep(targets);
        else
            performEffectOneSecondStep(targets);
        effectControlFlow(1);
    }

    private void performEffectOneFirstStep(List<Object> targets){
        damage((Player)targets.get(0), 2);
        getDamagedPlayer().add((Player)targets.get(0));
        targetSquare = ((Player)targets.get(0)).getPosition();
        getFirstEffectTarget().add((Player)targets.get(0));
    }

    private void performEffectOneSecondStep(List<Object> targets){
        move(getFirstEffectTarget().get(0), (Square)targets.get(0));
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        if (!intermediateEffectState)
            return getTargetEffectOneFirstStep();
        else
            return getTargetEffectOneSecondStep();
    }

    private ControllerViewEvent getTargetEffectOneFirstStep(){
        ArrayList<Player> possibleTargets = getOwner().getPosition().findVisiblePlayers();
        possibleTargets.removeAll(getOwner().getPosition().getSquarePlayers());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    private ControllerViewEvent getTargetEffectOneSecondStep(){
        ArrayList<Square> possibleDestination = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++){
            if (getFirstEffectTarget().get(0).getPosition().checkDirection(direction))
                possibleDestination.add(getFirstEffectTarget().get(0).getPosition().getNextSquare(direction));
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination));
    }

    @Override
    public boolean isUsableEffectOne() {
        if (!intermediateEffectState)
            return getUsableEffect()[0] && !((TargetPlayerRequestEvent)getTargetEffectOne()).getPossibleTargets().isEmpty();
        else
            return getUsableEffect()[0] && ((TargetSquareRequestEvent)getTargetEffectOne()).getPossibleTargetsX().length != 0;
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        move(getOwner(), (Square)targets.get(0));
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Square> possibleDestination = getOwner().getPosition().reachableInMoves(2);
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination));
    }

    @Override
    public boolean isUsableEffectTwo() {
        return getUsableEffect()[1] && getOwner().canAffortCost(getSecondEffectCost());
    }

    @Override
    public void performEffectThree(List<Object> targets) {
        for (Player p: targetSquare.getSquarePlayers()) {
            damage(p, 1);
            getDamagedPlayer().add(p);
        }
        if (getFirstEffectTarget().get(0).getPosition() != targetSquare) {
            damage(getFirstEffectTarget().get(0), 1);
            getDamagedPlayer().add(getFirstEffectTarget().get(0));
        }
        effectControlFlow(3);
    }

    @Override
    public ControllerViewEvent getTargetEffectThree() {
        ArrayList<Player> targets = targetSquare.getSquarePlayers();
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(targets), -1);
    }

    @Override
    public boolean isUsableEffectThree() {
        return getUsableEffect()[2];
    }
}
