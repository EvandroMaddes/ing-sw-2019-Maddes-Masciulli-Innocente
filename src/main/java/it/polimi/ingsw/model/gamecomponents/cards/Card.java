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
     * Is the Card colour
     */
    private CubeColour colour;
    /**
     * Is the card name, identified by a String
     */
    private String name;
    /**
     * Is the Player that owns the Card, null if it's on the ground
     */
    private Player owner;


    /**
     * Constructor set the name and the colour with the given values, set owner to null
     *
     * @param colour is the colour of the card
     * @param name   is the name of the card
     */
    public Card(CubeColour colour, String name) {
        this.name = name;
        this.colour = colour;
        this.owner = null;
    }

    /**
     * Getter method
     *
     * @return the card's name
     */
    public String getName() {
        return name;
    }


    /**
     * Getter method
     *
     * @return the colour of the card
     */
    public CubeColour getColour() {
        return this.colour;
    }

    /**
     * Getter method
     *
     * @return the card owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Setter method: set the owner
     *
     * @param player is the Player who grab the card
     */
    public void setOwner(Player player) {
        this.owner = player;
    }


    /**
     * Implements the BaseFightAction mark method, calling the PlayerBoard addMarks method
     *
     * @param target is the target Player
     * @param amount is the DamageToken amount
     */
    @Override
    public void mark(Player target, int amount) {
        target.getPlayerBoard().addMarks(getOwner(), amount);

    }

    /**
     * Implements the BaseFightAction damage method, calling the PlayerBoard addDamages method
     * and modifying the target Player's times that was damaged, in case of death
     *
     * @param target is the target Player
     * @param amount is the DamageToken amount
     */
    @Override
    public void damage(Player target, int amount) {
        target.getPlayerBoard().addDamages(getOwner(), amount);
        target.addTimesGetDamaged();
    }


    /**
     * Implements the BaseFightAction move method, calling the Player and Square relatives methods
     *
     * @param target      is the player moved
     * @param destination is the destination Square
     */
    @Override
    public void move(Player target, Square destination) {
        target.getPosition().getSquarePlayers().remove(target);
        target.setPosition(destination);
    }
}
