package it.polimi.ingsw.model.game_components.cards.power_ups;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;

import java.security.InvalidParameterException;


/**
 * @author Federico Innocente
 */
public class TargetingScope extends PowerUp {

    public boolean chooseCube;
    public AmmoCube cubeChoice;
    public PowerUp powerUpChoice;


    /**
     *
     * @param cubeChoice is the cube to pay
     */
    public void setCubeChoice(AmmoCube cubeChoice)
    {
        this.cubeChoice = cubeChoice;
        chooseCube = true;
    }

    /**
     *
     * @param powerUpChoice is the powerUp choosen as payment
     * @throws InvalidParameterException if the power up passed is the same of the card
     */
    public void setPowerUpChoice(PowerUp powerUpChoice) throws InvalidParameterException
    {
        if (powerUpChoice == this)
            throw new InvalidParameterException("Non puoi pagare con il power up stesso");
        this.powerUpChoice = powerUpChoice;
        chooseCube = false;
    }

    /**
     *
     * @param colour is the card colour
     */
    public TargetingScope(CubeColour colour)
    {
        super(colour, "TargetingScope");
    }

    /**
     * damage the target
     */
    @Override
    public void useEffect()
    {
        payCost();
        getTarget().getPlayerBoard().addDamages(this.getOwner(), 1);
        super.useEffect();
    }

    /**
     * pay the cost for the effect
     */
    public void payCost()
    {
        if (chooseCube)
            getOwner().getAmmo().remove(cubeChoice);
        else
            getOwner().getPowerUps().remove(powerUpChoice);
    }
}
