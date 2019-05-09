package it.polimi.ingsw.model.action;

import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;

import java.security.InvalidParameterException;

/**
 * @author Federico Innocente
 */
public class Shot extends ActionDecorator {

    private Action action;
    private Weapon weapon;


    /**
     * @param action is the action to decorate
     * @param weapon is the weapon to shot with
     */
    public Shot(Action action, Weapon weapon) {
        super(action);
        this.weapon = weapon;
    }

    /**
     * @param player is the player who perform the action
     * @throws InvalidParameterException is throw if the weapon is unloaded or if the weapon owner is not player
     */
    @Override
    public void performAction(Player player) throws InvalidParameterException {
        /*
        if ( player.getWeaponIndex(weapon) == -1 )

            throw new InvalidParameterException("Il giocatore non possiede l'arma");
        if (weapon.isLoaded()) {

            weapon.fire();
            weapon.invertLoadedState();

        }
        else
            throw new InvalidParameterException("Arma scarica");
       */
    }

}