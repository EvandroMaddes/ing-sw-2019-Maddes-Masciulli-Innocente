package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.TwoOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RocketLauncher extends TwoOptionalEffectWeapon {

    private Square firstTargetPosition;
    public RocketLauncher(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost, AmmoCube[] secondOptionalEffectCost) {
        super(colour, name, reloadCost, firstOptionalEffectCost, secondOptionalEffectCost);
    }

    public ArrayList<Player> getTargetsBaseEffect() {
        ArrayList<Player> target;

        target = new ArrayList<Player>();
        target = getOwner().getPosition().findVisiblePlayers();
        target.removeAll(getOwner().getPosition().getSquarePlayers());
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

    @Override
    public ControllerViewEvent getTargetEffectOne() {
        return null;
    }

    @Override
    public ControllerViewEvent getTargetEffectTwo() {
        return null;
    }

    @Override
    public ControllerViewEvent getTargetEffectThree() {
        return null;
    }

    @Override
    public void performEffectOne(List<Object> targets) {

    }

    @Override
    public void performEffectTwo(List<Object> targets) {

    }

    @Override
    public void performEffectThree(List<Object> targets) {
    }

}
