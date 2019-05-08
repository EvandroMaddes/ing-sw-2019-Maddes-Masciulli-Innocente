package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.TwoOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class PlasmaGun extends TwoOptionalEffectWeapon {

    public PlasmaGun(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost, AmmoCube[] secondOptionalEffectCost) {
        super(colour, name, reloadCost, firstOptionalEffectCost, secondOptionalEffectCost);
    }

    public ArrayList<Player> getTargetsBaseEffect(){
        return getOwner().getPosition().findVisiblePlayers();
    }

    public ArrayList<Player> getTargetsFirstOptionalEffect(){
        throw new IllegalStateException();
    }

    public ArrayList<Player> getTargetsSecondOptionalEffect(){
        return getOwner().getPosition().findVisiblePlayers();
    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() < 1)
            throw new NullPointerException();
        damage(targets.get(0), 2);
    }

    public void fireFirstBaseEffect(ArrayList<Player> targets, Square destination){
        getOwner().setPosition(destination);
    }

    public void fireSecondBaseEffectEffect(ArrayList<Player> targets, Square destination){
        fireBaseEffect(targets, destination);
        damage(targets.get(0), 1);
    }
}
