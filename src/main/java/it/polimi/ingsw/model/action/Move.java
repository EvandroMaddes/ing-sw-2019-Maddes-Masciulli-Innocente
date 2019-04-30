package it.polimi.ingsw.model.action;

import it.polimi.ingsw.model.board.Direction;
import it.polimi.ingsw.model.player.Player;


public class Move extends ActionDecorator {

    private Direction movement;


    /**
     *
     * @param action is the decorated action
     * @param movement is the direction of the movement
     */
    public Move (Action action, Direction movement)
    {
        super(action);
        this.movement = movement;
    }

    /**
     *
     * @param player is moved by one cell, according with movement
     */
    @Override
    public void performAction(Player player)
    {
        player.setPosition( movement.getNextSquare() );
    }
}
