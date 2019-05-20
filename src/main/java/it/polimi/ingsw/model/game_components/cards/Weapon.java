package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public abstract class Weapon extends Card {

    private AmmoCube[] reloadCost;
    private boolean loaded;
    private final boolean[] effectsEnable;
    private boolean[] usableEffect;



    public Weapon(CubeColour colour, String name, boolean[] effectsEnable, AmmoCube[] reloadCost) {
        super(colour, name);
        this.reloadCost = reloadCost;
        this.effectsEnable = effectsEnable;
        loaded =true;
    }

    /**
     *
     * @return reload cost
     */
    public AmmoCube[] getReloadCost()
    {
        return reloadCost;
    }

    public AmmoCube[] getGrabCost(){
        AmmoCube[] grabCost = new AmmoCube[getReloadCost().length - 1];

        for (int i = 0; i < grabCost.length; i++)
            grabCost[i] = getReloadCost()[i+1];
        return grabCost;
    }

    /**
     *
     * @return loaded
     */
    public boolean isLoaded()
    {
        return loaded;
    }
    
    public void setUnloaded(){
        this.loaded = false;
    }

    public void setLoaded(){
        this.loaded = true;
    }

    public boolean canActivateAnEffect(){return false;}

    public abstract void fire(ArrayList<Player> targets, Square destination , int selectedEffect);

    public abstract ArrayList<Player> getTargets(int selectedEffect);

    public abstract ArrayList<Player> getTargetsBaseEffect();

    protected abstract void fireBaseEffect(ArrayList<Player> targets, Square destination);








}
