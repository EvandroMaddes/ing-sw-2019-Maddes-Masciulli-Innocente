package it.polimi.ingsw.event.viewserverevent;

public class LobbyChoiceEvent  extends ViewServerEvent {
    private String chosenLobby;

    public LobbyChoiceEvent(String user, String chosenLobby) {
        super(user, false);
        this.chosenLobby = chosenLobby;
    }
    @Override
    public String performAction(){
        return chosenLobby;
    }


}
