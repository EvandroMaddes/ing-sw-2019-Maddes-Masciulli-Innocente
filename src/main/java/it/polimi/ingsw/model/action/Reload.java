package it.polimi.ingsw.model.action;


import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;


/**
 * @author Federico Innocente
 */
public class Reload extends ActionDecorator{

    private Action action;
    private Weapon weapon;
    private PowerUp[] powerUpsCubes;


    /**
     *
     * @param action is the decorated action
     * @param weapon is the weapon to reload
     */
    public Reload(Action action, Weapon weapon, PowerUp[] powerUpsCubes)
    {
        super(action);
        this.weapon = weapon;
        this.powerUpsCubes = powerUpsCubes;
    }

    /**
     * The method reload the weapon, removing the power Ups passed (assumed already checked) and the ammo cubs of the reloading cost
     * Is assumed that the player can affort the cost and that the paymode is already checked
     * @param player is the palyer who is reloading
     */
    @Override
    public void performAction(Player player)
    {
        boolean isPowerUp;
        for (int i = 0; i < weapon.getReloadCost().length; i++)
        {
            int j = 0;
            isPowerUp = false;

            while (j < powerUpsCubes.length && !isPowerUp)
            {
                if (weapon.getReloadCost()[i].getColour() == powerUpsCubes[j].getColour())
                {
                    player.getPowerUps().remove(powerUpsCubes[j]);
                    isPowerUp = true;
                }
                j++;
            }

            if (!isPowerUp)
            {
                player.getAmmo().remove(new AmmoCube(weapon.getReloadCost()[i].getColour()));
            }

        }

        weapon.setLoaded();
    }
}
