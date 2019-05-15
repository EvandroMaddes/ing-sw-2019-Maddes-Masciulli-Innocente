package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;


public abstract class OneOptionalEffectWeapon extends Weapon {

    private AmmoCube[] firstOptionalEffectCost;


    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param firstOptionalEffectCost cost of the firsts (and maybe only) optional effect
     */
    public OneOptionalEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost)
    {
        super(colour, name, reloadCost);
        this.firstOptionalEffectCost = firstOptionalEffectCost;
    }



    public void fire(ArrayList<Player> targets, Square destination, int selectedEffect) {
        switch (selectedEffect){
            case 1:{
                fireBaseEffect(targets, destination);
                break;
            }
            case 2:{
                fireFirstOptionalEffect(targets, destination);
                break;
            }
        }
    }
    public ArrayList<Player> getTargets(int selectedEffect){
        // switch
        ArrayList<Player> targets = new ArrayList<>();
        switch (selectedEffect){
            case 1:{
               targets = getTargetsBaseEffect();
                break;
            }
            case 2:{
               targets = getTargetsFirstOptionalEffect();
                break;
            }
        }

        return targets;
    }

    public abstract ArrayList<Player> getTargetsFirstOptionalEffect();
    public abstract void fireFirstOptionalEffect(ArrayList<Player> targets, Square destination);


}
