package it.polimi.ingsw.event.view_server_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.server.WaitServer;

public class LobbyChoiceEvent  extends ViewServerEvent {
    private String chosenLobby;

    public LobbyChoiceEvent(String user, String chosenLobby) {
        super(user);
        this.chosenLobby = chosenLobby;
    }
    @Override
    public void performAction(WaitServer server){
        //todo gestisce l'inserimento nella Lobby adatta;
    }


}