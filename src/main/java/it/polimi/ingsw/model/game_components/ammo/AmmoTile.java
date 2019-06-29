package it.polimi.ingsw.model.game_components.ammo;

import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;


/**
 *
 * @author Federico Innocente
 *
 *  Class that rappresent the base of every ammo tiles, with two ammo cube
 */
public class AmmoTile {

    private AmmoCube[] ammoCubes;
    private PowerUp powerUp;
    private boolean isPowerUpTile;

    public AmmoTile(AmmoCube firstAmmo, AmmoCube secondAmmo, AmmoCube thirdAmmo, boolean isPowerUpTile)
    {
        this.isPowerUpTile = isPowerUpTile;
        if (!isPowerUpTile)
        {
            ammoCubes = new  AmmoCube[3];
            ammoCubes[2] = thirdAmmo;
        }
        else{
            ammoCubes = new AmmoCube[2];
        }
        ammoCubes[0] = firstAmmo;
        ammoCubes[1] = secondAmmo;
    }

    /**
     * Called when the tile is placed on board, set it's PowerUp
     * @param powerUp is the powerUp associated with the tile
     */
    public void setPowerUp(PowerUp powerUp){
        this.powerUp = powerUp;
    }

    /**
     * Getter method
     * @return the AmmoCube[] vector
     */
    public AmmoCube[] getAmmoCubes() {
        return ammoCubes;
    }

    /**
     * Getter method
     * @return true if is a PowerUpTile (so AmmoCube'll be a 2 elements Vector)
     */
    public boolean isPowerUpTile() {
        return isPowerUpTile;
    }


    /**
     * if a player haven't pass the limit on 3 ammo cube for each color, grab all the possible AmmoCubes,
     * if is a PowerUpTile he'll grab from a vector containing 2 elements and the powerUp
     *
     * @param player is the player that grab the AmmoTiles
     */
    public void pickAmmo(Player player)
    {
        if (player.getCubeColourNumber(ammoCubes[0].getColour()) <3 )
            player.addAmmo(ammoCubes[0]);
        if (player.getCubeColourNumber(ammoCubes[1].getColour()) <3 )
            player.addAmmo(ammoCubes[1]);
        if ((!isPowerUpTile)&&player.getCubeColourNumber(ammoCubes[2].getColour()) <3)
                player.addAmmo(ammoCubes[2]);
        else if (isPowerUpTile)
            player.addPowerUp(powerUp);

    }

}
