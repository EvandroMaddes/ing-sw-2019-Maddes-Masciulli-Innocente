package it.polimi.ingsw.model.board;

import it.polimi.ingsw.event.modelviewevent.WeaponUpdateEvent;
import it.polimi.ingsw.model.gamecomponents.cards.Weapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;

/**
 * @author Evandro Maddes
 *
 */
public class SpawnSquare extends Square {
     private ArrayList<Weapon> weapons = new ArrayList<>();

     /**
      * Constructor of a single square
      * @param row
      * @param column

      */
     public SpawnSquare(int row, int column){
          super( row,  column);
     }

     /**
      *
      * @return weapons present at the spawn square
      */
     public ArrayList<Weapon> getWeapons()
     {
          return weapons;
     }

     public void grabWeapon(Weapon weaponSelected, Player player){
          player.addWeapon(weaponSelected);
          weapons.remove(weaponSelected);
          notifyView();
     }

     @Override
     /**
      * return true if there is at least 1 weapon that the player can buy
      */
     public boolean isGrabbable(Player grabber) {
          if (weapons.isEmpty())
               return false;
          for (Weapon w: weapons){
               if (grabber.canAffortCost(w.getGrabCost()))
                    return true;
          }
          return false;
     }

     private void notifyView(){
          WeaponUpdateEvent message = new WeaponUpdateEvent(getColumn(), getRow(), Encoder.encodeWeaponsIntoArray(weapons));
          setChanged();
          notifyObservers(message);
     }

     public void addWeapon(ArrayList<Weapon> weapons){
          this.weapons.addAll(weapons);
          notifyView();
     }
}
