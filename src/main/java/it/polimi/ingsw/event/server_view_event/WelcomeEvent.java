package it.polimi.ingsw.event.server_view_event;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class WelcomeEvent extends ClientEvent {

    private boolean[] availableChoices;
    /**
     * availableChoice[0] = nuova partita
     * availableChoice[1] = partita in attesa di iniziare
     * availableChoice[2] = partita in corso(+username per riconessione)
     *
     */
    private ArrayList<String> startedLobbies;
    private ArrayList<String> waitingLobbies;
    private ArrayList<String> startedLobbiesUsernames;


    public WelcomeEvent(String user, boolean[] availableChoices) {
        super(user);
        this.availableChoices=availableChoices;
    }



    @Override
    public Event performAction(RemoteView remoteView) {
        //todo fa scegliere se -)nuova partita
        //                      -)partita in attesa di iniziare
        //                      -)partita in corso(+username per riconessione)
        // return WelcomeRequestEvent
        return remoteView.welcomeChoice(availableChoices,startedLobbies,waitingLobbies,startedLobbiesUsernames);
    }
}
