package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.TargetPlayerRequestEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.TwoOptionalEffectWeapon;
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

    public MachineGun(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost, AmmoCube[] secondOptionalEffectCost) {
        super(colour, name, reloadCost, firstOptionalEffectCost, secondOptionalEffectCost);
        alreadyReDamagedTarget = new ArrayList<>();
    }

    @Override
    public void setLoaded() {
        super.setLoaded();
        extraDamageThirdEffect = false;
        thirdDamageDealed = false;
        alreadyReDamagedTarget.clear();
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
        else if (effectUsed == 2 && getUsableEffect()[2] && (thirdDamageDealed && extraDamageThirdEffect) )
            getUsableEffect()[effectUsed] = false;
        else
            throw new IllegalArgumentException("ControlFlowError");
    }

    @Override
    public void performEffectOne(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("Targets empty list");
        for (int i = 0; i < targets.size() && i < 2; i++){
            damage((Player)targets.get(i), 1);
            getFirstEffectTarget().add((Player)targets.get(i));
        }
        effectControlFlow(1);
    }

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getOwner().getPosition().findVisiblePlayers()), 2);
    }

    @Override
    public void performEffectTwo(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("Empty targets");
        damage((Player)targets.get(0), 1);
        Player target = (Player)targets.get(0);
        alreadyReDamagedTarget.add(target);
        getFirstEffectTarget().remove(target);
        effectControlFlow(2);
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        return new TargetPlayerRequestEvent(getOwner().getUsername(), Encoder.encodePlayerTargets(getFirstEffectTarget()), 1);
    }

    @Override
    public void performEffectThree(List<Object> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("Empty targets");
        Player target = (Player)targets.get(0);
        if ( !extraDamageThirdEffect && getFirstEffectTarget().contains(target)) {
            damage(getFirstEffectTarget().get(0), 1);
            alreadyReDamagedTarget.add(target);
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

}

