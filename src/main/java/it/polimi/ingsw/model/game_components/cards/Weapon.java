package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

import java.util.List;

public abstract class Weapon extends Card {

    private AmmoCube[] reloadCost;
    private boolean loaded;
    private boolean[] effectsEnable;
    private boolean[] usableEffect;


    public Weapon(CubeColour colour, String name, AmmoCube[] reloadCost) {
        super(colour, name);
        this.reloadCost = reloadCost;
        setEffectsEnable(new boolean[]{true, false, false});
        setLoaded();
    }

    /**
     *
     * @return reload cost
     */
    public AmmoCube[] getReloadCost()
    {
        return reloadCost;
    }

    public AmmoCube[] getGrabCost(){
        AmmoCube[] grabCost = new AmmoCube[getReloadCost().length - 1];

        for (int i = 0; i < grabCost.length; i++)
            grabCost[i] = getReloadCost()[i+1];
        return grabCost;
    }

    public boolean isLoaded()
    {
        return loaded;
    }

    public void setUnloaded(){
        this.loaded = false;
    }

    /**
     * Set the weapon as loaded. The method can be overrided to set the usable effects
     */
    public void setLoaded(){
        this.loaded = true;
        setUsableEffect();
    }

    public void updateUsableEffect(boolean[] newUsableEffect){
        usableEffect = newUsableEffect;
    }

    public boolean[] getUsableEffect() {
        return usableEffect;
    }

    public boolean[] getEffectsEnable() {
        return effectsEnable;
    }

    public void setEffectsEnable(boolean[] effectsEnable) {
        this.effectsEnable = effectsEnable;
    }

    public void performEffect(int selectedEffect, List<Object> targets){
        if (selectedEffect == 1)
            performEffectOne(targets);
        else
            throw new IllegalAccessError("The effect doesn't exist");
    }

    public ControllerViewEvent getTargetEffect(int selectedEffect){
        if (selectedEffect == 1)
            return getTargetEffectOne();
        else
            throw new IllegalAccessError("The effect doesn't exist");
    }

    public boolean isUsableEffect (int selectedEffect){
        if (selectedEffect == 1)
            return isUsableEffectOne();
        else
            return false;
    }







    protected void setUsableEffect(){
        updateUsableEffect(new boolean[]{true, true, true});
    }

    public boolean isUsable(){
        return isLoaded() && getUsableEffect()[0] && isUsableEffectOne();
    }

    public void effectControlFlow(int effectUsed){
        effectUsed--;
        if (effectUsed == 0 && getUsableEffect()[0] )
            updateUsableEffect(new boolean[]{false, false, false});
        else
            throw new IllegalArgumentException("Effect control flow error");
    }

    public abstract void performEffectOne(List<Object> targets);

    public abstract ControllerViewEvent getTargetEffectOne();

    public boolean isUsableEffectOne(){
        return effectsEnable[0] && usableEffect[0] && !((TargetPlayerRequestEvent)getTargetEffectOne()).getPossibleTargets().isEmpty();
    }

}
