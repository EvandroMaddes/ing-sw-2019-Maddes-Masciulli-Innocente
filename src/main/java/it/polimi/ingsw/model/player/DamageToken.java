package it.polimi.ingsw.model.player;

import java.io.Serializable;

/**
 * This class represent a player's damage token
 */
public class DamageToken implements Serializable {

    /**
     * Is the player represented by the token, it couldn't be changed
     */
    private final Player player;

    /**
     * Constructor: set the final value of player attribute
     *
     * @param player is the represented Player
     */
    public DamageToken(Player player) {
        this.player = player;
    }

    /**
     * Getter method:
     *
     * @return the represented player
     */
    public Player getPlayer() {
        return player;
    }
}
