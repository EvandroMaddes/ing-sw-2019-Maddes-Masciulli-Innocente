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

/**
 * done
 */
public class Electroscythe extends AlternateFireWeapon {

    public Electroscythe() {
        super(CubeColour.Blue, "ELECTROSCYTHE",
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)},
                new AmmoCube[]{new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Red)});
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        ArrayList<Player> squarePlayers = getOwner().getPosition().getSquarePlayers();
        squarePlayers.remove(getOwner());
        for (Player currentTarget: squarePlayers){
            damage(currentTarget, 1);
            getDamagedPlayer().add(currentTarget);
        }
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> targets = getOwner().getPosition().getSquarePlayers();
        targets.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(targets), -1);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        ArrayList<Player> squarePlayers = getOwner().getPosition().getSquarePlayers();
        squarePlayers.remove(getOwner());
        for (Player currentTarget: squarePlayers){
            damage(currentTarget, 2);
            getDamagedPlayer().add(currentTarget);
        }
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        return getTargetEffectOne();
    }
}
