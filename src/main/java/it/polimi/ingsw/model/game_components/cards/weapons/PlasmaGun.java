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

    public PlasmaGun() {
        super(CubeColour.Blue, "PLASMA GUN",
                new AmmoCube[]{new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Yellow)},
                new AmmoCube[]{},
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)});
    }

    @Override
    protected void setUsableEffect() {
        updateUsableEffect(new boolean[]{true, true, false});
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 ) {
            getUsableEffect()[0] = false;
            getUsableEffect()[2] = true;
        }
        else if (effectUsed == 1 && !getUsableEffect()[0])
            updateUsableEffect(new boolean[]{false,false,false});
        else if (effectUsed == 1)
            getUsableEffect()[1] = false;
        else if (effectUsed == 2)
            getUsableEffect()[2] = false;
    }

    @Override
    public boolean isUsable() {
        return isLoaded() && (( getUsableEffect()[0] && isUsableEffectOne() ) || ( getUsableEffect()[1] && isUsableEffectTwo() ) || (getUsableEffect()[2] && isUsableEffectThree()) );
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);
        damage(target, 2);
        getFirstEffectTarget().add(target);
        getDamagedPlayer().add(target);
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().findVisiblePlayers();
        possibleTargets.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        Square target = (Square)targets.get(0);
        getOwner().setPosition(target);
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Square> possibleDestination = getOwner().getPosition().reachableInMoves(2 );
        possibleDestination.remove(getOwner().getPosition());
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination));
    }

    @Override
    public boolean isUsableEffectTwo() {
        return getUsableEffect()[1] && ((TargetSquareRequestEvent)getTargetEffectTwo()).getPossibleTargetsY().length != 0;
    }

    @Override
    public void performEffectThree(List<Object> targets) {
        damage(getFirstEffectTarget().get(0), 1);
        getDamagedPlayer().add(getFirstEffectTarget().get(0));
        effectControlFlow(3);
    }

    @Override
    public ControllerViewEvent getTargetEffectThree() {
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getFirstEffectTarget()), -1);
    }
}
