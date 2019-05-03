package it.polimi.ingsw.event.view_select;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.action.Action;

import java.util.ArrayList;

public class ActionRequestEvent extends Event {

    private ArrayList<Action> actionsList;

    public ActionRequestEvent(String user, ArrayList<Action> actionsList){
        super(user);
        this.actionsList=actionsList;
    }
}
