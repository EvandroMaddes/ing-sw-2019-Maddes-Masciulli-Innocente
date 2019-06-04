package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class NewtonPlayerTargetRequestEvent extends TargetPlayerRequestEvent {

    public NewtonPlayerTargetRequestEvent(String user, ArrayList<Character> possibleTargets, int maxTarget) {
        super(user, possibleTargets, maxTarget);
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        // TODO: 2019-06-04  
        return null;
    }
}
