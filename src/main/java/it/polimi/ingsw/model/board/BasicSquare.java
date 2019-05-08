package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
import it.polimi.ingsw.model.player.Player;

/**
 * @author Evandro Maddes
 * Class represents tha square tnat contains an ammo card
 */
public class BasicSquare extends Square {

    private AmmoTile ammo;

    /**
     * Getter method
     * @return the ammo AmmoTile
     */
    public AmmoTile getAmmo() {
        return ammo;
    }

    /**
     * constructor
     * @param row
     * @param column
     */
    public BasicSquare(int row, int column ){

        super(row,column);

    }
    /**
     *
     * @param ammo
     */
    public void setAmmo(AmmoTile ammo) {
        this.ammo = ammo;
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

    }

    /**
     * if one square(excepted spawn square) do not have a ammo,it adds one.
     *
     * @param ammoTileCard card taht is drawed
     */
    public void replaceAmmoTile(AmmoTile ammoTileCard){
        if(checkAmmo()==false)
        {
           ammo = ammoTileCard;
        }

    }

    @Override
    public boolean isGrabbable() {
        if (ammo != null)
            return true;
        else
            return false;
    }
}

