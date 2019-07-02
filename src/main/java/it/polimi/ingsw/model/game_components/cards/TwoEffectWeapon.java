package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.custom_exceptions.EffectIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;

public abstract class TwoEffectWeapon extends Weapon {

    private AmmoCube[] secondEffectCost;
    ArrayList<Player> firstEffectTarget;

    public TwoEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost);
        this.secondEffectCost = secondEffectCost;
        firstEffectTarget = new ArrayList<>();
        setEffectsEnable(new boolean[]{true, true, false});
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        if(firstEffectTarget != null) {
            firstEffectTarget.clear();
        }
    }

    public boolean isUsableEffectTwo(){
        return getEffectsEnable()[1] && getUsableEffect()[1] && getOwner().canAffortCost(getSecondEffectCost()) && !((TargetPlayerRequestEvent) getTargetEffectTwo()).getPossibleTargets().isEmpty();
    }

    public abstract ControllerViewEvent getTargetEffectTwo();

    public abstract void performEffectTwo(List<Object> targets);

    public AmmoCube[] getSecondEffectCost() {
        return secondEffectCost;
    }

    @Override
    public void performEffect(int selectedEffect, List<Object> targets){
        if (selectedEffect == 1)
            performEffectOne(targets);
        else if (selectedEffect == 2)
            performEffectTwo(targets);
        else
            throw new EffectIllegalArgumentException();
    }

    public ControllerViewEvent getTargetEffect(int selectedEffect){
        if (selectedEffect == 1)
            return getTargetEffectOne();
        else if (selectedEffect == 2)
            return getTargetEffectTwo();
        else
            throw new EffectIllegalArgumentException();
    }

    public boolean isUsableEffect (int selectedEffect){
        if (selectedEffect == 1)
            return isUsableEffectOne();
        else if (selectedEffect == 2)
            return isUsableEffectTwo();
        else
            return false;
    }

    public ArrayList<Player> getFirstEffectTarget() {
        return firstEffectTarget;
    }

    @Override
    public boolean hasToPay(int effect) {
        if (effect == 1)
            return false;
        else if (effect == 2)
            return getUsableEffect()[1];
        else
            throw new EffectIllegalArgumentException();
    }

    @Override
    public AmmoCube[] getEffectCost(int effect) {
        if (effect == 1)
            return new AmmoCube[]{};
        else if (effect == 2)
            return secondEffectCost;
        else
            throw new EffectIllegalArgumentException();
    }
}
