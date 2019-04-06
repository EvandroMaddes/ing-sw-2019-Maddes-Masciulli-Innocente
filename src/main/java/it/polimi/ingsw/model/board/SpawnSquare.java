package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.CubeColour;
import it.polimi.ingsw.model.game_components.Weapon;

import java.util.ArrayList;

public class SpawnSquare extends Square {
     private ArrayList<Weapon> weapons;

     public ArrayList<Weapon> getWeapons() {
          return weapons;
     }

     public boolean compareColour(CubeColour choosenColour)
     {
          boolean i=true;
          return i;
     }

     public void replaceWeapon()
     {

     }

     public void removeWeapon()
     {

     }

}
