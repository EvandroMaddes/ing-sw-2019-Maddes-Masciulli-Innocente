package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.PlayerBoard;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

/**
 * @author Francesco Masciulli
 * represent the PlayerBoard Update after a damage, a mark or a death
 */
public class PlayerBoardUpdateEvent extends ModelViewEvent {

    private int skullNumber;
    private Character[] marks;
    private Character[] damages;

    public PlayerBoardUpdateEvent(String user, Character character, int skullNumber, Character[] marks, Character[] damages) {
        super(user);
        this.skullNumber = skullNumber;
        this.marks = marks;
        this.damages = damages;
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        // TODO: 2019-06-08
        //return remoteView.playerBoardUpdate(getHitCharacter(), getHittingCharacter(), getDamageTokenNumber(),getMarkNumber());
        return null;
    }
}
