package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

import java.util.List;


public abstract class TwoOptionalEffectWeapon extends OneOptionalEffectWeapon {

    private AmmoCube[] thirdEffectCost;

    public TwoOptionalEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost, AmmoCube[] thirdEffectCost) {
        super(colour, name, reloadCost, secondEffectCost);
        this.thirdEffectCost = thirdEffectCost;
        setEffectsEnable(new boolean[]{true, true, true});
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (getUsableEffect()[effectUsed])
            getUsableEffect()[effectUsed] = false;
        else
            throw new IllegalArgumentException();
    }

    public boolean isUsableEffectThree(){
        return getEffectsEnable()[2] && getUsableEffect()[2] && getOwner().canAffortCost(getThirdEffectCost()) && ((TargetPlayerRequestEvent) getTargetEffectThree()).getPossibleTargets().isEmpty();
    }

    public abstract ControllerViewEvent getTargetEffectThree();

    public abstract void performEffectThree(List<Object> targets);

    public AmmoCube[] getThirdEffectCost() {
        return thirdEffectCost;
    }

}
