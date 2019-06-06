package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * @author Francesco Masciulli
 * represent a GameTrack Update, when a player is killed
 *  or  (DominationMod) when a SpawnSquare is damaged
 */
public class GameTrackUpdateEvent extends ModelViewEvent {
    private Character character;
    private Character killerCharacter;
    private int skullNumber;

    public GameTrackUpdateEvent(String user,Character character, int skullNumber, Character killerCharacter) {
        super(user);
        this.killerCharacter = killerCharacter;
        this.character = character;
        this.skullNumber = skullNumber;
    }

    public Character getCharacter() {
        return character;
    }

    public int getSkullNumber() {
        return skullNumber;
    }

    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.gameTrackSkullUpdate(getCharacter(), getSkullNumber(), killerCharacter);
    }
}
