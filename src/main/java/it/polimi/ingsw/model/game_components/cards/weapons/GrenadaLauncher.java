package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.OneOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class GrenadaLauncher extends OneOptionalEffectWeapon {
    /**
     * @param colour
     * @param name
     * @param reloadCost
     * @param firstOptionalEffectCost cost of the firsts (and maybe only) optional effect
     */
    public GrenadaLauncher(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost) {
        super(colour, name, reloadCost, firstOptionalEffectCost);
    }


    public ArrayList<Player> getTargetBaseEffect() {
        ArrayList<Player> target;
        target = new ArrayList<Player>();
        target = getOwner().getPosition().findVisiblePlayers();
        return target;
    }

    public ArrayList<Player> getTargetFirstOptionalEffect() {
        ArrayList<Player> target;
        target= getTargetBaseEffect();
        return target;
    }


    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() != 0) {
            damage(targets.get(0), 1);
            targets.get(0).setPosition(destination);
        }
        else
            throw new NullPointerException();
    }

    public void fireFirstOptionalEffect(ArrayList<Player> targets, Square destination){
        Iterator iterator;
        iterator = targets.iterator();
        if (targets.size() != 0) {
            while (iterator.hasNext()){

                damage((Player)iterator.next(), 1);
            }
        }
        else
            throw new NullPointerException();
    }

}
