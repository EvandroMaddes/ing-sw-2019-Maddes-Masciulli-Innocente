package it.polimi.ingsw.model.gamecomponents.cards.weapons;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.TwoOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * done
 */
public class MachineGun extends TwoOptionalEffectWeapon {
    private boolean extraDamageThirdEffect;
    private boolean thirdDamageDealed;
    private ArrayList<Player> alreadyReDamagedTarget;

    public MachineGun() {
        super(CubeColour.Blue, "MACHINE GUN",
                new AmmoCube[]{new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Red)},
                new AmmoCube[]{new AmmoCube(CubeColour.Yellow)},
                new AmmoCube[]{new AmmoCube(CubeColour.Blue)});
        alreadyReDamagedTarget = new ArrayList<>();
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        extraDamageThirdEffect = false;
        thirdDamageDealed = false;
        if(alreadyReDamagedTarget != null) {
            alreadyReDamagedTarget.clear();
        }
    }

    @Override
    protected void setUsableEffect() {
        updateUsableEffect(new boolean[]{true, false, false});
    }

    @Override
    public void effectControlFlow(int effectUsed) {
        effectUsed--;
        if (effectUsed == 0 && Arrays.equals(getUsableEffect(), new boolean[]{true, false, false}))
            updateUsableEffect(new boolean[]{false, true, true});
        else if (effectUsed == 1 && getUsableEffect()[1])
            getUsableEffect()[effectUsed] = false;
        else if (effectUsed == 2 && (thirdDamageDealed && ( extraDamageThirdEffect || getFirstEffectTarget().isEmpty() ) ||
                extraDamageThirdEffect && targettablePlayer().isEmpty()) )
            getUsableEffect()[effectUsed] = false;

    }

    @Override
    public void performEffectOne(List<Object> targets) {
        checkEmptyTargets(targets);
        for (int i = 0; i < targets.size() && i < 2; i++){
            damage((Player)targets.get(i), 1);
            getFirstEffectTarget().add((Player)targets.get(i));
            getDamagedPlayer().add((Player)targets.get(i));
        }
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        ArrayList<Player> possibleTargets = getOwner().getPosition().findVisiblePlayers();
        possibleTargets.remove(getOwner());
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(possibleTargets), 2);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        if (getFirstEffectTarget().size() == 1){
            damage(getFirstEffectTarget().get(0), 1);
            alreadyReDamagedTarget.add(getFirstEffectTarget().get(0));
            getFirstEffectTarget().remove(getFirstEffectTarget().get(0));
        }
        else {
            checkEmptyTargets(targets);
            damage((Player) targets.get(0), 1);
            Player target = (Player) targets.get(0);
            alreadyReDamagedTarget.add(target);
            getFirstEffectTarget().remove(target);
        }
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        if (getFirstEffectTarget().size() == 1)
            return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getFirstEffectTarget()), -1);
        else
            return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getFirstEffectTarget()), 1);
    }

    @Override
    public void performEffectThree(List<Object> targets) {
        checkEmptyTargets(targets);
        Player target = (Player)targets.get(0);
        if ( !extraDamageThirdEffect && getFirstEffectTarget().contains(target)) {
            damage(getFirstEffectTarget().get(0), 1);
            alreadyReDamagedTarget.add(target);
            getDamagedPlayer().add(target);
            getFirstEffectTarget().remove(target);
            extraDamageThirdEffect = true;
        }
        else{
            if (!extraDamageThirdEffect && getFirstEffectTarget().size() == 1){
                damage(getFirstEffectTarget().get(0), 1);
                alreadyReDamagedTarget.add(getFirstEffectTarget().get(0));
                getFirstEffectTarget().clear();
                extraDamageThirdEffect = true;
            }
            damage(target, 1);
            thirdDamageDealed = true;
        }
        effectControlFlow(3);
    }

    @Override
    public ControllerViewEvent getTargetEffectThree() {
        ArrayList<Player> visibleTargets;
        visibleTargets = getOwner().getPosition().findVisiblePlayers();
        visibleTargets.remove(getOwner());
        visibleTargets.removeAll(alreadyReDamagedTarget);
        if(extraDamageThirdEffect)
            visibleTargets.removeAll(getFirstEffectTarget());
        if(thirdDamageDealed){
            for (Player p: visibleTargets){
                if (!getFirstEffectTarget().contains(p) && !alreadyReDamagedTarget.contains(p))
                    visibleTargets.remove(p);
            }
        }
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(visibleTargets), 1);
    }

    private ArrayList<Player> targettablePlayer(){
        ArrayList<Player> targettablePlayer = getOwner().getPosition().findVisiblePlayers();
        targettablePlayer.remove(getOwner());
        targettablePlayer.removeAll(getFirstEffectTarget());
        targettablePlayer.removeAll(alreadyReDamagedTarget);
        return targettablePlayer;
    }

}

