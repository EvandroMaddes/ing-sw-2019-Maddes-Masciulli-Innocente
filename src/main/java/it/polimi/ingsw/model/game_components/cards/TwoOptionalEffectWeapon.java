package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;


public abstract class TwoOptionalEffectWeapon extends OneOptionalEffectWeapon {

    private AmmoCube[] secondOptionalEffectCost;


    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param firstOptionalEffectCost
     * @param secondOptionalEffectCost is the cost of the secon optional effect
     */
    public TwoOptionalEffectWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost, AmmoCube[] secondOptionalEffectCost)
    {
        super(colour, name, reloadCost, firstOptionalEffectCost);
        this.secondOptionalEffectCost = secondOptionalEffectCost;
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
            case 3:{
                fireSecondOptionalEffect(targets,destination);
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
            case 3:{
               targets = getTargetsSecondOptionalEffect();
                break;
            }
        }

        return targets;
    }

    public abstract ArrayList<Player> getTargetsSecondOptionalEffect();
    public abstract void fireSecondOptionalEffect(ArrayList<Player> targets, Square destination);
}
