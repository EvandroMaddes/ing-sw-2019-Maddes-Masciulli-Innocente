package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;

/**
 * @author federicoinnocente
 */
public abstract class PowerUp extends Card {
    public enum Usability{
        AS_ACTION,
        WHILE_ACTION,
        END_TURN;
    }

    private final Usability usability;

    public PowerUp(CubeColour colour, String name, Usability usability) {
        super(colour, name);
        this.usability = usability;
    }

    public abstract void performEffect(Object target);

    public Usability whenToUse(){
        return usability;
    }
}
