package it.polimi.ingsw.event.serverviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.view.RemoteView;

/**
 * This message handle a username modification request
 * from the server, following the connection of a client that uses an already chosen username
 *
 * @author Francesco Masciulli
 */
public class UsernameModificationEvent extends ServerClientEvent {
    /**
     * Is the new random username
     */
    private String newUser;

    /**
     * Call the ServerClientEvent constructor and set the newUser value
     *
     * @param user    is the registering client username
     * @param newUser is the new random username
     */
    public UsernameModificationEvent(String user, String newUser) {
        super(user);
        this.newUser = newUser;
    }

    /**
     * Getter method:
     *
     * @return the newUser value
     */
    public String getNewUser() {
        return newUser;
    }

    /**
     * Setter method: set newUser value to the one given as arguments
     *
     * @param newUser is the set newUser
     */
    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    /**
     * Implements ServerCLientEvent method: handle the username modification on clientImplementation and remoteView
     *
     * @param clientImplementation is the client ClientInterface implementation
     * @param remoteView           is the client RemoteView Implementation
     * @return the message returned by the remoteView method
     */
    @Override
    public Event performAction(ClientInterface clientImplementation, RemoteView remoteView) {
        Event returnedEvent = remoteView.printUserNotification(this);
        clientImplementation.changeUsername(getUser(), getNewUser());
        remoteView.setUser(newUser);
        return returnedEvent;

    }

}
