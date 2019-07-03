package it.polimi.ingsw.event.viewserverevent;

/**
 * This Event contains the existing lobby chosen by the user and in which he'll be reconnected
 *
 * @author Francesco Masciulli
 */
public class LobbyChoiceEvent extends ViewServerEvent {
    /**
     * Is the chose Lobby ID
     */
    private String chosenLobby;

    /**
     * Call its super-class constructor and set the chosenLobby value
     *
     * @param user        is the client username
     * @param chosenLobby is the chosen lobby ID
     */
    public LobbyChoiceEvent(String user, String chosenLobby) {
        super(user);
        this.chosenLobby = chosenLobby;
    }

    /**
     * Handle the chose of an existing lobby
     *
     * @return the chosen lobby ID
     */
    @Override
    public String performAction() {
        return chosenLobby;
    }


}
