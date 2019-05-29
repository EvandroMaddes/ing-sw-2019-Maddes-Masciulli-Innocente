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
    boolean intermediateEffect;
    Player targetedPlayer;

    public Sledgehammer(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost, secondEffectCost);
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateEffect = false;
        targetedPlayer = null;
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
        if (targets.isEmpty())
            throw new IllegalArgumentException("no targets");
        Player target = (Player)targets.get(0);
        damage(target, 2);
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
        if (targets.isEmpty())
            throw new IllegalArgumentException("no targets");
        if (!intermediateEffect)
            performEffectTwoFirstStep(targets);
        else
            performEffectTwoSecondStep(targets);
        effectControlFlow(2);
    }

    private void performEffectTwoFirstStep(List<Object> targets){
        damage((Player)targets.get(0), 3);
        targetedPlayer = (Player)targets.get(0);
    }

    private void performEffectTwoSecondStep(List<Object> targets){
        move(targetedPlayer, (Square)targets.get(0));
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
        for (int direction = 0; direction < 4; direction++){
            if (targetedPlayer.getPosition().checkDirection(direction)){
                possibleTargets.add(targetedPlayer.getPosition().getNextSquare(direction));
                if (targetedPlayer.getPosition().getNextSquare(direction).checkDirection(direction))
                    possibleTargets.add(targetedPlayer.getPosition().getNextSquare(direction).getNextSquare(direction));
            }
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTargets), Encoder.encodeSquareTargetsY(possibleTargets));
    }

    @Override
    public boolean isUsableEffectTwo() {
        if (!intermediateEffect)
            return getEffectsEnable()[1] && getUsableEffect()[0] && getOwner().canAffortCost(getSecondEffectCost()) && !((TargetPlayerRequestEvent)getTargetEffectTwo()).getPossibleTargets().isEmpty();
        else
            return getEffectsEnable()[1] && getUsableEffect()[0] && !((TargetPlayerRequestEvent)getTargetEffectTwo()).getPossibleTargets().isEmpty();
    }
}



