package it.polimi.ingsw.model.gamecomponents.cards;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.utils.custom_exceptions.EffectIllegalArgumentException;

import java.util.List;

/**
 * Abstract class for teh weapons with three alternative effects
 *
 * @author Federico Innocente
 */
public abstract class TwoOptionalEffectWeapon extends OneOptionalEffectWeapon {

    /**
     * Is the cost of the third effect
     */
    private AmmoCube[] thirdEffectCost;

    /**
     * Constructor
     *
     * @param colour           is the colour of the weapon
     * @param name             is the name of the weapon
     * @param reloadCost       is the reload cost
     * @param secondEffectCost is the cost of the second effect
     * @param thirdEffectCost  is the cost of the third effect
     */
    public TwoOptionalEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost, AmmoCube[] thirdEffectCost) {
        super(colour, name, reloadCost, secondEffectCost);
        this.thirdEffectCost = thirdEffectCost;
        setEffectsEnable(new boolean[]{true, true, true});
    }

    /**
     * Manage the flow of the effects after using one, setting the once that are usable or less.
     * Some effects are only usable before or after other specific effects.
     *
     * @param effectUsed is the last effect used
     */
    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (getUsableEffect()[effectUsed])
            getUsableEffect()[effectUsed] = false;
        else
            throw new EffectIllegalArgumentException();
    }

    /**
     * Check if the third effect is usable in the current condition.
     * That means that teh effect must be enabled, the player can pay for it and that it has targets in range.
     *
     * @return true if the effect can be performed, false otherwise
     */
    public boolean isUsableEffectThree(){
        return getEffectsEnable()[2] && getUsableEffect()[2] && getOwner().canAffortCost(getThirdEffectCost()) && !((TargetPlayerRequestEvent) getTargetEffectThree()).getPossibleTargets().isEmpty();
    }

    /**
     * Calculate all the possible targets of the third effect (which could be squares or players) and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    public abstract ControllerViewEvent getTargetEffectThree();

    /**
     * Perform the third effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    public abstract void performEffectThree(List<Object> targets);

    /**
     * Method called to perform an effect. It can preform only an effect that is enabled for that weapon, otherwise throw an exception
     *
     * @param selectedEffect is the selected effect
     * @param targets        are the targets of the effect, that could be character o square alternatively (depends of the effect)
     */
    @Override
    public void performEffect(int selectedEffect, List<Object> targets){
        if (selectedEffect == 1)
            performEffectOne(targets);
        else if (selectedEffect == 2)
            performEffectTwo(targets);
        else if (selectedEffect == 3)
            performEffectThree(targets);
        else
            throw new EffectIllegalArgumentException();
    }

    /**
     * Get all the possible target of the effect, which could be both squares or players, and return a message ready to be send to the clients for the choice.
     * Only effects enabled for the weapons can be requested.
     *
     * @param selectedEffect is the effect which targets are requested
     * @return a message with the information about all possible targets of the effetc
     */
    @Override
    public ControllerViewEvent getTargetEffect(int selectedEffect){
        if (selectedEffect == 1)
            return getTargetEffectOne();
        else if (selectedEffect == 2)
            return getTargetEffectTwo();
        else if (selectedEffect == 3)
            return getTargetEffectThree();
        else
            throw new EffectIllegalArgumentException();
    }

    /**
     * Check if an effect can be correctly used.
     * That means that teh effect must be enabled, the player can pay for it and that it has targets in range.
     * Only enabled effects can be checked
     *
     * @param selectedEffect is the selected effect
     * @return true if the effect is usable, false otherwise
     */
    @Override
    public boolean isUsableEffect (int selectedEffect){
        if (selectedEffect == 1)
            return isUsableEffectOne();
        else if (selectedEffect == 2)
            return isUsableEffectTwo();
        else if (selectedEffect == 3)
            return  isUsableEffectThree();
        else
            throw new EffectIllegalArgumentException();
    }

    /**
     * Get the cost of an effect. Only enabled effect can have a cost
     *
     * @param effect is the effect which cost is searched
     * @return the cost of the effect
     */
    @Override
    public AmmoCube[] getEffectCost(int effect) {
        if (effect == 1)
            return new AmmoCube[]{};
        else if (effect == 2)
            return getSecondEffectCost();
        else if (effect == 3)
            return thirdEffectCost;
        else
            throw new EffectIllegalArgumentException();
    }

    /**
     * Check if an effect has to be payed or less.
     * That's because some effects can be performed on more steps, but has to payed ust once
     *
     * @param effect is the checked effect
     * @return true if the effect has to be payed, false otherwise
     */
    @Override
    public boolean hasToPay(int effect) {
        if (effect == 1)
            return false;
        else if (effect == 2)
            return getUsableEffect()[1];
        else if (effect == 3)
            return getUsableEffect()[2];
        else
            throw new EffectIllegalArgumentException();
    }

    /**
     * Getter method
     *
     * @return the third effect cost
     */
    private AmmoCube[] getThirdEffectCost() {
        return thirdEffectCost;
    }
}
