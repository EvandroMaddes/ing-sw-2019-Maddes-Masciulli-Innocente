package it.polimi.ingsw.model.game_components.ammo;

import it.polimi.ingsw.model.player.Player;

/**
 * @author Federico Innocente
 *
 * ammo tiles that complete the two base cube with another ammmo cube
 */

public class CubeAmmoTile extends AmmoTile {

    private AmmoCube thirdAmmo;

    //todo Aggiungere getter per thirdAmmo, gli altri sono implementati nella super classe


    public CubeAmmoTile( AmmoCube firstAmmo, AmmoCube secondAmmo, AmmoCube thirdAmmo )
    {
        super(firstAmmo, secondAmmo);
        this.thirdAmmo = thirdAmmo;
    }

    /**
     *
     * @param player is the player that grab the AmmoTiles
     *
     * override the method to grab the third cube too, if the player has less then three of that colour
     *
     */
    @Override
    public void pickAmmo(Player player)
    {
        super.pickAmmo(player);

        if (player.getCubeColourNumber(thirdAmmo.getColour()) <3 )
            player.addAmmo(thirdAmmo);
    }
}

