package it.polimi.ingsw.event.view_server_event;

import it.polimi.ingsw.network.server.WaitServer;

public class NewGameChoiceEvent extends ViewServerEvent {

    public NewGameChoiceEvent(String user) {
        super(user);
    }

    @Override
    public void performAction(WaitServer server) {
        //todo crea una nuova partita
    }
}
