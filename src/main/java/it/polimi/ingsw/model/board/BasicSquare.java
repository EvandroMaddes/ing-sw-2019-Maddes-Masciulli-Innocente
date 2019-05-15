package it.polimi.ingsw.model.board;

import it.polimi.ingsw.event.model_view_event.AmmoTileUpdateEvent;
import it.polimi.ingsw.event.model_view_event.AmmoUpdateEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
import it.polimi.ingsw.model.player.Player;

import java.util.Observable;

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
        boolean i=true;
        if(ammo==null)
        {
            i=false;
        }
        else i=true;
        return i;

    }

    /**
     * it calls metod of AmmoTile to grab Ammo
     * @param player who receives the ammo
     */
    public void grabAmmoTile( Player player){

           ammo.pickAmmo(player);

           AmmoTileUpdateEvent message = new AmmoTileUpdateEvent("MapUpdate", false, getColumn(), getRow(), null, null, null);
           notifyObservers(message);
    }

    /**
     * if one square(excepted spawn square) do not have a ammo,it adds one.
     * when replace ammo tiles. notify observers
     *
     * @param ammoTileCard card taht is drawed
     */
    public void replaceAmmoTile(AmmoTile ammoTileCard){
        if(checkAmmo()==false)
           ammo = ammoTileCard;

        AmmoTileUpdateEvent message;
        if (ammo.isPowerUpTile())
            message = new AmmoTileUpdateEvent("MapUpdate", true, getColumn(), getRow(), ammo.getAmmoCubes()[0].getColour().toString(), ammo.getAmmoCubes()[1].getColour().toString(), "POWERUP" );
        else
            message = new AmmoTileUpdateEvent("MapUpdate", true, getColumn(), getRow(), ammo.getAmmoCubes()[0].getColour().toString(), ammo.getAmmoCubes()[1].getColour().toString(), ammo.getAmmoCubes()[2].getColour().toString() );
        notifyObservers(message);

    }

    @Override
    public boolean isGrabbable() {
        if (ammo != null)
            return true;
        else
            return false;
    }
}

