package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.PlayerBoard;
import it.polimi.ingsw.view.RemoteView;

/**
 * @author Francesco Masciulli
 * represent the PlayerBoard Update after a damage, a mark or a death
 */
public class PlayerBoardUpdateEvent extends ModelViewEvent {

    private PlayerBoard playerBoard;

    private int damageTokenNumber;
    private Character hitCharacter;
    private int markNumber;

    /**
     * Constructor
     * @param user the Client user
     * @param damageTokenNumber the number of token that will be placed
     */
    public PlayerBoardUpdateEvent(String user, Character hitCharacter, int damageTokenNumber, int markNumber){
        super(user);
        this.damageTokenNumber=damageTokenNumber;
        this.hitCharacter = hitCharacter;
        this.damageTokenNumber = damageTokenNumber;
        this.markNumber = markNumber;
    }

    public int getDamageTokenNumber() {
        return damageTokenNumber;
    }

    public Character getHitCharacter() {
        return hitCharacter;
    }

    @Override
    public String getUser() {
        return super.getUser();
    }

    public int getMarkNumber() {
        return markNumber;
    }


    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.PlayerBoardUpdate(getHitCharacter(),getDamageTokenNumber(),getMarkNumber());
    }
}
