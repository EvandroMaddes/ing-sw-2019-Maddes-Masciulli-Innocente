package it.polimi.ingsw.model.gamecomponents.cards;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.custom_exceptions.EffectIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;

/**
 * The abstract class for the weapon with two effetcs, that could be both alternative or optional
 *
 * @author Federico Innocente
 */
public abstract class TwoEffectWeapon extends Weapon {
    /**
     * Is the cost of the second effect of the weapon
     */
    private AmmoCube[] secondEffectCost;

    /**
     * Is a list of the player already damaged by the weapon in that round, used to apply different effects on same or differents targets
     */
    private ArrayList<Player> firstEffectTarget;

    /**
     * Constructor
     *
     * @param colour           is the colour of the weapon
     * @param name             is the name of the weapon
     * @param reloadCost       is the reload cost
     * @param secondEffectCost is the cost of the second effect
     */
    TwoEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] secondEffectCost) {
        super(colour, name, reloadCost);
        this.secondEffectCost = secondEffectCost;
        firstEffectTarget = new ArrayList<>();
        setEffectsEnable(new boolean[]{true, true, false});
    }

    /**
     * Set the weapon loaded.
     * The lost of player damaged by the weapon is cleared
     */
    @Override
    public void setLoaded() {
        super.setLoaded();
        if (firstEffectTarget != null) {
            firstEffectTarget.clear();
        }
    }

    /**
     * Check if the second effect is usable in the current condition.
     * That means that teh effect must be enabled, the player can pay for it and that it has targets in range.
     *
     * @return true if the effect can be performed, false otherwise
     */
    public boolean isUsableEffectTwo() {
        return getEffectsEnable()[1] && getUsableEffect()[1] && getOwner().canAffortCost(getSecondEffectCost()) && !((TargetPlayerRequestEvent) getTargetEffectTwo()).getPossibleTargets().isEmpty();
    }

    /**
     * Calculate all the possible targets of the second effect (which could be squares or players) and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    public abstract ControllerViewEvent getTargetEffectTwo();

    /**
     * Perform the second effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    public abstract void performEffectTwo(List<Object> targets);

    /**
     * Method called to perform an effect. It can preform only an effect that is enabled for that weapon, otherwise throw an exception
     *
     * @param selectedEffect is the selected effect
     * @param targets        are the targets of the effect, that could be character o square alternatively (depends of the effect)
     */
    @Override
    public void performEffect(int selectedEffect, List<Object> targets) {
        if (selectedEffect == 1)
            performEffectOne(targets);
        else if (selectedEffect == 2)
            performEffectTwo(targets);
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
    public ControllerViewEvent getTargetEffect(int selectedEffect) {
        if (selectedEffect == 1)
            return getTargetEffectOne();
        else if (selectedEffect == 2)
            return getTargetEffectTwo();
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
    public boolean isUsableEffect(int selectedEffect) {
        if (selectedEffect == 1)
            return isUsableEffectOne();
        else if (selectedEffect == 2)
            return isUsableEffectTwo();
        else
            return false;
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
            return secondEffectCost;
        else
            throw new EffectIllegalArgumentException();
    }

    /**
     * Getter method
     *
     * @return the second effect cost
     */
    protected AmmoCube[] getSecondEffectCost() {
        return secondEffectCost;
    }

    /**
     * Getter method
     *
     * @return return the list of the players damaged in that shot action of the weapon
     */
    public List<Player> getFirstEffectTarget() {
        return firstEffectTarget;
    }
}
