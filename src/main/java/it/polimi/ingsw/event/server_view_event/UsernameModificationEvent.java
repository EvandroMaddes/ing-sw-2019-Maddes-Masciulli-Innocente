package it.polimi.ingsw.event.server_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.view.RemoteView;

public class UsernameModificationEvent extends Event {
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

    public void performAction(ClientInterface clientImplementation) {
        clientImplementation.changeUsername(getUser(), getNewUser());
    }

}
