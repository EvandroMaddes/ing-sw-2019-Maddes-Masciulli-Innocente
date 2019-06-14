package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author Federico Innocente
 *
 * abstract class that is the base for both weapons and powerUps
 */
public abstract class Card implements BaseFightAction {

    private CubeColour colour;
    private String name;
    private Player owner;




    /**
     *
     * @param colour is the colour of the card
     * @param name is the name of the card
     */
    public Card(CubeColour colour, String name)
    {
        this.name = name;
        this.colour = colour;
        this.owner = null;
    }

    /**
     * Getter method
     * @return the card's name
     */
    public String getName() {
        return name;
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
     * @return card owner
     */
    public Player getOwner()
    {
        return owner;
    }

    /**
     *
     * @param player
     */
    public void setOwner(Player player)
    {
        this.owner = player;
    }


    @Override
    public void mark(Player target, int amount) {
        target.getPlayerBoard().addMarks(getOwner(),amount);

    }

    @Override
    public void damage(Player target, int amount) {
        target.getPlayerBoard().addDamages(getOwner(),amount);
        target.addTimesGetDamaged();
    }


    /**
     * @param target is the player moved
     * @param direction is the direction in which the player is moved
     *
     */
    @Override
    public void move(Player target, Square destination)
    {
        target.getPosition().getSquarePlayers().remove(target);
        target.setPosition(destination);
    }
}
