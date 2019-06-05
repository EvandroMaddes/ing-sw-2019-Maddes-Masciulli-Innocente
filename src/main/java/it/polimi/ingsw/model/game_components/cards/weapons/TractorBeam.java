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
import java.util.Iterator;
import java.util.List;

public class TractorBeam extends AlternateFireWeapon {
    private boolean intermediateEffectState;

    public TractorBeam(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
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
        if (effectUsed == 0 && !intermediateEffectState){
            intermediateEffectState = true;
            updateUsableEffect(new boolean[]{true, false, false});
        }
        else if (effectUsed == 0 || effectUsed == 1)
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
        getFirstEffectTarget().add((Player)targets.get(0));
    }

    private void performEffectOneSecondStep(List<Object> targets){
        move(getFirstEffectTarget().get(0), (Square)targets.get(0));
        damage(getFirstEffectTarget().get(0), 1);
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
        ArrayList<Square> possibleStartingSquare = getOwner().getPosition().findVisibleSquare();
        ArrayList<Square> notVisibleStartingSquare = new ArrayList<>();
        for (Square visibleSqure:possibleStartingSquare){
            for (Square notVisibleSquare: visibleSqure.reachalbeInMoves(2)){
                if (!possibleStartingSquare.contains(notVisibleSquare))
                    notVisibleStartingSquare.add(notVisibleSquare);
            }

        }


        possibleStartingSquare.addAll(notVisibleStartingSquare);
        for (Square s:possibleStartingSquare)
        {
            ArrayList<Player> squarePlayers = s.getSquarePlayers();
            for (Player currTarget : squarePlayers) {
                if(!possibleTargets.contains(currTarget)) {
                    possibleTargets.add(currTarget);
                }
            }
            while(possibleTargets.contains(getOwner())){
                possibleTargets.remove(getOwner());
            }
            //possibleTargets.addAll(s.getSquarePlayers());
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    private ControllerViewEvent getTargetEffectOneSecondStep(){
        ArrayList<Square> possibleDestination = getFirstEffectTarget().get(0).getPosition().reachalbeInMoves(2);
        ArrayList<Square> validDestination = getOwner().getPosition().findVisibleSquare();
        ArrayList<Square> possibleTargets = new ArrayList<>();
        for (Square s:possibleDestination){
            if (validDestination.contains(s))
                possibleTargets.add(s);
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTargets), Encoder.encodeSquareTargetsY(possibleTargets));
    }

    @Override
    public boolean isUsableEffectOne() {
        if (!intermediateEffectState)
            return getUsableEffect()[0] && !((TargetPlayerRequestEvent)getTargetEffectOneFirstStep()).getPossibleTargets().isEmpty();
        else
            return getUsableEffect()[0];
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("no targets");
        move((Player)targets.get(0), getOwner().getPosition());
        damage((Player)targets.get(0), 3);
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Player> possibleTargets = new ArrayList<>();
        ArrayList<Square> reachablePosition = getOwner().getPosition().reachalbeInMoves(2);
        for (Square s: reachablePosition){
            ArrayList<Player> squarePlayers = s.getSquarePlayers();
            for (Player currTarget : squarePlayers) {
                if(!possibleTargets.contains(currTarget)) {
                    possibleTargets.add(currTarget);
                }
            }
            //possibleTargets.addAll(s.getSquarePlayers());
        }
        while(possibleTargets.contains(getOwner())){
            possibleTargets.remove(getOwner());
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

}

