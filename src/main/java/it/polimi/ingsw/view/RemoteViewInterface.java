package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.model_view_event.EndGameUpdate;
import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;

public interface RemoteViewInterface {

    Event getToVirtualView();
    void setToVirtualView(Event toServer);
    void toVirtualView();
    void fromVirtualView();
    String[] gameInit();

   /**
    * this method handle the UsernameModification events
    * @return the message returning to the server
    */
   void printScreen();
   void setGame(int mapNumber);
   boolean isGameSet();

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
   Event shotMoveChoiceEvent(int[] possibleSquareX, int[] possibleSquareY);
   Event weaponTargetChoice(ArrayList<Character> availableTargets,int numTarget);
   Event weaponEffectChoice(boolean[] availableWeaponEffects);
   Event weaponEffectPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest);
   Event weaponEffectSquareChoice(int[] possibleSquareX, int[] possibleSquareY);
   Event powerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours);
   Event newtonTargetChoice(ArrayList<Character> availableTargets, int maxTarget);
   Event newtonTeleporterTargetSquareChoice(int[] possibleSquareX, int[] possibleSquareY);
   Event genericPaymentChoice( boolean[] usableAmmo, String[] powerUpsType, CubeColour[] powerUpsColour);
   Event endRoundPowerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours, int maxUsablePowerUps);
   Event targetingScopeTargetChoice(ArrayList<Character> possibleTargets);
   Event whileActionPowerUpRequestEvent( String[] powerUpNames, CubeColour[] powerUpColours);

   //following methods manage UpdateEvent
   Event newPlayerJoinedUpdate(String newPlayer,Character characterChoice);
   Event addAmmoTileUpdate(int x, int y,String fistColour,String secondColour, String thirdColour);
   Event removeAmmoTileUpdate(int x, int y);
   Event positionUpdate(Character currCharacter, int x, int y);
   Event playerBoardUpdate(Character character, int skullNumber, Character[] marks, Character[] damages);
   Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, CubeColour[] color);
   Event playerAmmoUpdate(Character currCharacter, ArrayList<AmmoCube> ammo);
   Event playerWeaponUpdate(Character currCharacter, String[] weapons,boolean[] load);
   Event gameTrackSkullUpdate(Character[] killerCharacter,int[] skullNumber);
   Event weaponReplaceUpdate(int x, int y,String[] weapon);
   Event winnerUpdate(EndGameUpdate endGameUpdate);
   Event playerReconnectionNotify(String user, Character character, boolean disconnected);

   //folliwing method manage server-view even
   Event welcomeChoice(boolean[] available, ArrayList<String> startedLobbies,ArrayList<String> waitingLobbies);
   Event printUserNotification(UsernameModificationEvent usernameEvent);



}
