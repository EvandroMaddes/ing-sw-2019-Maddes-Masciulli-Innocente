package it.polimi.ingsw.model.gamecomponents.ammo;

import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;


/**
 * Class that represent the base of every ammo tiles, with two ammo cube and,
 * depending on the draw, a powerUp or a third ammo cube
 *
 * @author Federico Innocente
 */
public class AmmoTile {

    /**
     * The AmmoTile's AmmoCube array
     */
    private AmmoCube[] ammoCubes;
    /**
     * The AmmoTile's powerUp, is null if the tile isn't a powerUp tile
     */
    private PowerUp powerUp;
    /**
     * boolean that is true if the tile has a powerUp, false if has a third AmmoCube
     */
    private boolean isPowerUpTile;

    /**
     * Constructor: set the AmmoCubes array and, depending on isPowerUpValue, the third resource (PowerUp or AmmoTile)
     *
     * @param firstAmmo     is the first AmmoCube
     * @param secondAmmo    is the second AmmoCube
     * @param thirdAmmo     is the third AmmoCube, null if isPowerUpTile
     * @param isPowerUpTile is true if the third resource is a PowerUp
     */
    public AmmoTile(AmmoCube firstAmmo, AmmoCube secondAmmo, AmmoCube thirdAmmo, boolean isPowerUpTile) {
        this.isPowerUpTile = isPowerUpTile;
        if (!isPowerUpTile) {
            ammoCubes = new AmmoCube[3];
            ammoCubes[2] = thirdAmmo;
        } else {
            ammoCubes = new AmmoCube[2];
        }
        ammoCubes[0] = firstAmmo;
        ammoCubes[1] = secondAmmo;
    }

    /**
     * Called when the tile is placed on board, set it's PowerUp
     *
     * @param powerUp is the powerUp associated with the tile
     */
    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    /**
     * Getter method
     *
     * @return the AmmoCube[] vector
     */
    public AmmoCube[] getAmmoCubes() {
        return ammoCubes;
    }

    /**
     * Getter method
     *
     * @return true if is a PowerUpTile (so AmmoCube'll be a 2 elements Vector)
     */
    public boolean isPowerUpTile() {
        return isPowerUpTile;
    }


    /**
     * if a player hasn't pass the limit on 3 ammo cube for each color, grab all the possible AmmoCubes,
     * if is a PowerUpTile he'll grab from a vector containing 2 elements and the powerUp
     *
     * @param player is the player that grab the AmmoTiles
     */
    public void pickAmmo(Player player) {
        if (player.getCubeColourNumber(ammoCubes[0].getColour()) < 3)
            player.addAmmo(ammoCubes[0]);
        if (player.getCubeColourNumber(ammoCubes[1].getColour()) < 3)
            player.addAmmo(ammoCubes[1]);
        if ((!isPowerUpTile) && player.getCubeColourNumber(ammoCubes[2].getColour()) < 3)
            player.addAmmo(ammoCubes[2]);
        else if (isPowerUpTile)
            player.addPowerUp(powerUp);

    }

}
