package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
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
        // TODO: 2019-06-04
        return null;
    }
}
