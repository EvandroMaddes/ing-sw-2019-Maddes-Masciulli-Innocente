package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class WeaponEffectRequest extends ControllerViewEvent {

    private boolean[] availableEffect;
    public WeaponEffectRequest(String user, boolean[] availableEffect) {
        super(user);
        this.availableEffect=availableEffect;
    }

    public boolean[] getAvailableEffect() {
        return availableEffect;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.weaponEffectChoice(getAvailableEffect());
    }
}
