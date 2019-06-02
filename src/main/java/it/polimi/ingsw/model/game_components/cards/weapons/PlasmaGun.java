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

public class PlasmaGun extends TwoOptionalEffectWeapon {
    private int numberOfMoves;

    public PlasmaGun(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost, AmmoCube[] secondOptionalEffectCost) {
        super(colour, name, reloadCost, firstOptionalEffectCost, secondOptionalEffectCost);
    }

    @Override
    protected void setUsableEffect() {
        updateUsableEffect(new boolean[]{true, true, false});
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        numberOfMoves = 0;
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && getUsableEffect()[0])
            updateUsableEffect(new boolean[]{false, true, true});
        else if (effectUsed == 1 && getUsableEffect()[1] && numberOfMoves == 2)
            getUsableEffect()[1] = false;
        else if (effectUsed == 2 && getUsableEffect()[2])
            getUsableEffect()[2] = false;
    }

    @Override
    public boolean isUsable() {
        return isLoaded() && (( getUsableEffect()[0] && isUsableEffectOne() ) || ( getUsableEffect()[1] && isUsableEffectTwo() ));
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("Empty targets");
        Player target = (Player)targets.get(0);
        damage(target, 2);
        getFirstEffectTarget().add(target);
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getOwner().getPosition().findVisiblePlayers()), 1);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        Square target = (Square)targets.get(0);
        //qui ha senso calcolare la distanza come distanza di manhattan, perchè si è spostato in una posizione possibile (lo sapiamo per costruzione), a una distanza di massimo 2, il che rende la cosa valida
        numberOfMoves += Math.abs(target.getColumn() - getOwner().getPosition().getColumn()) + Math.abs(target.getRow() - getOwner().getPosition().getRow());
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Square> possibleDestination = getOwner().getPosition().reachalbeInMoves(2 - numberOfMoves);
        possibleDestination.remove(getOwner().getPosition());
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination));
    }

    @Override
    public void performEffectThree(List<Object> targets) {
        damage(getFirstEffectTarget().get(0), 1);
        effectControlFlow(3);
    }

    @Override
    public ControllerViewEvent getTargetEffectThree() {
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getFirstEffectTarget()), -1);
    }
}
