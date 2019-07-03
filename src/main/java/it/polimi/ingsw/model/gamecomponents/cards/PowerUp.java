package it.polimi.ingsw.model.gamecomponents.cards;

import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

/**
 * This abstract class will be implemented by the game's PowerUp classes
 *
 * @author Federico Innocente
 */
public abstract class PowerUp extends Card {
    /**
     * Enum representing the usability of the powerUp:
     * AS_ACTION: usable as action during the round
     * WHILE_ACTION: usable during an action
     * END_TURN: usable at the end of a round
     */
    public enum Usability {
        AS_ACTION,
        WHILE_ACTION,
        END_TURN;
    }

    /**
     * Usability attribute, set by contructor
     */
    private final Usability usability;

    /**
     * Constructor: call the Card constructor to set colour and name, set usability value
     *
     * @param colour    is the PowerUp colour
     * @param name      is the PowerUp name as String
     * @param usability is the Usability value
     */
    public PowerUp(CubeColour colour, String name, Usability usability) {
        super(colour, name);
        this.usability = usability;
    }

    /**
     * The method will be implemented in each powerUp concrete class
     *
     * @param target is the target of the effect
     */
    public abstract void performEffect(Object target);

    /**
     * Getter method:
     *
     * @return the powerUp's usability
     */
    public Usability whenToUse() {
        return usability;
    }
}
