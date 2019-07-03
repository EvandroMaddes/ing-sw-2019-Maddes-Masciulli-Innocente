package it.polimi.ingsw.model.gamecomponents.cards;


import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.player.Player;

/**
 * BaseFightAction interface, implemented by the different cards
 */
public interface BaseFightAction {
    /**
     * Damage a target Player of the amount number of token
     *
     * @param target is the target Player
     * @param amount is the DamageToken amount
     */
    public void damage(Player target, int amount);

    /**
     * Move a target Player to the destination Square
     *
     * @param target      is the target Player
     * @param destination is the destination Square
     */
    public void move(Player target, Square destination);

    /**
     * Mark a target Player of the amount number of token
     *
     * @param target is the target Player
     * @param amount is the DamageToken amount
     */
    public void mark(Player target, int amount);

}
