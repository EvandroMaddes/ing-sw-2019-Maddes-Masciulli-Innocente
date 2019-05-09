package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class ShockWave extends AlternateFireWeapon {
    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param alternativeEffectCost
     */
    public ShockWave(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost, alternativeEffectCost);
    }



    public ArrayList<Player> getTargetsBaseEffect(){
        ArrayList<Player> targets = null;
        ArrayList<Player> giocatoriNellaPartita = null;//Todo NB metodo getSquarePlayer deve ricevere i players in game

        for (int i = 0; i < 4; i++) {
            if (getOwner().getPosition().checkDirection(i))
                targets.addAll(getOwner().getPosition().getNextSquare(i).getSquarePlayers(giocatoriNellaPartita));
        }
        return targets;
    }

    public ArrayList<Player> getTargetsAlternativeEffect() {
        ArrayList<Player> targets = null;
       targets = getTargetsBaseEffect();
        return targets;
    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        Iterator iterator;
        iterator = targets.iterator();

       if(targets.get(0).setPosition()!= targets.get(1).getPosition() !=targets.get(2).getPosition()) {

           if (targets.size() != 0) {
               int i = 0;

               while (iterator.hasNext() && i < 0) {
                   Player target = (Player) iterator.next();
                   damage(target, 2);
                   i++;
               }
           } else
               throw new NullPointerException("nobody to damage");
       }else
           throw new IllegalArgumentException("Targets aren't in three different squares");// lanciata se i tre bersagli non sono su tre quadrati differenti

    }

    public void fireAlternativeEffect(ArrayList<Player> targets, Square destination){
        Iterator iterator;
        iterator = targets.iterator();

        if (targets.size() != 0) {

            while (iterator.hasNext()) {
                Player target = (Player) iterator.next();
                damage(target, 1);
            }

        } else
            throw new NullPointerException("nobody to damage");
    }
}
