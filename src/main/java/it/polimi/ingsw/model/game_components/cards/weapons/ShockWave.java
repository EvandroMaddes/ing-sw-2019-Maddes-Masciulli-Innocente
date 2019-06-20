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

public class ShockWave extends AlternateFireWeapon {
    private boolean firstRequestDone;
    private boolean secondRequestDone;

    public ShockWave() {
        super(CubeColour.Yellow, "SHOCKWAVE",
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow)},
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow)});
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        firstRequestDone = false;
        secondRequestDone = false;
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 1)
            updateUsableEffect(new boolean[]{false, false, false});
        else if (effectUsed == 0){
            if (!firstRequestDone) {
                firstRequestDone = true;
                updateUsableEffect(new boolean[]{true, false, false});
            }
            else if (!secondRequestDone)
                secondRequestDone = true;
            else
                updateUsableEffect(new boolean[]{false, false, false});
        }
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        damage((Player)targets.get(0), 1);
        getDamagedPlayer().add((Player)targets.get(0));
        getFirstEffectTarget().add((Player)targets.get(0));
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().getNextSquarePlayer();
        for (Player p: getFirstEffectTarget()) {
            possibleTargets.removeAll(p.getPosition().getSquarePlayers());
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        ArrayList<Player> nextSquarePlayer = getOwner().getPosition().getNextSquarePlayer();
        for (Player p: nextSquarePlayer) {
            damage(p, 1);
            getDamagedPlayer().add(p);
        }


        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getOwner().getPosition().getNextSquarePlayer()), -1);
    }
}
