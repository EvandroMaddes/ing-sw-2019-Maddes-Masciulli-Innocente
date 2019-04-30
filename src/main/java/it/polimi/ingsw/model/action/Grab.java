package it.polimi.ingsw.model.action;

import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;


/**
 * @author Federico Innocente
 */
public class Grab extends ActionDecorator {

    private Weapon choosenWeapon;

    /**
     *
     * @param action is the decorated action
     */
    public Grab (Action action, Weapon choice)
    {
        super(action);
        choosenWeapon = choice;
    }

    /**
     * if the player is on a spawn square, grab the choosen weapon; if he is on a basic square, grab the ammo
     * @param player is the player who perform the action
     */
    @Override
    public void performAction(Player player)
    {
        if (player.getPosition() instanceof BasicSquare)
        {
            BasicSquare position = (BasicSquare) player.getPosition();
            position.grabAmmoTile(player);
        }
        else
        {
            SpawnSquare position = (SpawnSquare) player.getPosition();
            position.grabWeapon(choosenWeapon, player);
        }
    }
}
