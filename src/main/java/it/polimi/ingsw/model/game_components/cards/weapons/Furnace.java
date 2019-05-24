package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.AlternateFireWeapon;
import it.polimi.ingsw.model.player.Player;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;

public class Furnace extends AlternateFireWeapon {

    public ArrayList<Player> getTargetsBaseEffect(){
        return null;


    }

    public ArrayList<Player> getTargetsAlternativeEffect(){
        return null;

    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){

    }

    /**
     *
     * @param colour
     * @param name
     * @param reloadCost
     * @param alternativeEffectCost
     */

    public Furnace(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] alternativeEffectCost) {
        super(colour, name, reloadCost, alternativeEffectCost);
    }

    public void fireAlternativeEffect(ArrayList<Player> targets, Square destination){
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
