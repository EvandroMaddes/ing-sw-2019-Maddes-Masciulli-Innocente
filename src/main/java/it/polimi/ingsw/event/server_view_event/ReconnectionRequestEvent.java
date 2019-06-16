package it.polimi.ingsw.event.server_view_event;



import java.util.ArrayList;


public class ReconnectionRequestEvent extends UsernameModificationEvent {

    private ArrayList<String> disconnectedUsers;

    public ReconnectionRequestEvent(String user, ArrayList<String> disconnectedUsers) {
        super(user,user);
        this.disconnectedUsers = disconnectedUsers;
    }

    public ArrayList<String> getDisconnectedUsers() {
        return disconnectedUsers;
    }



}
