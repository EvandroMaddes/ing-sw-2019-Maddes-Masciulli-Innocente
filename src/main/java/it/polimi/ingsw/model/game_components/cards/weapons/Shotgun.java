package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class Shotgun extends AlternateFireWeapon {
    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param alternativeEffectCost
     */
    public Shotgun(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost, alternativeEffectCost);
    }

    public ArrayList<Player> getTargetsBaseEffect(){
        ArrayList<Player> targets;
        ArrayList<Player> giocatoriNellaPartita = null;//Todo NB metodo getSquarePlayer deve ricevere i players in game

        targets = getOwner().getPosition().getSquarePlayers(giocatoriNellaPartita);
        return targets;
    }

    public ArrayList<Player> getTargetsAlternativeEffect() {
        ArrayList<Player> targets = null;
        ArrayList<Player> giocatoriNellaPartita = null;//Todo NB metodo getSquarePlayer deve ricevere i players in game

        for (int i = 0; i < 4; i++) {
            if (getOwner().getPosition().checkDirection(i))
                targets.addAll(getOwner().getPosition().getNextSquare(i).getSquarePlayers(giocatoriNellaPartita));
        }
        return targets;
    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() != 0) {
            damage(targets.get(0), 3);
            targets.get(0).setPosition(destination);
        }
        else
            throw new NullPointerException();


    }

    public void fireAlternativeEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() != 0) {
            damage(targets.get(0), 2);
        }
        else
            throw new NullPointerException();

    }
}
