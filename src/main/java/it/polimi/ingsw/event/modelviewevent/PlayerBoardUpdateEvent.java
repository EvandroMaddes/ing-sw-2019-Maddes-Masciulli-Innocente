package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

/**
 * represent the PlayerBoard Update after a damage, a mark or a death
 * @author Francesco Masciulli
 */
public class PlayerBoardUpdateEvent extends ModelViewBroadcastEvent {
    /**
     * Is the number of skull on the playerBoard
     */
    private int skullNumber;
    /**
     * Array that represent the marks of each other player, identified by the Character, on the playerBoard
     */
    private Character[] marks;
    /**
     * Array that represent the damages of each other player, identified by the Character, on the playerBoard
     */
    private Character[] damages;
    /**
     * The playerBoard owner, represented by his character
     */
    private Character playerBoardCharacter;

    /**
     * Constructor: call the super-class constructor and set the attributes value
     * @param playerBoardCharacter is the playerBoard's owner character
     * @param skullNumber is the number of skull on the playerBoard
     * @param marks is the set Character array for the marks
     * @param damages is the set Character array for the damages
     */
    public PlayerBoardUpdateEvent(Character playerBoardCharacter, int skullNumber, Character[] marks, Character[] damages) {
        this.skullNumber = skullNumber;
        this.playerBoardCharacter = playerBoardCharacter;
        this.marks = marks;
        this.damages = damages;
    }

    /**
     * PerformAction implementations: handle the playerBoard update
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        return remoteView.playerBoardUpdate(playerBoardCharacter,skullNumber,marks,damages);
    }
}
