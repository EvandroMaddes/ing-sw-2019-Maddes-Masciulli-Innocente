package it.polimi.ingsw.event.model_view_event;


import java.util.ArrayList;

/**
 * @author Francesco Masciulli
 * represent a Player Position Update (after a Spawn o Movement)
 */
public class WeaponUpdateEvent extends PositionUpdateEvent {
    private ArrayList<String> weapon;

    /**
     * Constructor
     * @param user the Client user
     * @param mapUpdate must be "MAPUPDATE"
     * @param squareX the square X coordinate that must be updated
     * @param squareY the square Y coordinate that must be updated
     * @param weapon the new weapon based on the ground
     */
    public WeaponUpdateEvent(String user, String mapUpdate, int squareX, int squareY, ArrayList<String> weapon){
        super(user, mapUpdate, squareX, squareY);
        this.weapon=weapon;
    }
}
