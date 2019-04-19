package it.polimi.ingsw.model.game_components.ammo;

import it.polimi.ingsw.model.player.Player;


/**
 *
 * @author Federico Innocente
 *
 *  Class that rappresent the base of every ammo tiles, with two ammo cube
 */
public abstract class AmmoTile {

    private AmmoCube firstAmmo;
    private AmmoCube secondAmmo;

    public AmmoCube getFirstAmmo() {
        return firstAmmo;
    }

    public AmmoCube getSecondAmmo() {
        return secondAmmo;
    }

    public AmmoTile(AmmoCube firstAmmo, AmmoCube secondAmmo )
    {
        this.firstAmmo = firstAmmo;
        this.secondAmmo = secondAmmo;
    }

    /**
     *
     * @param player is the player that grab the AmmoTiles
     *
     * if a player haven't pass the limit on ammo cube, grab the two cubes
     */
    public void pickAmmo(Player player)
    {
        if (player.getCubeColourNumber(firstAmmo.getColour()) <3 )
            player.addAmmo(firstAmmo);
        if (player.getCubeColourNumber(secondAmmo.getColour()) <3 )
            player.addAmmo(secondAmmo);
    }

}
