package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class Heatseeker extends Weapon {
    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     */
    public Heatseeker(CubeColour colour, String name, AmmoCube[] reloadCost) {
        super(colour, name,new boolean[1], reloadCost);
    }

    @Override
    public void fire(ArrayList<Player> targets, Square destination, int selectedEffect) {
        fireBaseEffect(targets, destination);
    }

    @Override
    public ArrayList<Player> getTargets(int selectedEffect) {
        return getTargetsBaseEffect();
    }

    public ArrayList<Player> getTargetsBaseEffect(){

            return null;
    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if(targets.size() < 1)
            throw new NullPointerException();
        damage(targets.get(0), 3);
    }
}
