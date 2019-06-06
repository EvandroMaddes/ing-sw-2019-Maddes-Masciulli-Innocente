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

public class Flamethrower extends AlternateFireWeapon {
    private boolean intermediateEffectState;
    private int firstEffectDirection;

    public Flamethrower(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost, secondEffectCost);
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateEffectState = false;
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && !intermediateEffectState) {
            intermediateEffectState = true;
            updateUsableEffect(new boolean[]{true, false, false});
        }
        else if (effectUsed == 0)
            getUsableEffect()[0] = false;
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
        damage((Player)targets.get(0), 1);
        getDamagedPlayer().add((Player)targets.get(0));
        for (int direction = 0; direction < 4; direction++)
            if (getOwner().getPosition().getNextSquare(direction) == ((Player)targets.get(0)).getPosition())
                firstEffectDirection = direction;
    }

    private void performEffectOneSecondStep(List<Object> targets){
        damage((Player)targets.get(0), 1);
        getDamagedPlayer().add((Player)targets.get(0));
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        if (!intermediateEffectState)
            return getTargetEffectOneFirstStep();
        else
            return getTargetEffectOneSecondStep();
    }

    private ControllerViewEvent getTargetEffectOneFirstStep(){
        ArrayList<Player> possibleTargets = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++){
            if (getOwner().getPosition().checkDirection(direction))
                possibleTargets.addAll(getOwner().getPosition().getNextSquare(direction).getSquarePlayers());
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    private ControllerViewEvent getTargetEffectOneSecondStep(){
        ArrayList<Player> possibleTargets = new ArrayList<>();
        if (getOwner().getPosition().getNextSquare(firstEffectDirection).checkDirection(firstEffectDirection))
            possibleTargets.addAll(getOwner().getPosition().getNextSquare(firstEffectDirection).getNextSquare(firstEffectDirection).getSquarePlayers());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    @Override
    public boolean isUsableEffectOne() {
        if (!intermediateEffectState)
            return getUsableEffect()[0] && !((TargetPlayerRequestEvent)getTargetEffectOne()).getPossibleTargets().isEmpty();
        else
            return getUsableEffect()[0] && getOwner().canAffortCost(getSecondEffectCost()) && !((TargetPlayerRequestEvent)getTargetEffectOne()).getPossibleTargets().isEmpty();
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("no targets");
        int direction = 0;
        Square target = (Square)targets.get(0);
        for (int i = 0; i < 4; i++){
            if(getOwner().getPosition().getNextSquare(i) == target)
                direction = i;
        }
        for (Player p:target.getSquarePlayers()){
            damage(p, 2);
            getDamagedPlayer().add(p);
        }
        if (target.checkDirection(direction))
            target = target.getNextSquare(direction);
        for (Player p:target.getSquarePlayers()) {
            damage(p, 1);
            getDamagedPlayer().add(p);
        }
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Square> possibleTargets = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++){
            if (getOwner().getPosition().checkDirection(direction)){
                if (!getOwner().getPosition().getNextSquare(direction).getSquarePlayers().isEmpty())
                    possibleTargets.add(getOwner().getPosition().getNextSquare(direction));
                else if (getOwner().getPosition().getNextSquare(direction).checkDirection(direction) && !getOwner().getPosition().getNextSquare(direction).getNextSquare(direction).getSquarePlayers().isEmpty())
                    if (!getOwner().getPosition().getNextSquare(direction).getNextSquare(direction).getSquarePlayers().isEmpty())
                        possibleTargets.add(getOwner().getPosition().getNextSquare(direction));
            }
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTargets), Encoder.encodeSquareTargetsY(possibleTargets));
    }
}
