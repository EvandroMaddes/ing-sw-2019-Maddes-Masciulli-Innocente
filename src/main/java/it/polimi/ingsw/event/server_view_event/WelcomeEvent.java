package it.polimi.ingsw.event.server_view_event;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class WelcomeEvent extends ClientEvent {

    private boolean[] availableChoices;
    private ArrayList<String> waitingLobbies;
    private ArrayList<String> startedLobbies;
    private ArrayList<String> startedLobbiesUsernames;


    public WelcomeEvent(String user, boolean[] availableChoices, ArrayList<String> waitingLobbies, ArrayList<String> startedLobbies) {
        super(user);
        this.availableChoices=availableChoices;
        this.waitingLobbies = waitingLobbies;
        this.startedLobbies = startedLobbies;
    }


    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.welcomeChoice(availableChoices,startedLobbies,waitingLobbies);

    }
}
