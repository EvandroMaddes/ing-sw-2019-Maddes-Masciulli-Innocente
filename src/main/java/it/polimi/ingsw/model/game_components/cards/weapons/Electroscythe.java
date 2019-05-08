package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class Electroscythe extends AlternateFireWeapon {

    public ArrayList<Player> getTargetsBaseEffect(){
        return getOwner().getPosition().getSquarePlayers();
    }

    public ArrayList<Player> getTargetsAlternativeEffect(){
        return getOwner().getPosition().getSquarePlayers();
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
