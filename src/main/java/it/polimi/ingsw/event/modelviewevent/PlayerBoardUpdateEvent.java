package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * @author Francesco Masciulli
 * represent the PlayerBoard Update after a damage, a mark or a death
 */
public class PlayerBoardUpdateEvent extends ModelViewBroadcastEvent {

    private int skullNumber;
    private Character[] marks;
    private Character[] damages;
    private Character playerBoardCharacter;

    public PlayerBoardUpdateEvent(Character playerBoardCharacter, int skullNumber, Character[] marks, Character[] damages) {
        this.skullNumber = skullNumber;
        this.playerBoardCharacter = playerBoardCharacter;
        this.marks = marks;
        this.damages = damages;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.playerBoardUpdate(playerBoardCharacter,skullNumber,marks,damages);
    }
}
