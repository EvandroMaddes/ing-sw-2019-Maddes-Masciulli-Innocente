package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

/**
 * @author Francesco Masciulli
 * it represent an Ammo Tile replacement on the Map
 */
public class AmmoTileUpdateEvent extends PositionUpdateEvent {
    private boolean replace;
    private String firstColour;
    private String secondColour;
    private String thirdColour;

    /**
     * Constructor
     * @param squareX the square X coordinate that must be updated
     * @param squareY the square Y coordinate that must be updated
     * @param firstColour   the first ammo colour
     * @param secondColour the second ammo colour
     * @param thirdColour   the third ammo colour OR must be setted  "POWERUP"
     */
    public AmmoTileUpdateEvent(boolean replace, int squareX, int squareY, String firstColour, String secondColour, String thirdColour){
        super("BROADCAST", squareX,squareY);
        this.firstColour=firstColour;
        this.secondColour=secondColour;
        this.thirdColour=thirdColour;
        this.replace = replace;
    }


    @Override
    public Event performAction(RemoteView remoteView) {
        if (replace) {
            return remoteView.addAmmoTileUpdate(getPositionX(), getPositionY(), firstColour, secondColour, thirdColour);
        }
        else {
            return remoteView.removeAmmoTileUpdate(getPositionX(),getPositionY());
        }
    }
}
