package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class TractorBeam extends AlternateFireWeapon {

    public TractorBeam(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost, secondEffectCost);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("Empty target list");
        Player target = (Player)targets.get(0);
        move(target, getOwner().getPosition());
        damage(target, 3);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        return null;
    }

    @Override
    public void performEffectOne(List<Object> targets) {

    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        return null;
    }
}
