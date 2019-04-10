package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;

import java.util.ArrayList;

/**
 * @author Evandro Maddes
 * @// TODO: 10/04/2019 metodo comparecolour 
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
      * it checks if the discard card and spawn square have the same colour
      * @param choosenColour
      * @return
      */
     public boolean compareColour(CubeColour choosenColour)
     {
          boolean i=true;
          return i;
     }

     /**
      * After one player picks-up a weapon, this metod replaces it or leaves empty(only if there are non more weapons)
      * @param weaponCard card that is drawed 
      */
     public void replaceWeapon(Weapon weaponCard) throws NullPointerException
           {int i=0;
                try {
                     while(weapons.get(i)==null && i<3){
                          weapons.add(weaponCard);
                     }
                }
             catch (NullPointerException){
                     
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
