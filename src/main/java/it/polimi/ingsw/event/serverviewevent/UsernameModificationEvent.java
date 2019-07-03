package it.polimi.ingsw.event.serverviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.view.RemoteView;

public class UsernameModificationEvent extends ServerClientEvent{
    private String newUser;
    public UsernameModificationEvent(String user, String newUser) {
        super(user);
        this.newUser = newUser;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    @Override
    public Event performAction(ClientInterface clientImplementation, RemoteView remoteView) {
        Event returnedEvent = remoteView.printUserNotification(this);
        clientImplementation.changeUsername(getUser(), getNewUser());
        remoteView.setUser(newUser);
        return returnedEvent;

    }

}
