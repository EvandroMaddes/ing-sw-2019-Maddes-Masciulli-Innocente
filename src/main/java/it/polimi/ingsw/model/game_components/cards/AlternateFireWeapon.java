package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;


public abstract class AlternateFireWeapon extends Weapon {

    private AmmoCube[] alternativeEffectCost;

    /**
     * Constructor
     * @param alternativeEffectCost
     */

    public AlternateFireWeapon(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost);
        this.alternativeEffectCost = alternativeEffectCost;
    }

    /**
     *
     * @param targets
     * @param destination
     * @param selectedEffect
     */
    public void fire(ArrayList<Player> targets, Square destination, int selectedEffect) {
        switch (selectedEffect){
            case 1:{
                fireBaseEffect(targets, destination);
                break;
            }
            case 2:{
             fireAlternativeEffect(targets,destination);
                break;
            }
        }
    }

    /**
     *
     * @param selectedEffect
     * @return
     */
    public ArrayList<Player> getTargets(int selectedEffect){
        // switch
        ArrayList<Player> targets = new ArrayList<>();
        switch (selectedEffect){
            case 1:{
                getTargetsBaseEffect();
                break;
            }
            case 2:{
               getTargetsAlternativeEffect();
                break;
            }
        }

        return targets;
    }

    /**
     *
     * @return
     */
    public abstract ArrayList<Player> getTargetsAlternativeEffect();

    /**
     *
     * @param targets
     * @param destination
     */
    public abstract void fireAlternativeEffect(ArrayList<Player> targets, Square destination);

}
