package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

/**
 * done
 */
public class Electroscythe extends AlternateFireWeapon {

    public Electroscythe(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost, alternativeEffectCost);
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        ArrayList<Player> squarePlayers = getOwner().getPosition().getSquarePlayers();
        squarePlayers.remove(getOwner());
        for (Player currentTarget: squarePlayers){
            damage(currentTarget, 1);
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
        }
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        return getTargetEffectOne();
    }
}
