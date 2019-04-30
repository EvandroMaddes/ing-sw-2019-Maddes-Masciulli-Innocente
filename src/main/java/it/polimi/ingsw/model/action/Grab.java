package it.polimi.ingsw.model.action;

import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;


public class Grab extends ActionDecorator {

    private Weapon chooseWeapon;

    /**
     *
     * @param action is the decorated action
     */
    public Grab (Action action, Weapon choice)
    {
        super(action);
        chooseWeapon = choice;
    }


    @Override
    public void performAction(Player player)
    {
        if (/* se il quadrato è base */)
        {
            BasicSquare position = (BasicSquare) player.getPosition();
            position.grabAmmoTile(player);
        }
        else
        {
            SpawnSquare position = (SpawnSquare) player.getPosition();
            position.
        }
    }
}
