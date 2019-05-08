package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.OneOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class Distructor extends OneOptionalEffectWeapon {

    public Distructor(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost) {
        super(colour, name, reloadCost, firstOptionalEffectCost);
    }

    @Override
    public ArrayList<Player> getTarget(int selectedEffect){
        ArrayList<Player> targets;

        switch(selectedEffect){
            case 1: {
                targets = getTargetBaseEffect();
                break;
            }
            case 2:{
                 targets = getTargetFirstOptionalEffect();
                break;
            }
            default:{
                throw new IllegalArgumentException();
            }
        }
        return targets;
    }

    @Override
    public void fire(ArrayList<Player> targets, Square destination, int selectedEffect) {
        switch (selectedEffect){
            case 1:{
                fireBaseEffect();
                break;
            }
            case 2:{
                fireFirstOptionalEffect();
                break;
            }
        }
    }
}
