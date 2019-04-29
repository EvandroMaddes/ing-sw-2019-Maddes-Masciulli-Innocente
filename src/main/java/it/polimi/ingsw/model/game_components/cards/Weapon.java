package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public abstract class Weapon extends Card {

    private AmmoCube[] reloadCost;
    private boolean loaded;

    /**
     *
     * @param colour is the weapon colour
     * @param name is the weapon name
     * @param reloadCost is an array of the ammo cube needed to reload the weapon
     */
    public Weapon(CubeColour colour, String name, AmmoCube[] reloadCost)
    {
        super(colour, name);
        this.reloadCost = reloadCost;
        loaded = true;
    }

    /**
     *
     * @return loaded
     */
    public boolean isLoaded()
    {
        return loaded;
    }

    /**
     * invert the loaded state of the weapon
     */
    public void invertLoadedState()
    {
        loaded = !loaded;
    }

    /**
     * this method is overloaded for every weapon
     */
    public void fire()
    {
        invertLoadedState();
    }



}
