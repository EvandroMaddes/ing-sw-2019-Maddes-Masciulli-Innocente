package it.polimi.ingsw.event.modelviewevent;


import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * Message to notify the change of the weapon on a spawn square
 *
 * @author Francesco Masciulli
 * @author Federico Innocente
 * @author Evandro Maddes
 */
public class WeaponUpdateEvent extends PositionUpdateEvent {

    /**
     * Is the list of the new weapons names
     */
    private String[] weapon;

    /**
     * Constructor
     *
     * @param squareX the square X coordinate that must be updated
     * @param squareY the square Y coordinate that must be updated
     * @param weapon  the new weapon based on the ground
     */
    public WeaponUpdateEvent(int squareX, int squareY, String[] weapon) {
        super(squareX, squareY);
        this.weapon = weapon;
    }

    /**
     * PerformAction implementation: handle the update of the weapons on a spawn square
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {

        return remoteView.weaponReplaceUpdate(getPositionX(), getPositionY(), weapon);
    }
}
