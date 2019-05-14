package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class PowerGlove extends AlternateFireWeapon {
    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param alternativeEffectCost
     */
    public PowerGlove(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost, alternativeEffectCost);
    }

    public ArrayList<Player> getTargetsBaseEffect(){
        ArrayList<Player> targets = null;

        for (int i = 0; i < 4; i++) {
            if (getOwner().getPosition().checkDirection(i))
                targets.addAll(getOwner().getPosition().getNextSquare(i).getSquarePlayers());
        }
        return targets;
    }

    public ArrayList<Player> getTargetsAlternativeEffect() {
        ArrayList<Player> targets = null;
        for (int i = 0; i < 4; i++){
            if (getOwner().getPosition().checkDirection(i)) {
                targets.addAll(getOwner().getPosition().getNextSquare(i).getSquarePlayers());

                if (getOwner().getPosition().getNextSquare(i).checkDirection(i))
                    targets.addAll(getOwner().getPosition().getNextSquare(i).getNextSquare(i).getSquarePlayers());
            }
        }
        return targets;
    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() != 0) {
            getOwner().setPosition(targets.get(0).getPosition());
            damage(targets.get(0), 1);
            mark(targets.get(0),2);
        }
        else
            throw new NullPointerException("nobody to damage");


    }

    public void fireAlternativeEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() != 0) {
            damage(targets.get(0), 2);
            damage(targets.get(1), 2);
            getOwner().setPosition(targets.get(1).getPosition());

        }
        else
            throw new NullPointerException("nobody to damage");

    }
}

