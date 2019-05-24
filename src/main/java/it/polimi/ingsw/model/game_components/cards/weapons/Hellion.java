package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Hellion extends AlternateFireWeapon {
    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param alternativeEffectCost
     */
    public Hellion(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost, alternativeEffectCost);
    }

    public ArrayList<Player> getTargetsBaseEffect(){
        ArrayList<Player> targets = getOwner().getPosition().findVisiblePlayers();
        for (Player p: targets) {
            if(p.getPosition() == getOwner().getPosition())
                targets.remove(p);
        }
        return targets;
    }

    public ArrayList<Player> getTargetsAlternativeEffect(){
        return getTargetsBaseEffect();
    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if (targets.isEmpty())
            throw new NullPointerException();
        damage( targets.get(0), 1);
        for (Player p: targets.get(0).getPosition().getSquarePlayers() ) {
            mark(p,1);
        }
    }

    public void fireAlternativeEffect(ArrayList<Player> targets, Square destination){
        if (targets.isEmpty())
            throw new NullPointerException();
        damage( targets.get(0), 1);
        for (Player p: targets.get(0).getPosition().getSquarePlayers() ) {
            mark(p,2);
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
    public void performEffectOne(List<Object> targets) {

    }

    @Override
    public void performEffectTwo(List<Object> targets) {

    }

}
