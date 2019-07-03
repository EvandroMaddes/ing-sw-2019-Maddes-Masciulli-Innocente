package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.viewcontrollerevent.UpdateChoiceEvent;
import it.polimi.ingsw.view.RemoteView;

public class ReconnectionMapUpdate extends ModelViewBroadcastEvent {
    private int mapNumber;

    public ReconnectionMapUpdate(int mapNumber) {
        this.mapNumber = mapNumber;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        remoteView.setGame(mapNumber);
        return new UpdateChoiceEvent("BROADCAST");
    }
}
