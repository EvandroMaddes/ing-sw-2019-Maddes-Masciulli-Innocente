package it.polimi.ingsw.model.board;

import it.polimi.ingsw.event.model_view_event.WeaponUpdateEvent;
import it.polimi.ingsw.model.game_components.cards.Card;
import it.polimi.ingsw.model.game_components.cards.Weapon;
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

     /**
      * @author Francesco Masciulli
      * it checks if the discard card and spawn square have the same colour, comparing the Colour's String
      * @param discardCard is the Player's discarded Card;
      * @return isSameColour the result of comparison.
      */
     public boolean compareColour(Card discardCard) {
          return (this.getSquareColour().equals(discardCard.getColour().toString()));
     }

     /**
      * todo check metodo:
      * After one player picks-up a weapon, this method replaces it
      * @param weaponCard card that is drawn, must be NOT NULL
      */
     public void replaceWeapon(Weapon weaponCard)
     {

          try {
                    weapons.add(weaponCard);


          }
          catch (NullPointerException e)
          {

               weapons.remove(null);
          }
          notifyView();
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
          if (!weapons.isEmpty())
               return false;
          for (Weapon w: weapons){
               if (grabber.canAffortCost(w.getGrabCost()))
                    return true;
          }
          return false;
     }

     private void notifyView(){
          WeaponUpdateEvent message = new WeaponUpdateEvent(getColumn(), getRow(), Encoder.encodeWeaponsIntoArray(weapons));
          notifyObservers(message);
     }
}
