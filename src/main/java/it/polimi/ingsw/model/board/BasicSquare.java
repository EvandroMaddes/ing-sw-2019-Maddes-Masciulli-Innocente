package it.polimi.ingsw.model.board;

import it.polimi.ingsw.event.modelviewevent.AmmoTileUpdateEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoTile;
import it.polimi.ingsw.model.player.Player;

/**
 * Class represents tha square that contains an ammo card
 * @author Evandro Maddes
 * @author Federico Innocente
 */
public class BasicSquare extends Square {

    /**
     * Is the BasicSquare AmmoTile
     */
    private AmmoTile ammo;

    /**
     * Constructor: call the Square Constructor to set row and column
     * @param row is the Square row on the Map squares matrix
     * @param column is the Square column on the Map squares matrix
     */
    public BasicSquare(int row, int column ){
        super(row,column);
    }

    /**
     * Square method implementation: call the checkAmmo method;
     * @param grabber is the player that would grab from the Square
     * @return true if there is an ammo to grab
     */
    @Override
    public boolean isGrabbable(Player grabber) {
        return checkAmmo();
    }

    /**
     * Getter method
     * @return the ammo AmmoTile
     */
    public AmmoTile getAmmo() {
        return ammo;
    }


    /**
     * Setter method set the ammo attribute
     * @param ammo is the AmmoTile that must be set
     */
    public void setAmmo(AmmoTile ammo) {
        this.ammo = ammo;
    }

    /**
     * it checks if on the square there is an ammo or not
     * @return true if the ammo isn't set to null, false in the other case
     */
    public boolean checkAmmo(){
        return ammo != null;
    }

    /**
     * it calls method from AmmoTile to grab an Ammo
     * @param player is the Player who grabs the ammo
     */
    public void grabAmmoTile( Player player){

           ammo.pickAmmo(player);
           ammo = null;//remove ammo after it is grabbed

           AmmoTileUpdateEvent message = new AmmoTileUpdateEvent(false, getColumn(), getRow(), null, null, null);
           setChanged();
           notifyObservers(message);
    }

    /**
     * if the BasicSquare doesn't have a ammo,it adds one;
     * when an AmmoTile is replaced, notifies the observers
     * @param ammoTileCard card that was drawn from AmmoTileDeck
     */
    public void replaceAmmoTile(AmmoTile ammoTileCard){
        if(!checkAmmo()) {
            ammo = ammoTileCard;

            AmmoTileUpdateEvent message;
            if (ammo.isPowerUpTile())
                message = new AmmoTileUpdateEvent(true, getColumn(), getRow(), ammo.getAmmoCubes()[0].getColour().toString(), ammo.getAmmoCubes()[1].getColour().toString(), "POWERUP");
            else
                message = new AmmoTileUpdateEvent(true, getColumn(), getRow(), ammo.getAmmoCubes()[0].getColour().toString(), ammo.getAmmoCubes()[1].getColour().toString(), ammo.getAmmoCubes()[2].getColour().toString());
            setChanged();
            notifyObservers(message);
        }
    }


}

