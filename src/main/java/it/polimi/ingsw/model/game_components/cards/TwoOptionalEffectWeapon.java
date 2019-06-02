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

    @Override
    public void performEffect(int selectedEffect, List<Object> targets){
        if (selectedEffect == 1)
            performEffectOne(targets);
        else if (selectedEffect == 2)
            performEffectTwo(targets);
        else if (selectedEffect == 3)
            performEffectThree(targets);
        else
            throw new IllegalAccessError("The effect doesn't exist");
    }

    public ControllerViewEvent getTargetEffect(int selectedEffect){
        if (selectedEffect == 1)
            return getTargetEffectOne();
        else if (selectedEffect == 2)
            return getTargetEffectTwo();
        else if (selectedEffect == 3)
            return getTargetEffectThree();
        else
            throw new IllegalAccessError("The effect doesn't exist");
    }

    public boolean isUsableEffect (int selectedEffect){
        if (selectedEffect == 1)
            return isUsableEffectOne();
        else if (selectedEffect == 2)
            return isUsableEffectTwo();
        else if (selectedEffect == 3)
            return  isUsableEffectThree();
        else
            throw new IllegalAccessError("The effect doesn't exist");
    }

    @Override
    public AmmoCube[] getEffectCost(int effect) {
        if (effect == 1)
            return new AmmoCube[]{};
        else if (effect == 2)
            return getSecondEffectCost();
        else if (effect == 3)
            return thirdEffectCost;
        else
            throw new UnsupportedOperationException("Effetto richiseto non esistente");
    }

    @Override
    public boolean hasToPay(int effect) {
        if (effect == 1)
            return false;
        else if (effect == 2)
            return getUsableEffect()[1];
        else if (effect == 3)
            return getUsableEffect()[2];
        else
            throw new UnsupportedOperationException("efeftto richiesto non esistente");
    }
}
