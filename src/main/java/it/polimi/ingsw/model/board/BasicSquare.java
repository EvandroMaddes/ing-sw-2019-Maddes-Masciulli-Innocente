package it.polimi.ingsw.model.board;

import it.polimi.ingsw.event.model_view_event.AmmoTileUpdateEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
import it.polimi.ingsw.model.player.Player;

/**
 * @author Evandro Maddes
 * Class represents tha square tnat contains an ammo card
 */
public class BasicSquare extends Square {

    private AmmoTile ammo;

    /**
     * constructor
     * @param row
     * @param column
     */
    public BasicSquare(int row, int column ){
        super(row,column);
    }

    /**
     * Getter method
     * @return the ammo AmmoTile
     */
    public AmmoTile getAmmo() {
        return ammo;
    }

    /**
     * it checks if on the square there is an ammo or not
     * @return
     */
    public boolean checkAmmo(){
        return ammo != null;
    }

    /**
     * it calls metod of AmmoTile to grab Ammo
     * @param player who receives the ammo
     */
    public void grabAmmoTile( Player player){

           ammo.pickAmmo(player);
           ammo = null;//remove ammo after it is grabbed

           AmmoTileUpdateEvent message = new AmmoTileUpdateEvent(false, getColumn(), getRow(), null, null, null);
           notifyObservers(message);
    }

    /**
     * 
     * @param ammo
     */
    public void setAmmo(AmmoTile ammo) {
        this.ammo = ammo;
    }

    /**
     * if one square(excepted spawn square) do not have a ammo,it adds one.
     * when replace ammo tiles. notify observers
     *
     * @param ammoTileCard card taht is drawed
     */
    public void replaceAmmoTile(AmmoTile ammoTileCard){
        if(!checkAmmo())
           ammo = ammoTileCard;

        AmmoTileUpdateEvent message;
        if (ammo.isPowerUpTile())
            message = new AmmoTileUpdateEvent(true, getColumn(), getRow(), ammo.getAmmoCubes()[0].getColour().toString(), ammo.getAmmoCubes()[1].getColour().toString(), "POWERUP" );
        else
            message = new AmmoTileUpdateEvent(true, getColumn(), getRow(), ammo.getAmmoCubes()[0].getColour().toString(), ammo.getAmmoCubes()[1].getColour().toString(), ammo.getAmmoCubes()[2].getColour().toString() );
        notifyObservers(message);//send to VirtualView
    }

    @Override
    public boolean isGrabbable(Player grabber) {
        return checkAmmo();
    }
}

