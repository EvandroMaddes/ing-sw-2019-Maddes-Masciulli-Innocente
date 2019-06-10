package it.polimi.ingsw.model.game_components.cards.power_ups;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.event.controller_view_event.NewtonTargetSquareRequestEvent;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;

/**
 * @author Federico Innocente
 */
public class Newton extends PowerUp {
    private Player targetPlayer;

    public Newton(CubeColour colour) {
        super(colour, "Newton");
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
        for (int direction = 0; direction < 4; direction++) {
            if (targetPlayer.getPosition().checkDirection(direction)) {
                possibleDestination.add(targetPlayer.getPosition().getNextSquare(direction));
                if (targetPlayer.getPosition().getNextSquare(direction).checkDirection(direction))
                    possibleDestination.add(targetPlayer.getPosition().getNextSquare(direction).getNextSquare(direction));
            }
        }
        return new NewtonTargetSquareRequestEvent(getOwner().getUsername(), Encoder.encodeSquareTargetsX(possibleDestination), Encoder.encodeSquareTargetsY(possibleDestination));
    }

    @Override
    public Usability whenToUse() {
        return Usability.AS_ACTION;
    }

}
