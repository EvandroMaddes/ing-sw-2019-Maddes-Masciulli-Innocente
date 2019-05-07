package it.polimi.ingsw.model.game_components.ammo;

import it.polimi.ingsw.model.player.Player;


/**
 *
 * @author Federico Innocente
 *
 *  Class that rappresent the base of every ammo tiles, with two ammo cube
 */
public class AmmoTile {

    private AmmoCube[] ammoCubes;
    private boolean isPowerUpTile;


    public AmmoTile(AmmoCube firstAmmo, AmmoCube secondAmmo, AmmoCube thirdAmmo, boolean isPowerUpTile)
    {   this.isPowerUpTile = isPowerUpTile;
        if (!isPowerUpTile)
        {
            ammoCubes = new  AmmoCube[3];
            ammoCubes[3] = thirdAmmo;
        }
        else{
            ammoCubes = new AmmoCube[2];
        }
        ammoCubes[0] = firstAmmo;
        ammoCubes[1] = secondAmmo;
    }

    /**
     *
     * @param player is the player that grab the AmmoTiles
     *
     * if a player haven't pass the limit on ammo cube, grab the two cubes
     */
    public void pickAmmo(Player player)
    {
        if (player.getCubeColourNumber(ammoCubes[0].getColour()) <3 )
            player.addAmmo(ammoCubes[0]);
        if (player.getCubeColourNumber(ammoCubes[1].getColour()) <3 )
            player.addAmmo(ammoCubes[1]);
        if (!isPowerUpTile){
            if (player.getCubeColourNumber(ammoCubes[2].getColour()) <3 ){
                player.addAmmo(ammoCubes[2]);
            }
        }
    }

}
