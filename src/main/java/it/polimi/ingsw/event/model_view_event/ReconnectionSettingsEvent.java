package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.UpdateChoiceEvent;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class ReconnectionSettingsEvent extends ModelViewEvent {

    private ArrayList<ClientEvent>  reconnectionSettingsEvents =new ArrayList();
    public ReconnectionSettingsEvent(String user) {
        super(user);
    }

    public void addEvent(ClientEvent toAdd){
        reconnectionSettingsEvents.add(toAdd);
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        for (ClientEvent currUpdate: reconnectionSettingsEvents) {
            currUpdate.performAction(remoteView);
        }
        return new UpdateChoiceEvent("BROADCAST");
    }
}
