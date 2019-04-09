package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;

import java.util.ArrayList;

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
      * If one player picks-up a weapon, this metod replaces it or leaves empty(only if there are non more weapons)
      */
     public void replaceWeapon()
     {

     }

     /**
      *
      */
     public void removeWeapon()
     {

     }

}
