package it.polimi.ingsw.event.model_view_event;

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
     * @param user the Client user
     * @param squareX the square X coordinate that must be updated
     * @param squareY the square Y coordinate that must be updated
     * @param firstColour   the first ammo colour
     * @param secondColour the second ammo colour
     * @param thirdColour   the third ammo colour OR must be setted  "POWERUP"
     */
    public AmmoTileUpdateEvent(String user, boolean replace, int squareX, int squareY, String firstColour, String secondColour, String thirdColour){
        super(user, squareX,squareY);
        this.firstColour=firstColour;
        this.secondColour=secondColour;
        this.thirdColour=thirdColour;
        this.replace = replace;
    }
}
