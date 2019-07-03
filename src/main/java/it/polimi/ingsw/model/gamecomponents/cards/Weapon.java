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
 * Class that implements the abstracts weapons with only one base effects
 *
 * @author Federico Innocente
 */
public abstract class Weapon extends Card {

    /**
     * Is the cost that a player has to play to reload the weapon
     */
    private AmmoCube[] reloadCost;

    /**
     * Is a flag that determinate if the weapon is loaded or not
     */
    private boolean loaded;

    /**
     * Represents the effect that the weapon has:
     * 0 - base effect, every weapons have it
     * 1 - second effect, only weapons with alternative or optional effect has it
     * 2 - third effect, only weapons with two optional effects has it
     */
    private boolean[] effectsEnable;

    /**
     * Represents which of the three effect the weapon can perform in that moment
     */
    private boolean[] usableEffect;

    /**
     * Represent all the players that the weapon damaged the last shot phase
     */
    private ArrayList<Player> damagedPlayer;

    /**
     * Constructor
     *
     * @param colour     is the colour of the weapon
     * @param name       is the name of the weapon
     * @param reloadCost is the reload cost of the weapon
     */
    public Weapon(CubeColour colour, String name, AmmoCube[] reloadCost) {
        super(colour, name);
        this.reloadCost = reloadCost;
        setEffectsEnable(new boolean[]{true, false, false});
        damagedPlayer = new ArrayList<>();
        setLoaded();
    }

    /**
     * Method to reset the usable effect for the weapon in a determinate moment
     *
     * @param newUsableEffect is the new configuration of the usable effectcs
     */
    public void updateUsableEffect(boolean[] newUsableEffect) {
        usableEffect = newUsableEffect;
    }

    /**
     * Method called to perform an effect. It can preform only an effect that is enabled for that weapon, otherwise throw an exception
     *
     * @param selectedEffect is the selected effect
     * @param targets        are the targets of the effect, that could be character o square alternatively (depends of the effect)
     */
    public void performEffect(int selectedEffect, List<Object> targets) {
        if (selectedEffect == 1)
            performEffectOne(targets);
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
    public ControllerViewEvent getTargetEffect(int selectedEffect) {
        if (selectedEffect == 1)
            return getTargetEffectOne();
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
    public boolean isUsableEffect(int selectedEffect) {
        if (selectedEffect == 1)
            return isUsableEffectOne();
        else
            return false;
    }

    /**
     * Set the usable effects. This method is called in the reloading phase of the weapon to set the effects that the player can use at teh start of the shot action
     */
    protected void setUsableEffect() {
        updateUsableEffect(new boolean[]{true, true, true});
    }

    /**
     * Check if the weapon is usable.
     * To be usable, the weapon must be loaded and at least one of its effects must be correctly usable
     *
     * @return true if the weapon is usable, false otherwise
     */
    public boolean isUsable() {
        return isLoaded() && getUsableEffect()[0] && isUsableEffectOne();
    }

    /**
     * Manage the flow of the effects after using one, setting the once that are usable or less.
     * Some effects are only usable before or after other specific effects.
     *
     * @param effectUsed is the last effect used
     */
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && getUsableEffect()[0])
            updateUsableEffect(new boolean[]{false, false, false});
        else
            throw new EffectIllegalArgumentException();
    }

    /**
     * Perform the basic effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    public abstract void performEffectOne(List<Object> targets);

    /**
     * Calculate all the possible targets of the basic effect (which could be squares or players) and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    public abstract ControllerViewEvent getTargetEffectOne();

    /**
     * Check if the basic effect is usable in the current condition.
     * That means that teh effect must be enabled, the player can pay for it and that it has targets in range.
     *
     * @return true if the effect can be performed, false otherwise
     */
    public boolean isUsableEffectOne() {
        return effectsEnable[0] && usableEffect[0] && !((TargetPlayerRequestEvent) getTargetEffectOne()).getPossibleTargets().isEmpty();
    }

    /**
     * Check if an effect has to be payed or less.
     * That's because some effects can be performed on more steps, but has to payed just once
     *
     * @param effect is the checked effect
     * @return true if the effect has to be payed, false otherwise
     */
    public boolean hasToPay(int effect) {
        return false;
    }

    /**
     * Get the cost of an effect. Only enabled effect can have a cost
     *
     * @param effect is the effect which cost is searched
     * @return the cost of the effect
     */
    public AmmoCube[] getEffectCost(int effect) {
        if (effect == 1)
            return new AmmoCube[]{};
        else
            throw new EffectIllegalArgumentException();
    }

    /**
     * Check that the target list of an effect is not empty
     * If it is, throw an IllegalArgumentException
     *
     * @param targets is the list of the targets passed to an effect
     */
    public void checkEmptyTargets(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("No targets found");
    }

    /**
     * Reset the list of the players damaged by the weapon
     */
    public void resetDamagedplayer(){
        // TODO: 2019-07-03
        damagedPlayer.clear();
    }

    /**
     * Getter method
     *
     * @return the reload cost
     */
    public AmmoCube[] getReloadCost() {
        return reloadCost;
    }

    /**
     * Getter method
     *
     * @return the grab cost, which is the reload cost without the fist ammo cube
     */
    public AmmoCube[] getGrabCost() {
        AmmoCube[] grabCost = new AmmoCube[getReloadCost().length - 1];

        for (int i = 0; i < grabCost.length; i++)
            grabCost[i] = getReloadCost()[i + 1];
        return grabCost;
    }

    /**
     * Getter method
     *
     * @return the load state of the weapon
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Setter method, set the weapon unloaded
     */
    public void setUnloaded() {
        this.loaded = false;
        getOwner().notifyWeaponsChange();
    }

    /**
     * Setter method, set the weapon loaded
     */
    public void setLoaded() {
        this.loaded = true;
        setUsableEffect();
        if (getOwner() != null)
            getOwner().notifyWeaponsChange();
    }

    /**
     * Getter method
     *
     * @return the usable effects
     */
    public boolean[] getUsableEffect() {
        return usableEffect;
    }

    /**
     * Getter method
     *
     * @return the effect enable
     */
    protected boolean[] getEffectsEnable() {
        return effectsEnable;
    }

    /**
     * Setter method
     *
     * @param effectsEnable set the effect enable for the weapon
     */
    void setEffectsEnable(boolean[] effectsEnable) {
        this.effectsEnable = effectsEnable;
    }

    /**
     * Getter method
     *
     * @return the player damaged in the last weapon action
     */
    public List<Player> getDamagedPlayer() {
        return damagedPlayer;
    }
}
