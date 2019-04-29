package it.polimi.ingsw.model.player;

/**
 * @author Federico Innocente
 */

public enum Character {

    D_STRUCT_OR,
    BANSHEE,
    DOZER,
    VIOLET,
    SPROG;

    private Player player;


    /**
     *
     * @return the player associated with the character
     */
    public Player getPlayer()
    {
        return player;
    }
}
