package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

public class Hellion extends AlternateFireWeapon {

    public Hellion() {
        super(CubeColour.Red, "HELLION",
                new AmmoCube[]{new AmmoCube(CubeColour.Red), new AmmoCube(CubeColour.Yellow)},
                new AmmoCube[]{new AmmoCube(CubeColour.Red)});
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);
        damage(target, 1);
        getDamagedPlayer().add(target);
        for (Player p :target.getPosition().getSquarePlayers()){
            mark(p, 1);
        }
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = new ArrayList<>();
        possibleTargets.addAll(getOwner().getPosition().findVisiblePlayers());
        for (Player p:getOwner().getPosition().getSquarePlayers()){
            possibleTargets.remove(p);
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets),1);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);
        damage(target, 1);
        getDamagedPlayer().add(target);
        for (Player p :target.getPosition().getSquarePlayers()){
            mark(p, 2);
        }
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        return getTargetEffectOne();
    }
}
