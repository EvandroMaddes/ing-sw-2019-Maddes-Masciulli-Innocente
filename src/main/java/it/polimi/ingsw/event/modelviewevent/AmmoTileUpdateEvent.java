package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * it represent an Ammo Tile replacement on the Map
 *
 * @author Francesco Masciulli, Federico Innocente
 */
public class AmmoTileUpdateEvent extends PositionUpdateEvent {
    /**
     * Is true if the tile must be pu on the ground, false if must be cleaned
     */
    private boolean replace;
    /**
     * Is the colour of the first ammo
     */
    private String firstColour;
    /**
     * is the colour of the second ammo
     */
    private String secondColour;
    /**
     * Is the colour of the third ammo, if present, or must be setted to "POWERUP"
     */
    private String thirdColour;

    /**
     * Constructor: set the attributes values
     *
     * @param squareX      the square X coordinate that must be updated
     * @param squareY      the square Y coordinate that must be updated
     * @param firstColour  the first ammo colour
     * @param secondColour the second ammo colour
     * @param thirdColour  the third ammo colour OR must be setted  "POWERUP"
     * @param replace is true if the AmmoTile must be set on the map
     */
    public AmmoTileUpdateEvent(boolean replace, int squareX, int squareY, String firstColour, String secondColour, String thirdColour) {
        super(squareX, squareY);
        this.firstColour = firstColour;
        this.secondColour = secondColour;
        this.thirdColour = thirdColour;
        this.replace = replace;
    }


    /**
     * ClientEvent performAction implementation: handle the AmmoTileUpdate removing o placing it, depending on replace value
     *
     * @param remoteView is the Client RemoteView implementation
     * @return the UpdateChoiceEvent
     */
    @Override
    public Event performAction(RemoteView remoteView) {
        if (replace) {
            return remoteView.addAmmoTileUpdate(getPositionX(), getPositionY(), firstColour, secondColour, thirdColour);
        } else {
            return remoteView.removeAmmoTileUpdate(getPositionX(), getPositionY());
        }
    }
}
