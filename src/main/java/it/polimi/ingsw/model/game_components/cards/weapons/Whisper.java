package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;


import java.util.ArrayList;

public class Whisper extends Weapon {

    public ArrayList<Player> getTargetBaseEffect(){
        ArrayList<Player> possibleTargets = getOwner().getPosition().findVisiblePlayers();
        for (Player p: possibleTargets) {
            if ( Math.abs(p.getPosition().getRow() - getOwner().getPosition().getRow()) + Math.abs(p.getPosition().getColumn() - getOwner().getPosition().getColumn() ) < 2){
                possibleTargets.remove(p);
            }
        }
        return possibleTargets;
    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() < 1)
            throw new NullPointerException();
    }
}
