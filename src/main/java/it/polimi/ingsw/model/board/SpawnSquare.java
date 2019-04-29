package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Card;
import it.polimi.ingsw.model.game_components.cards.Weapon;

import java.util.ArrayList;

/**
 * @author Evandro Maddes
 *
 */
public class SpawnSquare extends Square {
     private ArrayList<Weapon> weapons;

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
     public boolean compareColour(Card discardCard)
     {
         return (discardCard.getColour().toString() == this.getSquareRoom());
     }

     /**
      * After one player picks-up a weapon, this metod replaces it or leaves empty(only if there are non more weapons)
      * @param weaponCard card that is drawed 
      */
     public void replaceWeapon(Weapon weaponCard) throws NullPointerException
     {
          int i=0;
          try {
               while(i<3){
                    weapons.add(weaponCard);

               }
          }
          catch (NullPointerException e)
          {
               weapons.add(null);
          }
     }

     /**
      * @param weaponCard crad that is selected by a player
      */
     public void removeWeapon(Weapon weaponCard)
     {
          weapons.remove(weaponCard);
     }

}
