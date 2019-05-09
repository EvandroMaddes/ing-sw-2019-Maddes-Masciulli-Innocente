package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.TwoOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class RocketLauncher extends TwoOptionalEffectWeapon {

    private Square firstTargetPosition;
    public RocketLauncher(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost, AmmoCube[] secondOptionalEffectCost) {
        super(colour, name, reloadCost, firstOptionalEffectCost, secondOptionalEffectCost);
    }

    public ArrayList<Player> getTargetsBaseEffect() {
        ArrayList<Player> target;
        ArrayList<Player> giocatoriNellaPartita = null;//Todo NB metodo getSquarePlayer deve ricevere i players in game

        target = new ArrayList<Player>();
        target = getOwner().getPosition().findVisiblePlayers();
        target.removeAll(getOwner().getPosition().getSquarePlayers(giocatoriNellaPartita));
        return target;
    }

    public ArrayList<Player> getTargetsFirstOptionalEffect() {

        throw  new IllegalStateException();//this exception is managed by controller: target is a square with distance two
    }

    public ArrayList<Player> getTargetsSecondOptionalEffect() {
       return getTargetsBaseEffect();

    }



        public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() != 0) {
            firstTargetPosition = targets.get(0).getPosition();
            damage(targets.get(0), 1);
            targets.get(0).setPosition(destination);
        }
        else
            throw new NullPointerException("nobody to damage");
    }

    public void fireFirstOptionalEffect(ArrayList<Player> targets, Square destination){
        getOwner().setPosition(destination);
    }

    public void fireSecondOptionalEffect(ArrayList<Player> targets, Square destination){
        Iterator iterator;
        iterator = targets.iterator();
        if (targets.size() != 0) {
            while (iterator.hasNext()) {
                Player currentTarget = (Player) iterator.next();
                //verify if target is distant only one square
                if (currentTarget.getPosition() == firstTargetPosition) {
                    damage(currentTarget, 1);
                }
            }
        }
    }

}
