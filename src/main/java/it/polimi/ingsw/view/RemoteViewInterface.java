package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Character;

import javax.xml.stream.XMLEventWriter;
import java.util.ArrayList;
import java.util.Map;

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

   //following methods manages Request event
   Event gameChoice();
   Event characterChoice(ArrayList<Character> availableCharacters);
   Event actionChoice(boolean fireEnable);
   Event reloadChoice(ArrayList<String> reloadableWeapons);
   Event respawnChoice(String[] powerUpNames, CubeColour[] powerUpColours);
   Event positionMoveChoice(int[] possibleSquareX, int[] possibleSquareY);
   Event positionGrabChoice(int[] possibleSquareX, int[] possibleSquareY);
   Event weaponChoice(ArrayList<String> availableWeapons);//to fire
   Event weaponGrabChoice(ArrayList<String> availableWeapon);
   Event weaponDiscardChoice(ArrayList<String> yourWeapon);
   Event weaponGrabPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest);
   Event weaponReloadPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest);
   Event weaponEffectChoice(boolean[] availableWeaponEffects);
   Event weaponTargetChoice(ArrayList<Character> availableTargets,int numTarget);
   Event weaponEffectPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest);
   Event weaponEffectSquareChoice(int[] possibleSquareX, int[] possibleSquareY);
   Event newtonTargetChoice(ArrayList<Character> availableTargets, int maxTarget);
   Event newtonTeleporterTargetSquareChoice(int[] possibleSquareX, int[] possibleSquareY);
   Event genericPaymentChoice( boolean[] usableAmmo, String[] powerUpsType, CubeColour[] powerUpsColour);
   Event endRoundPowerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours, int maxUsablePowerUps);
   Event targetingScopeTargetChoice(ArrayList<Character> possibleTargets);
   //following methods manage UpdateEvent
   Event newPlayerJoinedUpdate(String newPlayer);
   Event addAmmoTileUpdate(int x, int y,String fistColour,String secondColour, String thirdColour);
   Event removeAmmoTileUpdate(int x, int y);
   Event positionUpdate(Character currCharacter, int x, int y);
   Event playerBoardUpdate(Character currCharacter, Character hittingCharacter, int damageToken, int markNumber);
   Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, CubeColour[] color);
   Event playerAmmoUpdate(Character currCharacter, ArrayList<AmmoCube> ammo);
   Event playerWeaponUpdate(Character currCharacter, String[] weapons);
   Event gameTrackSkullUpdate(Character[] killerCharacter,int[] skullNumber);
   Event weaponReplaceUpdate(int x, int y,String[] weapon);

   //todo fare per ultimo Event targetPowerUpChoice();
   Event powerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours);



}
