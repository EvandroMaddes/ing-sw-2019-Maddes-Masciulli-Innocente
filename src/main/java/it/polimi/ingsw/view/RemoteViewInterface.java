package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;

public interface RemoteViewInterface {

    Event getToVirtualView();
    void setToVirtualView(Event toServer);
    void toVirtualView();
    void fromVirtualView();
    String[] gameInit();

    /**
     * this method print the gameScreen after each ModelUpdate from Server
     */
   void printScreen();

   Event gameChoice();
   Event characterChoice(ArrayList<Character> availableCharacters);
   //todo devono rimandare SkipActionChoice se non fa niente
   Event actionChoice(boolean fireEnable);
   Event reloadChoice(ArrayList<String> reloadableWeapons);

   Event respawnChoice(ArrayList<String> powerUpNames, ArrayList<CubeColour> powerUpColours);
   Event positionMoveChoice(int[] possibleSquareX, int[] possibleSquareY);
   Event positionGrabChoice(int[] possibleSquareX, int[] possibleSquareY);
   Event weaponChoice(ArrayList<String> availableWeapons);//to fire
   Event weaponGrabChoice(ArrayList<String> availableWeapon);
   Event weaponEffectChoice(boolean[] availableWeaponEffects);
   Event weaponTargetChoice(ArrayList<Character> availableTargets,int numTarget);
   Event weaponDiscardChoice(ArrayList<String> yourWeapon);
   Event weaponEffectSquareChoice(int[] possibleSquareX, int[] possibleSquareY);

   //following methods manage UpdateEvent
   Event newPlayerJoinedUpdate(String newPlayer);
   Event addAmmoTileUpdate(int x, int y,String fistColour,String secondColour, String thirdColour);
   Event removeAmmoTileUpdate(int x, int y);

   //todo fare per ultimi
   Event effectPaymentChoice();
   Event targetPowerUpChoice();
   Event powerUpChoice(ArrayList<String> powerUpNames, ArrayList<CubeColour> powerUpColours);

   //todo magari per position comune
   void positionChoice();


}
