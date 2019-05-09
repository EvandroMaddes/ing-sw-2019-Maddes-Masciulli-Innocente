package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class Flametrthrower extends AlternateFireWeapon {


    public Flametrthrower(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost, alternativeEffectCost);
    }


    //todo tona i tutti i giocatori distanti due; il controller deve verificare che i giocatori scelti son nella stessa direzione
    public ArrayList<Player> getTargetsBaseEffect() {
        ArrayList<Player> giocatoriNellaPartita = null;//Todo NB metodo getSquarePlayer deve ricevere i players in game
        ArrayList<Player> targets = null;
        for (int i = 0; i < 4; i++){
            if (getOwner().getPosition().checkDirection(i)) {
                targets.addAll(getOwner().getPosition().getNextSquare(i).getSquarePlayers(giocatoriNellaPartita));

             if (getOwner().getPosition().getNextSquare(i).checkDirection(i))
                 targets.addAll(getOwner().getPosition().getNextSquare(i).getNextSquare(i).getSquarePlayers(giocatoriNellaPartita));
            }
        }
        return targets;
    }

    public ArrayList<Player> getTargetsAlternativeEffect(){
        return getTargetsBaseEffect();
    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){

        if (targets.size() != 0) {
            damage(targets.get(0), 1);
            damage(targets.get(1), 1);
        }
        else
            throw new NullPointerException();
    }

    public void fireAlternativeEffect(ArrayList<Player> targets, Square destination){
        Iterator iterator;
        iterator = targets.iterator();
        if (targets.size() != 0) {
            while (iterator.hasNext()) {
                Player currentTarget = (Player) iterator.next();
                //verify if target is distant only one square
                if (currentTarget.getPosition() == getOwner().getPosition().getNextSquare(0) ||
                        currentTarget.getPosition() == getOwner().getPosition().getNextSquare(1) ||
                        currentTarget.getPosition() == getOwner().getPosition().getNextSquare(2) ||
                        currentTarget.getPosition() == getOwner().getPosition().getNextSquare(3)) {

                    damage(currentTarget, 2);
                } else { //target is distant two square
                    damage(currentTarget, 1);
                }

            }
        }  else
            throw new NullPointerException();
    }

}