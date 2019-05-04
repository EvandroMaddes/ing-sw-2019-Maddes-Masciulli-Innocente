package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.model.board.Square;

/**
 * @author Francesco Masciulli
 * it represent an Ammo Tile replacement on the Map
 */
public class AmmoTileUpdateEvent extends PositionUpdateEvent {
    private String firstColour;
    private String secondColour;
    private String thirdColour;

    /**
     * Constructor
     * @param user the Client user
     * @param mapUpdate must be "MAPUPDATE"
     * @param square the square that must be updated
     * @param firstColour   the first ammo colour
     * @param secondColour the second ammo colour
     * @param thirdColour   the third ammo colour OR must be setted  "POWERUP"
     */
    public AmmoTileUpdateEvent(String user, String mapUpdate, Square square, String firstColour, String secondColour, String thirdColour){
        super(user, mapUpdate, square);
        this.firstColour=firstColour;
        this.secondColour=secondColour;
        this.thirdColour=thirdColour;
    }
}
