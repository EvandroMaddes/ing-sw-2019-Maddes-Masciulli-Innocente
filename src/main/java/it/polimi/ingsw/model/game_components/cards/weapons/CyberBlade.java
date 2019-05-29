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

public class CyberBlade extends TwoOptionalEffectWeapon {

    public CyberBlade(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost, AmmoCube[] thirdEffectCost) {
        super(colour, name, reloadCost, secondEffectCost, thirdEffectCost);
    }

    @Override
    public void setLoaded() {
        updateUsableEffect(new boolean[]{true, true, false});
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0) {
            getUsableEffect()[0] = false;
            getUsableEffect()[2] = true;
        }
        else if (effectUsed == 1 || effectUsed == 2)
            getUsableEffect()[effectUsed] = false;
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("no taregts");
        Player target = (Player)targets.get(0);
        damage(target, 2);
        getFirstEffectTarget().add(target);
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
            throw new IllegalArgumentException("no taregts");
        Square target = (Square) targets.get(0);
        move(getOwner(), target);
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Square> possibleTargets = getOwner().getPosition().reachalbeInMoves(1);
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTargets), Encoder.encodeSquareTargetsY(possibleTargets));
    }

    @Override
    public void performEffectThree(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("no taregts");
        Player target = (Player)targets.get(0);
        damage(target, 2);
        effectControlFlow(3);
    }

    @Override
    public ControllerViewEvent getTargetEffectThree() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().getSquarePlayers();
        possibleTargets.remove(getOwner());
        possibleTargets.remove(getFirstEffectTarget().get(0));
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }
}
