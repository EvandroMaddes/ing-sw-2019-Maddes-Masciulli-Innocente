package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class TargetingScopeTargetRequestEvent extends ControllerViewEvent {
    private ArrayList<Character> possibleTargets;

    public TargetingScopeTargetRequestEvent(String user, ArrayList<Character> possibleTargets) {
        super(user);
        this.possibleTargets = possibleTargets;
    }

    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.targetingScopeTargetChoice(possibleTargets);
    }

    public ArrayList<Character> getPossibleTargets() {
        return possibleTargets;
    }
}
