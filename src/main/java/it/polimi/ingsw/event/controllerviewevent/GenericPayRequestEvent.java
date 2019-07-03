package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.view.RemoteView;

public class GenericPayRequestEvent extends ControllerViewEvent {
    private boolean[] usableAmmo;
    private String[] powerUpsType;
    private CubeColour[] powerUpsColour;

    public GenericPayRequestEvent(String user, boolean[] usableAmmo, String[] powerUpsType, CubeColour[] powerUpsColour) {
        super(user);
        this.usableAmmo = usableAmmo;
        this.powerUpsType = powerUpsType;
        this.powerUpsColour = powerUpsColour;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.genericPaymentChoice(usableAmmo,powerUpsType,powerUpsColour);
    }

    public boolean[] getUsableAmmo() {
        return usableAmmo;
    }

    public String[] getPowerUpsType() {
        return powerUpsType;
    }

    public CubeColour[] getPowerUpsColour() {
        return powerUpsColour;
    }
}
