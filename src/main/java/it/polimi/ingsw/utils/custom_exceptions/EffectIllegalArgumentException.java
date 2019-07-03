package it.polimi.ingsw.utils.custom_exceptions;

/**
 * Weapon management exception
 * @author Francesco Masciulli
 */
public class EffectIllegalArgumentException extends UnsupportedOperationException {
    /**
     * Constructor:
     * set a custom message
     */
    public EffectIllegalArgumentException() {
        super("The effect doesn't exist");
    }
}
