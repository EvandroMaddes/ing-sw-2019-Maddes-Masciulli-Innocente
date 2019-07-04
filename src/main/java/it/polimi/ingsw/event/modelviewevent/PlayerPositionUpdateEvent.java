package it.polimi.ingsw.event.modelviewevent;


import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * represent the updatePlayer updated Position
 *
 * @author Francesco Masciulli
 * @author Federico Innocente
 */
public class PlayerPositionUpdateEvent extends PositionUpdateEvent {

    /**
     * Is the character that update his position
     */
    private Character character;

    /**
     * Constructor
     *
     * @param positionX his next position X coordinate
     * @param positionY his next position Y coordinate
     */
    public PlayerPositionUpdateEvent(Character character, int positionX, int positionY) {
        super(positionX, positionY);
        this.character = character;
    }

    /**
     * performAction implementation: handle the change of a player position
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.positionUpdate(character, getPositionX(), getPositionY());
    }
}
