package it.polimi.ingsw.event.viewserverevent;

/**
 * The Event handle a new game request
 *
 * @author Francesco Masciulli
 */
public class NewGameChoiceEvent extends ViewServerEvent {

    /**
     * Call its super-class constructor
     *
     * @param user is the client username
     */
    public NewGameChoiceEvent(String user) {
        super(user, true);
    }

    /**
     * the client username is used as part of the new lobby ID
     *
     * @return the new lobby creator's username
     */
    @Override
    public String performAction() {
        return getUser();
    }
}
