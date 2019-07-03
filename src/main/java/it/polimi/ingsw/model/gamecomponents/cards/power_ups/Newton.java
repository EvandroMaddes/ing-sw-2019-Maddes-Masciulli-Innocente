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
 * @author Federico Innocente
 */
public class Newton extends PowerUp {
    private Player targetPlayer;

    public Newton(CubeColour colour) {
        super(colour, "Newton", Usability.AS_ACTION);
        targetPlayer = null;
    }

    @Override
    public void performEffect(Object target) {
        if (targetPlayer == null)
            targetPlayer = (Player) target;
        else {
            move(targetPlayer, (Square) target);
            targetPlayer = null;
        }
    }

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
