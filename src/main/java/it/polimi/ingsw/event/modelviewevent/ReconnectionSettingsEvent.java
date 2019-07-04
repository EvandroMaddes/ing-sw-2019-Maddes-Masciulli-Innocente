package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.viewcontrollerevent.UpdateChoiceEvent;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

/**
 * Message to send all information about the game to a player who reconnected to print it again on his client
 *
 * @author Francesco Masciulli
 */
public class ReconnectionSettingsEvent extends ModelViewEvent {

    /**
     * Is a list of sub message with the information on single components
     */
    private ArrayList<ClientEvent> reconnectionSettingsEvents;

    /**
     * Constructor
     *
     * @param user is the username of the reconnected player
     */
    public ReconnectionSettingsEvent(String user) {
        super(user);
        reconnectionSettingsEvents = new ArrayList<>();
    }

    /**
     * Add a message to update a new components
     *
     * @param toAdd is the added update message
     */
    public void addEvent(ClientEvent toAdd) {
        reconnectionSettingsEvents.add(toAdd);
    }

    /**
     * PerformAction implementation: handle the player reconnection graphic update on a client
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        for (ClientEvent currUpdate : reconnectionSettingsEvents) {
            currUpdate.performAction(remoteView);
        }
        remoteView.printScreen();
        return new UpdateChoiceEvent(getUser());
    }
}
