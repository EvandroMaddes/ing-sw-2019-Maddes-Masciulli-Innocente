package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

/**
 * @author federicoinnocente
 */
public abstract class PowerUp extends Card {

    public enum Usability{
        AS_ACTION,
        DURING_ACTION,
        END_TURN;
    }

    public PowerUp(CubeColour colour, String name) {
        super(colour, name);
    }

    public abstract void performEffect(Object target);

    public abstract Usability whenToUse();
}
