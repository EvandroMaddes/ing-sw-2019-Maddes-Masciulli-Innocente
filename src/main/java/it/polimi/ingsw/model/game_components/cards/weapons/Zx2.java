package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Zx2 extends AlternateFireWeapon {
    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param alternativeEffectCost
     */
    public Zx2(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost, alternativeEffectCost);
    }


    public ArrayList<Player> getTargetsBaseEffect(){
        ArrayList<Player> targets;
        targets = getOwner().getPosition().findVisiblePlayers();
        return targets;
    }

    public ArrayList<Player> getTargetsAlternativeEffect() {
        ArrayList<Player> targets = null;
       targets = getTargetsBaseEffect();
        return targets;
    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() != 0) {
            damage(targets.get(0), 1);
            mark(targets.get(0),2);
        }
        else
            throw new NullPointerException("nobody to damage");


    }

    public void fireAlternativeEffect(ArrayList<Player> targets, Square destination) {
        if (!targets.get(0).getPosition().getSquareColour().equals(targets.get(1).getPosition().getSquareColour())&&
                !targets.get(0).getPosition().getSquareColour().equals(targets.get(2).getPosition().getSquareColour()) &&
                !targets.get(1).getPosition().getSquareColour().equals(targets.get(2).getPosition().getSquareColour())) {
            for (int i = 0; i < 3; i++) {
                if (targets.size() != 0) {
                    mark(targets.get(i), 1);

                } else
                    throw new NullPointerException("nobody to damage");
            }

        }else
             throw new IllegalArgumentException("targets aren?t in three different rooms");// lanciata se i tre bersagli non sono in tre stanze differenti

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
    public void performEffectOne(List<Object> targets) {

    }

    @Override
    public void performEffectTwo(List<Object> targets) {

    }

}
