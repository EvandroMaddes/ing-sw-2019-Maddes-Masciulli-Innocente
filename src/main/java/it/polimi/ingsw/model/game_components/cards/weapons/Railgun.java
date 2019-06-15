package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

public class Railgun extends AlternateFireWeapon {
    private boolean intermediateEffectState;

    public Railgun() {
        super(CubeColour.Yellow, "RAILGUN",
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Yellow), new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{});
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateEffectState = false;
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0)
            updateUsableEffect(new boolean[]{false, false, false});
        else if (effectUsed == 1 && !intermediateEffectState) {
            intermediateEffectState = true;
            updateUsableEffect(new boolean[]{false, true, false});
        }
        else if (effectUsed == 1)
            getUsableEffect()[1] = false;
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        damage((Player)targets.get(0), 3);
        getDamagedPlayer().add((Player)targets.get(0));
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getCardinalTargets();
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        damage((Player)targets.get(0), 2);
        getFirstEffectTarget().add((Player)targets.get(0));
        getDamagedPlayer().add((Player)targets.get(0));
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        if (!intermediateEffectState)
            return getTargetEffectTwoFirstStep();
        else
            return getTargetEffectTwoSecondStep();
    }

    private ControllerViewEvent getTargetEffectTwoFirstStep(){
        ArrayList<Player> possibleTargets = getCardinalTargets();
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    private ControllerViewEvent getTargetEffectTwoSecondStep(){
        Player firstTarget = getFirstEffectTarget().get(0);
        ArrayList<Player> possibleTargets;
        if (firstTarget.getPosition() == getOwner().getPosition()){
            possibleTargets = getCardinalTargets();
            possibleTargets.remove(firstTarget);
        }
        else{
            int direction = 1;
            if (firstTarget.getPosition().getRow() == getOwner().getPosition().getRow()) {
                if (firstTarget.getPosition().getColumn() < getOwner().getPosition().getColumn())
                    direction = 3;
                else
                    direction = 4;
            }
            else if (firstTarget.getPosition().getColumn() == getOwner().getPosition().getColumn() && firstTarget.getPosition().getRow() < getOwner().getPosition().getRow()) {
                    direction = 0;
            }
            Square checkedSquare = getOwner().getPosition();
            possibleTargets = checkedSquare.getSquarePlayers();
            possibleTargets.remove(getOwner());
            while (checkedSquare.getNextSquare(direction) != null){
                checkedSquare = checkedSquare.getNextSquare(direction);
                possibleTargets.addAll(checkedSquare.getSquarePlayers());
            }
            possibleTargets.remove(firstTarget);
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    private ArrayList<Player> getCardinalTargets(){
        ArrayList<Player> targets = new ArrayList<>();
        targets.addAll(getOwner().getPosition().getSquarePlayers());
        targets.remove(getOwner());
        for (int direction = 0; direction < 4; direction++){
            Square actualPosition = getOwner().getPosition();
            while (actualPosition.getNextSquare(direction) != null) {
                actualPosition = actualPosition.getNextSquare(direction);
                targets.addAll(actualPosition.getSquarePlayers());
            }
        }
        return  targets;
    }
}



