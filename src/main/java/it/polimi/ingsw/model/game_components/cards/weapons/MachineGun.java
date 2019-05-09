package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.TwoOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class MachineGun extends TwoOptionalEffectWeapon {

    public MachineGun(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost, AmmoCube[] secondOptionalEffectCost) {
        super(colour, name, reloadCost, firstOptionalEffectCost, secondOptionalEffectCost);
    }

    public ArrayList<Player> getTargetsBaseEffect(){
        return getOwner().getPosition().findVisiblePlayers();
    }

    public ArrayList<Player> getTargetsFirstOptionalEffect(){
        return getOwner().getPosition().findVisiblePlayers();
    }

    public ArrayList<Player> getTargetsSecondOptionalEffect(){
        return getOwner().getPosition().findVisiblePlayers();
    }

    @Override
    public void fireBaseEffect(ArrayList<Player> targets, Square destination) {
        if(targets.size() < 2)
            throw new NullPointerException();
        damage(targets.get(0), 1);
        damage(targets.get(1), 1);
    }

    public void fireFirstOptionalEffect(ArrayList<Player> targets, Square destination) {
        damage(targets.get(0), 1);
    }

    public void fireSecondOptionalEffect(ArrayList<Player> targets, Square destination) {
        switch (targets.size()){
            case 0:{
                throw new NullPointerException();
            }
            case 1:{
                damage(targets.get(0), 1);
                break;
            }
            case 2:{
                damage(targets.get(1), 1);
                break;
            }
            default:{
                damage(targets.get(1),1);
                damage(targets.get(2),1);
            }
        }
    }
}

