package it.polimi.ingsw.model.board;

import it.polimi.ingsw.event.modelviewevent.WeaponUpdateEvent;
import it.polimi.ingsw.model.gamecomponents.cards.Weapon;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Encoder;

import java.util.ArrayList;

/**
 * Class that implements the SpawnSquares
 * @author Evandro Maddes
 * @author Federico Innocente
 */
public class SpawnSquare extends Square {
     /**
      * An ArrayList that contains the Weapons on the SpawnSquare
      */
     private ArrayList<Weapon> weapons = new ArrayList<>();

     /**
      * Constructor: call the super-class constructor
      * @param row is the Square row
      * @param column is the Square column

      */
     public SpawnSquare(int row, int column){
          super( row,  column);
     }

     /**
      * Getter method:
      * @return the weapons on the spawn square
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
      * Implements isGrabbable method from Square
      * return true if there is at least 1 weapon that the player could take
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

     /**
      * Method that notifies the VirtualViews when a weapon is modified
      */
     private void notifyView(){
          WeaponUpdateEvent message = new WeaponUpdateEvent(getColumn(), getRow(), Encoder.encodeWeaponsIntoArray(weapons));
          setChanged();
          notifyObservers(message);
     }

     /**
      * Method that add some weapons and notifies the VirtualViews
      * @param weapons are the weapons that must be added
      */
     public void addWeapon(ArrayList<Weapon> weapons){
          this.weapons.addAll(weapons);
          notifyView();
     }
}
