package it.polimi.ingsw.model.gamecomponents.cards.power_ups;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.event.controllerviewevent.NewtonTargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;

/**
 * Newton PowerUp implementation
 *
 * @author Federico Innocente
 */
public class Newton extends PowerUp {
    /**
     * is the target player on which will'be used the effect
     */
    private Player targetPlayer;

    /**
     * Constructor: set the colour value and usability value, reset the targetPlayer setting it to null
     *
     * @param colour is the PowerUp CubeColour value
     */
    public Newton(CubeColour colour) {
        super(colour, "Newton", Usability.AS_ACTION);
        targetPlayer = null;
    }

    /**
     * Implements PowerUp method: set a chosen Player target or, if already chosen, the target Square where will be moved
     *
     * @param target is the target of the effect (a Player or a Square)
     */
    @Override
    public void performEffect(Object target) {
        if (targetPlayer == null)
            targetPlayer = (Player) target;
        else {
            move(targetPlayer, (Square) target);
            targetPlayer = null;
        }
    }

    /**
     * Get the possible destination of the targetPlayer after the Newton usage
     *
     * @returna NewtonTargetRequestEvent containing the possible Squares positions
     */
    public ControllerViewEvent getTargets() {
        ArrayList<Square> possibleDestination = new ArrayList<>();
        possibleDestination.add(targetPlayer.getPosition());
        for (int direction = 0; direction < 4; direction++) {
            if (targetPlayer.getPosition().checkDirection(direction)) {
                possibleDestination.add(targetPlayer.getPosition().getNextSquare(direction));
                if (targetPlayer.getPosition().getNextSquare(direction).checkDirection(direction))
                    possibleDestination.add(targetPlayer.getPosition().getNextSquare(direction).getNextSquare(direction));
            }
        }
        return new NewtonTargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination));
    }
}
