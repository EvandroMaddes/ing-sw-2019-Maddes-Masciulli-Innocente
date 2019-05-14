package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class  RailGun extends AlternateFireWeapon {


    public  RailGun(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost, alternativeEffectCost);
    }

    public ArrayList<Player> getTargetsBaseEffect() {
        ArrayList<Player> targets=null;

        for (int i = 0; i < 4; i++){
            if (getOwner().getPosition().checkDirection(i)) {
                targets.addAll(getOwner().getPosition().getNextSquare(i).getSquarePlayers());

                if (getOwner().getPosition().getNextSquare(i).checkDirection(i))
                    targets.addAll(getOwner().getPosition().getNextSquare(i).getNextSquare(i).getSquarePlayers());

                if (getOwner().getPosition().getNextSquare(i).getNextSquare(i).checkDirection(i))
                    targets.addAll(getOwner().getPosition().getNextSquare(i).getNextSquare(i).getNextSquare(i).getSquarePlayers());

                if (getOwner().getPosition().getNextSquare(i).getNextSquare(i).getNextSquare(i).checkDirection(i))
                    targets.addAll(getOwner().getPosition().getNextSquare(i).getNextSquare(i).getNextSquare(i).getSquarePlayers());

            }
        }

        targets.addAll(getOwner().getPosition().getSquarePlayers());
        return targets;
    }

    public ArrayList<Player> getTargetsAlternativeEffect() {

        return getTargetsBaseEffect();
    }




    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() != 0) {
            damage(targets.get(0),3);
        }
        else
            throw new NullPointerException();
    }

    public void fireAlternativeEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() == 1) {
            damage(targets.get(0),2);
        }
        else if (targets.size() == 2) {
            damage(targets.get(0), 2);
            damage(targets.get(1), 2);
        }

        throw new NullPointerException();
     }
}



