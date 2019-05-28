package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class TargetPlayerRequestEvent extends ControllerViewEvent {
    private ArrayList<Character> possibleTargets;
    // maxTarget = -1 mean no target needed, the effect will be manage by itself if he can be used
    private int maxTarget;


    public TargetPlayerRequestEvent(String user, ArrayList<Character> possibleTargets, int maxTarget) {
        super(user);
        this.possibleTargets = possibleTargets;
        this.maxTarget = maxTarget;
    }

    public ArrayList<Character> getPossibleTargets() {
        return possibleTargets;
    }

    public void setPossibleTargets(ArrayList<Character> possibleTargets) {
        this.possibleTargets = possibleTargets;
    }

    public int getMaxTarget() {
        return maxTarget;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.weaponTargetChoice(getPossibleTargets(),getMaxTarget());
    }
}
