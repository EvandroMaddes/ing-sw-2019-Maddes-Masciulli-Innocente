package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.Direction;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author Federico Innocente
 *
 * abstract class that is the base for both weapons and powerUps
 */
public abstract class Card implements BaseFightAction, Iterator {

    private CubeColour colour;
    private String name;


    /**
     *
     * @param colour is the colour of the card
     * @param name is the name of the card
     */
    public Card(CubeColour colour, String name)
    {
        this.name = name;
        this.colour = colour;
    }

    /**
     *
     * @return the colour of the card
     */
    public CubeColour getColour()
    {
        return this.colour;
    }

    /**
     *
     * @param targets are the targets who are applied the damage to
     * @param amount is the number of damage applied
     * @param damager is the player who applied the damage
     */
    @Override
    public void damage(ArrayList<Player> targets, int amount, Player damager)
    {
        Iterator iterator = targets.iterator();
        Player target;

        while (iterator.hasNext())
        {
            target = (Player)iterator.next();
            target.getPlayerBoard().addDamages(damager, amount);
        }
    }

    /**
     *
     * @param targets ate the targets of the marking
     * @param amount is the number of marks given
     * @param marker is the player who apply marks
     */
    @Override
    public void mark(ArrayList<Player> targets, int amount, Player marker)
    {
        Iterator iterator = targets.iterator();
        Player target;

        while (iterator.hasNext())
        {
            target = (Player)iterator.next();
            target.getPlayerBoard().addMarks(marker, amount);
        }
    }

    /**
     *
     * @param target is the player moved
     * @param direction is the direction in which the player is moved
     */
    // !!! dovrei lanciare un eccezione quando provo a muovere in direzione in cui non posso andare?
    @Override
    public void move(Player target, Direction direction)
    {
        if (target.getPosition().checkDirection(direction))
            target.setPosition(target.getPosition().getNextSquare(direction));
    }
}
