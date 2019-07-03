package it.polimi.ingsw.model.gamecomponents.cards;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;


/**
 * Abstract class that is the base for both weapons and powerUps
 *
 * @author Federico Inncente
 */
public abstract class Card implements BaseFightAction {

    /**
     * Is the colour of the card
     */
    private CubeColour colour;
    /**
     * Is the name of the card
     */
    private String name;
    /**
     * Is the owner of the card
     */
    private Player owner;




    /**
     * Constructor
     * @param colour is the colour of the card
     * @param name is the name of the card
     */
    Card(CubeColour colour, String name)
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
     * Getter method
     * @return the colour of the card
     */
    public CubeColour getColour()
    {
        return this.colour;
    }

    /**
     * Getter method
     * @return card owner
     */
    public Player getOwner()
    {
        return owner;
    }

    /**
     * Setter method
     * @param player is the new card owner
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
     *
     */
    @Override
    public void move(Player target, Square destination)
    {
        target.getPosition().getSquarePlayers().remove(target);
        target.setPosition(destination);
    }
}
