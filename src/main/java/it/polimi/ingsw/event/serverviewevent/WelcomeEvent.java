package it.polimi.ingsw.event.serverviewevent;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

/**
 * This event is handle the client lobby selection;
 *
 * @author Francesco Masciulli
 */
public class WelcomeEvent extends ClientEvent {

    /**
     * Represent the available choices for a client:
     * each client, on connection, could chose between:
     * availableChoice[0] is new lobby and is always true
     * availableChoice[1] is true if exist some lobby that isn't started
     * availableChoice[2] is true if exist some started lobby with at least a disconnected client
     */
    private boolean[] availableChoices;
    /**
     * If presents, the name of each started lobby with a possibility of reconnection
     */
    private ArrayList<String> startedLobbies;
    /**
     * If presents, the name of each lobby waiting for the match start
     */
    private ArrayList<String> waitingLobbies;


    /**
     * Constructor: call super-class constructor and set the attributes
     *
     * @param user             is the connected client username
     * @param availableChoices is the boolean array that contains the possible choices
     * @param waitingLobbies   is the ArrayList with the available waiting lobby
     * @param startedLobbies   is the ArrayList with the available started lobby
     */
    public WelcomeEvent(String user, boolean[] availableChoices, ArrayList<String> waitingLobbies, ArrayList<String> startedLobbies) {
        super(user);
        this.availableChoices = availableChoices;
        this.waitingLobbies = waitingLobbies;
        this.startedLobbies = startedLobbies;
    }


    /**
     * Handle the choice of the Lobby
     *
     * @param remoteView is the Client RemoteView implementation
     * @return a LobbyChoiceEvent with the user's choice
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.welcomeChoice(availableChoices, startedLobbies, waitingLobbies);

    }
}
