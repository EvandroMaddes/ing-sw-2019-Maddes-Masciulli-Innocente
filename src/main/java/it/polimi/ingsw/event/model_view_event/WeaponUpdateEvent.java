package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.model.board.Square;

public class WeaponUpdateEvent extends PositionUpdateEvent {
    private String weapon;

    /**
     * Constructor
     * @param user the Client user
     * @param mapUpdate must be "MAPUPDATE"
     * @param square the square that must be updated
     * @param weapon the new weapon based on the ground
     */
    public WeaponUpdateEvent(String user, String mapUpdate, Square square, String weapon){
        super(user, mapUpdate, square);
        this.weapon=weapon;
    }
}
