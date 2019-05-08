package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class Heatseeker extends Weapon {

    public ArrayList<Player> getTargetsBaseEffect(){

    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if(targets.size() < 1)
            throw new NullPointerException();
        damage(targets.get(0), 3);
    }
}
