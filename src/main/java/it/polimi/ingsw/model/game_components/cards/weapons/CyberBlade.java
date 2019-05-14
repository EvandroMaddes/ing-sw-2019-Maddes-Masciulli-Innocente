package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.TwoOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class  Cyberblade extends TwoOptionalEffectWeapon {
    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param firstOptionalEffectCost
     * @param secondOptionalEffectCost
     */
    public Cyberblade(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost, AmmoCube[] secondOptionalEffectCost) {
        super(colour, name, reloadCost, firstOptionalEffectCost, secondOptionalEffectCost);
    }

    public ArrayList<Player> getTargetsBaseEffect() {
        ArrayList<Player> target;
        ArrayList<Player> giocatoriNellaPartita = null;//Todo NB metodo getSquarePlayer deve ricevere i players in game

        target = getOwner().getPosition().getSquarePlayers();
        return target;

    }

    public ArrayList<Player> getTargetsFirstOptionalEffect() {

        throw  new IllegalStateException();//this exception is managed by controller: target is a square with distance one
    }

    public ArrayList<Player> getTargetsSecondOptionalEffect() {
        return getTargetsBaseEffect();

    }



    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() != 0) {
            damage(targets.get(0), 2);
        }
        else
            throw new NullPointerException("nobody to damage");
    }

    public void fireFirstOptionalEffect(ArrayList<Player> targets, Square destination){
        getOwner().setPosition(destination);
    }

    public void fireSecondOptionalEffect(ArrayList<Player> targets, Square destination){
       fireBaseEffect(targets,destination);
    }

}
