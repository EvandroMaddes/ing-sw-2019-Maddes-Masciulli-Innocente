package it.polimi.ingsw.model.game_components.ammo;

import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;

/**
 * @author Federico Innocente
 *
 * class for ammo tiles, that complete the two base cube with a powerUp
 */
public class PowerUpAmmoTile extends AmmoTile {

    private PowerUp powerUp;

    //todo Aggiungere getter per powerUp, gli altri sono implementati nella superclasse

    public PowerUpAmmoTile( AmmoCube firstAmmo, AmmoCube secondAmmo, PowerUp powerUp )
    {
        super(firstAmmo, secondAmmo);
        this.powerUp = powerUp;
    }

    /**
     *
     * @param player is the player that grab the AmmoTiles
     *
     * override the method to grab the powerUp too, if the player has less than 3 of them
     */
    @Override
    public void pickAmmo(Player player)
    {
        super.pickAmmo(player);

        if (player.getPowerUps().size() < 3 )
            player.addPowerUp(powerUp);
    }
}
