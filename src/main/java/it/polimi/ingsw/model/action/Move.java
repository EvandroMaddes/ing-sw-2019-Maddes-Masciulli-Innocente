package it.polimi.ingsw.model.action;

import it.polimi.ingsw.model.player.Player;


/**
 * @author Federico Innocente
 */
public class Move extends ActionDecorator {

    private int movementDirection;


    /**
     *
     * @param action is the decorated action
     * @param movementDirection is the direction of the movement
     */
    public Move (Action action, int movementDirection)
    {
        super(action);
        this.movementDirection = movementDirection;
    }

    /**
     *
     * @param player is moved by one cell, according with movement
     */
    @Override
    public void performAction(Player player)
    {
        player.setPosition( player.getPosition().getNextSquare(movementDirection) );
    }
}
