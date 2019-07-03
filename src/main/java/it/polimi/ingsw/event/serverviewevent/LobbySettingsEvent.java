package it.polimi.ingsw.event.serverviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.view.RemoteView;

public class LobbySettingsEvent extends ServerClientEvent {
    private int mapNumber;

    public LobbySettingsEvent(String user, int mapNumber) {
        super(user);
        this.mapNumber = mapNumber;
    }

    public int getMapNumber() {
        return mapNumber;
    }


    @Override
    public Event performAction(ClientInterface clientImplementation, RemoteView remoteView) {
        remoteView.setGame(mapNumber);
        remoteView.printScreen();
        return null;
    }
}
