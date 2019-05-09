package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.TwoOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class Thor extends TwoOptionalEffectWeapon {

    public ArrayList<Player> getTargetsBaseEffect(){
        return getOwner().getPosition().findVisiblePlayers();
    }

    public ArrayList<Player> getTargetsFirstOptionalEffect(){
        ArrayList<Player> targets = getTargetsBaseEffect();
        ArrayList<Player> secondStepTargets;
        for (Player p1: targets) {
            secondStepTargets = p1.getPosition().findVisiblePlayers();
            for (Player p2: secondStepTargets) {
                if (p2 != getOwner() && !targets.contains(p2)){
                    targets.add(p2);
                }
            }
        }
        return targets;
    }

    public ArrayList<Player> getTargetsSecondOptionalEffect(){
        ArrayList<Player> targets = getTargetsFirstOptionalEffect();
        ArrayList<Player> thirdSteptargets;
        for (Player p2: targets){
            thirdSteptargets = p2.getPosition().findVisiblePlayers();
            for (Player p3:thirdSteptargets) {
                if (p3 != getOwner() && !targets.contains(p3)){
                    targets.add(p3);
                }

            }

        }
        return targets;
    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination) {
        if(targets.size() < 1)
            throw new NullPointerException();
        damage(targets.get(0), 2);
    }

    public void fireFirstOptionalEffect(ArrayList<Player> targets, Square destination) {
        if (targets.size() < 2)
            throw new NullPointerException();
        damage(targets.get(1), 1);
        fireBaseEffect(targets, destination);
    }

    public void fireSecondOptionalEffect(ArrayList<Player> targets, Square destination) {
        if(targets.size() < 3)
            throw new NullPointerException();
        damage(targets.get(2), 2);
        fireFirstOptionalEffect(targets, destination);
    }
}
