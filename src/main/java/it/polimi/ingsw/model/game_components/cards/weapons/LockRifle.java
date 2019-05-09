package it.polimi.ingsw.model.game_components.cards.weapons;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.OneOptionalEffectWeapon;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class LockRifle extends OneOptionalEffectWeapon {

    public LockRifle(CubeColour colour, String name, AmmoCube[] reloadCost, AmmoCube[] firstOptionalEffectCost) {
        super(colour, name, reloadCost, firstOptionalEffectCost);
    }

    /**
     *
     * @return true if the base effect can be activated
     */
    @Override
    public boolean canActivateAnEffect() {
        return !getTargetsBaseEffect().isEmpty();
    }

    @Override
    public void fire(ArrayList<Player> targets, Square destination, int selectedEffect) {
        switch (selectedEffect){
            case 1:{
                fireBaseEffect(targets, destination);
                break;
            }
            case 2:{
                fireFirstOptionalEffect(targets, destination);
                break;
            }
        }
    }

    public ArrayList<Player> getTargetsBaseEffect(){
        return getOwner().getPosition().findVisiblePlayers();
    }

    public ArrayList<Player> getTargetsFirstOptionalEffect(){
        return getOwner().getPosition().findVisiblePlayers();
    }

    public void fireBaseEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() != 0) {
            damage(targets.get(0), 2);
            mark(targets.get(0), 1);
        }
        else
            throw new NullPointerException();
    }

    public void fireFirstOptionalEffect(ArrayList<Player> targets, Square destination){
        if (targets.size() != 0) {
            mark(targets.get(0), 1);
        }
        else
            throw new NullPointerException();
    }

}
