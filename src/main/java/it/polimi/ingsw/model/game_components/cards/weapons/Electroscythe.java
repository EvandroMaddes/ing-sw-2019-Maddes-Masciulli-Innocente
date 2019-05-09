package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class Electroscythe extends AlternateFireWeapon {
    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param alternativeEffectCost
     */

    public Electroscythe(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost, alternativeEffectCost);
    }

    public ArrayList<Player> getTargetsBaseEffect(){
        ArrayList<Player> giocatoriInPartita= null;
        return getOwner().getPosition().getSquarePlayers(giocatoriInPartita);
    }

    public ArrayList<Player> getTargetsAlternativeEffect(){
        ArrayList<Player> giocatoriInPartita= null;
        return getOwner().getPosition().getSquarePlayers(giocatoriInPartita);
    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        targets = getTargetsBaseEffect();
        for (Player p: targets) {
            damage(p, 1);
        }
    }

    public void fireAlternativeEffect(ArrayList<Player> targets, Square destination){
        targets = getTargetsAlternativeEffect();
        for (Player p: targets) {
            damage(p, 2);
        }
    }
}
