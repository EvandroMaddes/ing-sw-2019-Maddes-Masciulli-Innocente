package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.viewcontrollerevent.UpdateChoiceEvent;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message to send all the information about the map to a reconnected player
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 * @author Francesco Masciulli
 */
public class ReconnectionMapUpdate extends ModelViewBroadcastEvent {
    /**
     * Is the number of the map
     */
    private int mapNumber;

    /**
     * Setter method
     * @param mapNumber set the map number
     */
    public ReconnectionMapUpdate(int mapNumber) {
        this.mapNumber = mapNumber;
    }

    /**
     * PerformAction implementation: handle the player reconnection graphic update
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        remoteView.setGame(mapNumber);
        return new UpdateChoiceEvent("BROADCAST");
    }
}
