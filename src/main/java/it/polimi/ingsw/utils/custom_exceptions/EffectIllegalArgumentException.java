package it.polimi.ingsw.utils.custom_exceptions;

public class EffectIllegalArgumentException extends UnsupportedOperationException {
    public EffectIllegalArgumentException() {
        super("The effect doesn't exist");
    }
}
