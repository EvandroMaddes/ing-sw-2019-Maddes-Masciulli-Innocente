package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

import java.util.List;

public abstract class TwoEffectWeapon extends Weapon {

    private AmmoCube[] secondEffectCost;

    public TwoEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost);
        this.secondEffectCost = secondEffectCost;
        setEffectsEnable(new boolean[]{true, true, false});
    }

    public boolean isUsableEffectTwo(){
        return getEffectsEnable()[1] && getUsableEffect()[1] && getOwner().canAffortCost(getSecondEffectCost()) && ((TargetPlayerRequestEvent) getTargetEffectTwo()).getPossibleTargets().isEmpty();
    }

    public abstract ControllerViewEvent getTargetEffectTwo();

    public abstract void performEffectTwo(List<Object> targets);

    public AmmoCube[] getSecondEffectCost() {
        return secondEffectCost;
    }
}
