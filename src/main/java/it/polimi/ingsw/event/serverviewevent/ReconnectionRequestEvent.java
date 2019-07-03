package it.polimi.ingsw.event.serverviewevent;


import java.util.ArrayList;

/**
 * Event that handle a ReconnectionRequest from a client which username that is not registered in-game
 *
 * @author Francesco Masciulli
 */
public class ReconnectionRequestEvent extends UsernameModificationEvent {

    /**
     * An ArrayList that contains all of the disconnected players username
     */
    private ArrayList<String> disconnectedUsers;

    /**
     * Constructor: call the super-class constructor, setting the newName as the user; set also the disconnectedUsers
     *
     * @param user              is the client username
     * @param disconnectedUsers is the List of disconnected in-game users
     */
    public ReconnectionRequestEvent(String user, ArrayList<String> disconnectedUsers) {
        super(user, user);
        this.disconnectedUsers = disconnectedUsers;
    }

    /**
     * Getter method:
     *
     * @return the disconnectedUsers ArrayList
     */
    public ArrayList<String> getDisconnectedUsers() {
        return disconnectedUsers;
    }


}
