package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Player;

public class  PowerUp extends Card {

    private Player target;

    public PowerUp(CubeColour colour, String name)
    {
        super(colour, name);
    }

    /**
     *
     * @return target
     */
    public Player getTarget()
    {
        return target;
    }

    /**
     *
     * @param target is the target of the effect
     */
    public void setTarget(Player target)
    {
        this.target = target;
    }

    /**
     * Discard the power uop
     * Overrided for every powerup
     */
    public void useEffect()
    {
        this.getOwner().discardPowerUp(this);
    }

}
