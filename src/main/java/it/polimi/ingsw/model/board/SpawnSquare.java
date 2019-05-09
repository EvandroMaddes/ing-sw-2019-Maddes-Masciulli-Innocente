package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Card;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Player;

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
     }

     /**
      * @param weaponCard card that is selected by a player
      */
     public void removeWeapon(Weapon weaponCard)
     {
          weapons.remove(weaponCard);
     }

     public void grabWeapon(Weapon weaponSelected, Player player){
          player.addWeapon(weaponSelected);
          removeWeapon(weaponSelected);
     }

     @Override
     /**
      * return true if there is at leat 1 weapon in the square
      */
     public boolean isGrabbable() {
          return !weapons.isEmpty();
     }
}
