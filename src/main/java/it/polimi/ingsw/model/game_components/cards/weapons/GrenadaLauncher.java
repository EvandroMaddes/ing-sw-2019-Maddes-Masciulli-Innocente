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

public class GrenadaLauncher extends OneOptionalEffectWeapon {
    private boolean intermediateEffectState;

    public GrenadaLauncher(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost, secondEffectCost);
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateEffectState = false;
    }

    @Override
    protected void setUsableEffect() {
        updateUsableEffect(new boolean[]{true, true, false});
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && !intermediateEffectState)
            intermediateEffectState = true;
        else if (effectUsed == 0 || effectUsed == 1)
            getUsableEffect()[effectUsed] = false;
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        if (!intermediateEffectState){
            performEffectOneFirstStep(targets);
        }
        else
            performEffectOneSecondStep(targets);
        effectControlFlow(1);
    }

    private void performEffectOneFirstStep(List<Object> targets){
        if (targets.isEmpty())
            throw new IllegalArgumentException("No targets");
        damage((Player)targets.get(0), 1);
        getFirstEffectTarget().add((Player)targets.get(0));
    }

    private void performEffectOneSecondStep(List<Object> targets){
        if (targets.isEmpty())
            throw new IllegalArgumentException("No targets");
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
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getOwner().getPosition().findVisiblePlayers()), 1);
    }

    private ControllerViewEvent getTargetEffectOneSecondStep(){
        ArrayList<Square> possibleDestination = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            if (getFirstEffectTarget().get(0).getPosition().checkDirection(i))
                possibleDestination.add(getFirstEffectTarget().get(0).getPosition().getNextSquare(i));
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination));
    }

    @Override
    public boolean isUsableEffectOne() {
        if (!intermediateEffectState)
            return getUsableEffect()[0] && getEffectsEnable()[0] && !((TargetPlayerRequestEvent)getTargetEffectOne()).getPossibleTargets().isEmpty();
        else
            return getUsableEffect()[0];
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("no targets");
        Square targetSquare = (Square)targets.get(0);
        for (Player p:targetSquare.getSquarePlayers()){
            if (p != getOwner())
                damage(p, 1);
        }
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Square> possibleTargets = new ArrayList<>();
        for (Player p:getOwner().getPosition().findVisiblePlayers()){
            if (!possibleTargets.contains(p.getPosition()))
                possibleTargets.add(p.getPosition());
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTargets), Encoder.encodeSquareTargetsY(possibleTargets));
    }
}
