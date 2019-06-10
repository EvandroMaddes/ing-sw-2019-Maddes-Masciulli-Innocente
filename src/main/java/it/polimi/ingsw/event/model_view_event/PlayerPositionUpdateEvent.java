package it.polimi.ingsw.event.model_view_event;


import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * @author Francesco Masciulli
 * represent the updatePlayer updated Position
 */
public class PlayerPositionUpdateEvent extends PositionUpdateEvent {

    private Character character;

    /**
     * Constructor
     * @param positionX  his next position X coordinate
     * @param positionY his next position Y coordinate
     */
    public PlayerPositionUpdateEvent(Character character, int positionX, int positionY){
        super(positionX, positionY);
        this.character = character;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.positionUpdate(character, getPositionX(), getPositionY());
    }
}
