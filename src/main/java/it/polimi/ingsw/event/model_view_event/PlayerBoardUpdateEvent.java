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


    private int damageTokenNumber;
    private Character hitCharacter;
    private Character hittingCharacter;
    private int markNumber;

    /**
     *
     * @param user is the client user
     * @param hitCharacter is the owner of the updated playerboard
     * @param hittingCharacter is the Player that make a damage or a mark
     * @param damageTokenNumber is the damage amount
     * @param markNumber is the mark amount
     */
    public PlayerBoardUpdateEvent(String user, Character hitCharacter, Character hittingCharacter, int damageTokenNumber, int markNumber){
        super(user);
        this.damageTokenNumber=damageTokenNumber;
        this.hitCharacter = hitCharacter;
        this.hittingCharacter = hittingCharacter;
        this.markNumber = markNumber;
    }

    public int getDamageTokenNumber() {
        return damageTokenNumber;
    }

    public Character getHitCharacter() {
        return hitCharacter;
    }

    public Character getHittingCharacter() {
        return hittingCharacter;
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

        return remoteView.playerBoardUpdate(getHitCharacter(), getHittingCharacter(), getDamageTokenNumber(),getMarkNumber());
    }
}
