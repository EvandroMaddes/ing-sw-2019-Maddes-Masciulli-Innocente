package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import javax.swing.text.Position;
import java.util.ArrayList;

public class Furnace extends AlternateFireWeapon {

    public ArrayList<Player> getTargetsBaseEffect(){
        return null;


    }

    public ArrayList<Player> getTargetsAlternativeEffect(){
        return null;

    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){

    }

    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param alternativeEffectCost
     */

    public Furnace(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost, alternativeEffectCost);
    }

    public void fireAlternativeEffect(ArrayList<Player> targets, Square destination){
    }
}
