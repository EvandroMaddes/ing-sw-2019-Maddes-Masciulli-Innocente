package it.polimi.ingsw.event.controllerviewevent;

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
        return remoteView.newtonTargetChoice(getPossibleTargets(),getMaxTarget());
    }
}
