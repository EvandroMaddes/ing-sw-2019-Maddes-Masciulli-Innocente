package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class EffectPaymentRequest extends PowerUpRequestEvent {

    private int[] number;
    private ArrayList<CubeColour> playerCube;

    public EffectPaymentRequest(String user, ArrayList<String> powerUpNames, ArrayList<CubeColour> powerUpColours,
                                int numberOfRed, int numberOfBlue, int numberOfYellow, ArrayList<CubeColour> playerCube) {
        super(user, powerUpNames, powerUpColours);
        number[0] = numberOfRed;
        number[1] = numberOfBlue;
        number[2] = numberOfYellow;
        this.playerCube = playerCube;
    }

    //todo reimplentare
    @Override
    public Event performAction(RemoteView remoteView) {
        return super.performAction(remoteView);
    }
}
