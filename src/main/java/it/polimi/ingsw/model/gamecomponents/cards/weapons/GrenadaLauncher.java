package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.OneOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the weapon Grenade Launcher
 *
 * @author Federico Inncente
 */
public class GrenadaLauncher extends OneOptionalEffectWeapon {
    /**
     * Flag to determinate if the first step of the first effect has been performed
     */
    private boolean intermediateEffectState;

    /**
     * Constructor
     */
    public GrenadaLauncher() {
        super(CubeColour.Red, "GRENADE LAUNCHER",
                new AmmoCube[]{new AmmoCube(CubeColour.Red)},
                new AmmoCube[]{new AmmoCube(CubeColour.Red)});
    }

    /**
     * Set the weapon loaded. The intermediateEffectState flag is turned back to false.
     */
    @Override
    public void setLoaded() {
        super.setLoaded();
        intermediateEffectState = false;
    }

    /**
     * Set the usable effects. This method is called in the reloading phase of the weapon to set the effects that the player can use at teh start of the shot action
     */
    @Override
    protected void setUsableEffect() {
        updateUsableEffect(new boolean[]{true, true, false});
    }

    /**
     * Manage the flow of the effects after using one, setting the once that are usable or less.
     *
     * @param effectUsed is the last effect used
     */
    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && !intermediateEffectState)
            intermediateEffectState = true;
        else if (effectUsed == 1 && intermediateEffectState)
            updateUsableEffect(new boolean[]{false, false, false});
        else if (effectUsed == 0 || effectUsed == 1)
            getUsableEffect()[effectUsed] = false;
    }

    /**
     * Perform the basic effect of the weapon. This effect is split in two phase, chosen according to teh flag intermediateEffectState
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectOne(List<Object> targets) {
        if (!intermediateEffectState){
            performEffectOneFirstStep(targets);
        }
        else
            performEffectOneSecondStep(targets);
        effectControlFlow(1);
    }

    /**
     * Perform the first step of the base effect
     *
     * @param targets is a list of targets chosen by the player
     */
    private void performEffectOneFirstStep(List<Object> targets){
        checkEmptyTargets(targets);
        damage((Player)targets.get(0), 1);
        getDamagedPlayer().add((Player)targets.get(0));
        getFirstEffectTarget().add((Player)targets.get(0));
    }

    /**
     * Perform the second step of the base effect
     *
     * @param targets is a list of targets chosen by the player
     */
    private void performEffectOneSecondStep(List<Object> targets){
        checkEmptyTargets(targets);
        move(getFirstEffectTarget().get(0), (Square)targets.get(0));
    }

    /**
     * Calculate all the possible player or square targets of the basic effect and encode them into a message ready to be send to the player.
     * This methods call two sub-methods according with the flag intermediateEffectState
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectOne() {
        if (!intermediateEffectState)
            return getTargetEffectOneFirstStep();
        else
            return getTargetEffectOneSecondStep();
    }

    /**
     * Calculate all the possible player targets of the basic effect (first step) and encode them into a message ready to be send to the player.
     *
     * @return a message with all teh information about teh possible targets
     */
    private ControllerViewEvent getTargetEffectOneFirstStep(){
        ArrayList<Player> possibleTargets = getOwner().getPosition().findVisiblePlayers();
        possibleTargets.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 1);
    }

    /**
     * Calculate all the possible square targets of the basic effect (second step) and encode them into a message ready to be send to the player.
     *
     * @return a message with all teh information about teh possible targets
     */
    private ControllerViewEvent getTargetEffectOneSecondStep(){
        ArrayList<Square> possibleDestination = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            if (getFirstEffectTarget().get(0).getPosition().checkDirection(i))
                possibleDestination.add(getFirstEffectTarget().get(0).getPosition().getNextSquare(i));
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination));
    }

    /**
     * Check if the fist effect is usable, according with the phase (intermediateEffectState) and the map situation
     *
     * @return true if the effect is usable
     */
    @Override
    public boolean isUsableEffectOne() {
        if (!intermediateEffectState)
            return getUsableEffect()[0] && getEffectsEnable()[0] && !((TargetPlayerRequestEvent)getTargetEffectOne()).getPossibleTargets().isEmpty();
        else
            return getUsableEffect()[0];
    }

    /**
     * Perform the second effect of the weapon
     *
     * @param targets are the targets chosen by the player
     */
    @Override
    public void performEffectTwo(List<Object> targets) {
        checkEmptyTargets(targets);
        Square targetSquare = (Square)targets.get(0);
        for (Player p:targetSquare.getSquarePlayers()){
            if (p != getOwner()) {
                damage(p, 1);
                getDamagedPlayer().add(p);
            }
        }
        effectControlFlow(2);
    }

    /**
     * Calculate all the possible square targets of the second effect and encode them into a message ready to be send to the player .
     *
     * @return a message with all teh information about teh possible targets
     */
    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        ArrayList<Square> possibleTargets = new ArrayList<>();
        for (Player p:getOwner().getPosition().findVisiblePlayers()){
            if (!possibleTargets.contains(p.getPosition()))
                possibleTargets.add(p.getPosition());
        }
        return new TargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleTargets), Encoder.encodeSquareTargetsY(possibleTargets));
    }

    /**
     * Check if the second effect is usable in the current condition.
     * That means that teh effect must be enabled, the player can pay for it and that it has targets in range.
     *
     * @return true if the effect can be performed, false otherwise
     */
    @Override
    public boolean isUsableEffectTwo() {
        return getUsableEffect()[1] && getOwner().canAffortCost(getSecondEffectCost()) && ((TargetSquareRequestEvent)getTargetEffectTwo()).getPossibleTargetsY().length != 0;
    }
}
