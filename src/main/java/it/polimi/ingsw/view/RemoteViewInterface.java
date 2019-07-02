package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.model_view_event.EndGameUpdate;
import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import java.util.ArrayList;

/**
 * It menages interaction with user
 */
public interface RemoteViewInterface {

    /**
    * It prints title "ADRENALINE" and catch user choice for username and connection(RMI/SOCKET)
    * @return user choices
    */
    String[] gameInit();

   /**
    * Print screen gameboard updated: map, playerboards and gametrack
    */
   void printScreen();

   /**
    * setter
    * @param mapNumber is the map chosen
    */
   void setGame(int mapNumber);

   /**
    * It checks if game is ready to start(display ready)
    * @return game set or not
    */
   boolean isGameSet();

   //+ + + + + following methods manages Request event + + + + +//
   /**
    * user choice for map a
    * @return event that contains player's choice
    */
   Event gameChoice();

   /**
    * user choice for character
    * @param availableCharacters character available
    * @return event that contains player's choice
    */
   Event characterChoice(ArrayList<Character> availableCharacters);

   /**
    *User choose an action at the beginning of his own turn
    * @param fireEnable true if player can use action fire
    * @return event that contains player's choice
    */
   Event actionChoice(boolean fireEnable);

   /**
    * user choose weapon to reload
    * @param reloadableWeapons list of weapon that can be reloaded
    * @return event that contains player's choice
    */
   Event reloadChoice(ArrayList<String> reloadableWeapons);

   /**
    * User choose which powerUp discard
    * @param powerUpNames list of powerUp
    * @param powerUpColours color of powerUp
    * @return event that contains player's choice
    */
   Event respawnChoice(String[] powerUpNames, CubeColour[] powerUpColours);

   /**
    * user choose where move his character
    * @param possibleSquareX column
    * @param possibleSquareY row
    * @return event that contains player's choice
    */
   Event positionMoveChoice(int[] possibleSquareX, int[] possibleSquareY);

   /**
    *User choose where move his character and grab item on new position
    * @param possibleSquareX column
    * @param possibleSquareY row
    * @return event that contains player's choice
    */
   Event positionGrabChoice(int[] possibleSquareX, int[] possibleSquareY);

   /**
    * User choose weapon to fire
    * @param availableWeapons weapon loaded and ready to fire
    * @return event that contains player's choice
    */
   Event weaponChoice(ArrayList<String> availableWeapons);//to fire

   /**
    * User select one weapon to grab
    * @param availableWeapon weapon on spawn square
    * @return event that contains player's choice
    */
   Event weaponGrabChoice(ArrayList<String> availableWeapon);

   /**
    * User choose one weapon to discard
    * @param yourWeapon player's weapon
    * @return event that contains player's choice
    */
   Event weaponDiscardChoice(ArrayList<String> yourWeapon);

   /**
    * It return how a player pay the weapon GRAB cost
    * @param powerUpNames list of power up's name available to use
    * @param powerUpColours list of power up's colour available to use
    * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
    * @param maximumPowerUpRequest max number of power up you can use
    * @return event that contains player's choice
    */
   Event weaponGrabPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest);

   /**
    * User selects how to reload his weapon: power Up, ammo or both
    * @param powerUpNames list of power up's name available to use
    * @param powerUpColours list of power up's colour available to use
    * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
    * @param maximumPowerUpRequest max number of power up you can use
    * @return event that contains player's choice
    */
   Event weaponReloadPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest);

   /**
    *User choice one square for action move
    * @param possibleSquareX row of possible destination square
    * @param possibleSquareY column of possible destination square
    * @return
    */
   Event shotMoveChoiceEvent(int[] possibleSquareX, int[] possibleSquareY);

   /**
    * User select target to hit(characters)
    * @param availableTargets characters that can be hit
    * @return event that contains player's choice
    */
   Event weaponTargetChoice(ArrayList<Character> availableTargets,int numTarget);

   /**
    * User choose at least one effect for his own weapon
    * @param availableWeaponEffects effect available for selected weapon
    * @return event that contains player's choice
    */
   Event weaponEffectChoice(boolean[] availableWeaponEffects);

   /**
    * it return how a player pay the cost of effect selected(weapon effect)
    * @param powerUpNames list of power up's name available to use
    * @param powerUpColours list of power up's colour available to use
    * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
    * @param maximumPowerUpRequest max number of power up you can use
    * @return event that contains player's choice
    */
   Event weaponEffectPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest);

   /**
    *User choose square target for his selected effect
    * @param possibleSquareX column of available square
    * @param possibleSquareY row of available square
    * @return event that contains player's choice
    */
   Event weaponEffectSquareChoice(int[] possibleSquareX, int[] possibleSquareY);

   /**
    *User choose one powerUp to use
    * @param powerUpNames name of powerUp available
    * @param powerUpColours color of powerUp available
    * @return event that contains player's choice
    */
   Event powerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours);

   /**
    * User choose target of his power up
    * @param availableTargets character available to hit
    * @param maxTarget max number of target
    * @return event that contains player's choice
    */
   Event newtonTargetChoice(ArrayList<Character> availableTargets, int maxTarget);

   /**
    * it selects the destination square of target or newton powerUp
    * @param possibleSquareX column of possible square
    * @param possibleSquareY row of possible square
    * @return event that contains player's choice
    */
   Event newtonTeleporterTargetSquareChoice(int[] possibleSquareX, int[] possibleSquareY);

   /**
    * it shows how to pay when player can choose one item from ammo or powerUp
    * @param usableAmmo available ammo
    * @param powerUpsType list of power up's name available to use
    * @param powerUpsColour list of power up's colour available to use
    * @return event that contains player's choice
    */
   Event genericPaymentChoice( boolean[] usableAmmo, String[] powerUpsType, CubeColour[] powerUpsColour);

   /**
    * It show the possibility to use one power up at the end of turn(no yor turn)
    * @param powerUpNames list of  power up's name available to use
    * @param powerUpColours list of power up's colour available to use
    * @param maxUsablePowerUps max powerUp that you can use
    * @return event that contains player's choice
    */
   Event endRoundPowerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours, int maxUsablePowerUps);

   /**
    * User choice a target character
    * @param possibleTargets character available to hit
    * @return event that contains player's choice
    */
   Event targetingScopeTargetChoice(ArrayList<Character> possibleTargets);

   /**
    * User choose to use one of his power up during an action
    * @param powerUpNames available power up
    * @param powerUpColours colour of power up
    * @return event that contains player's choice
    */
   Event whileActionPowerUpRequestEvent( String[] powerUpNames, CubeColour[] powerUpColours);

   // + + + + + following methods manage UpdateEvent + + + + +//
   /**
    * Every time one player joins the game it's notified to other player
    * @param newPlayer  new player username
    * @param characterChoice new player character choose
    * @return message notify the success of updating
    */
   Event newPlayerJoinedUpdate(String newPlayer,Character characterChoice);

   /**
    * It updates map by adding an ammo tile
    * @param x row
    * @param y column
    * @param fistColour ammo cube
    * @param secondColour ammo cube
    * @param thirdColour ammo cube or power up
    * @return message notify the success of updating
    */
   Event addAmmoTileUpdate(int x, int y,String fistColour,String secondColour, String thirdColour);

   /**
    * It updates map by deleting an ammo tile
    * @param x row
    * @param y column
    * @return message notify the success of updating
    */
   Event removeAmmoTileUpdate(int x, int y);

   /**
    * @param currCharacter modified player position
    * @param x row: if sets at 404, player must be removed
    * @param y column: if sets at 404, players must be removed
    * @return message notify the success of updating
    */
   Event positionUpdate(Character currCharacter, int x, int y);

   /**
    * Modification of player Board(damage, mark, skull)
    * @param character modified player board
    * @param skullNumber number of skull
    * @param marks number of marks and who did it
    * @param damages number of damage and who did it
    * @return message notify the success of updating
    */
   Event playerBoardUpdate(Character character, int skullNumber, Character[] marks, Character[] damages);

   /**
    *It shows power up available on player board
    * @param currCharacter  modified player board
    * @param powerUp power up of player
    * @param color colour of power up
    * @return message notify the success of updating
    */
   Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, CubeColour[] color);

   /**
    *It shows ammo available on player board
    * @param currCharacter  modified player board
    * @param ammo ammo of player
    * @return message notify the success of updating
    */
   Event playerAmmoUpdate(Character currCharacter, ArrayList<AmmoCube> ammo);

   /**
    *It shows weapon available on player board
    * @param currCharacter  modified player board
    * @param weapons weapons of player
    * @return message notify the success of updating
    */
   Event playerWeaponUpdate(Character currCharacter, String[] weapons,boolean[] load);

   /**
    * It updates number of skull on the game track
    * @param killerCharacter player who takes the skull
    * @param skullNumber number of skull left:
    *                    0-one skull;
    *                    1-one damage;
    *                    2-two damage;
    * @return message notify the success of updating
    */
   Event gameTrackSkullUpdate(Character[] killerCharacter,int[] skullNumber);

   /**
    * It replaces weapons on spawn square
    * @param x      coordinate x (row)
    * @param y      coordinate y (column)
    * @param weapon weapon to add
    * @return message notify the success of updating
    */

   Event weaponReplaceUpdate(int x, int y,String[] weapon);

   /**
    * It shows winner of the game and his point
    * @param endGameUpdate
    */
   // TODO: 02/07/2019 commentare parametro
   Event winnerUpdate(EndGameUpdate endGameUpdate);

   // TODO: 02/07/2019  commenatre metodo
   Event playerReconnectionNotify(String user, Character character, boolean disconnected);

   // + + + + +folliwing method manage server-view even + + + + +//

   /**
    * User choose which game would like to join
    * @param available  : availableChoice[0] = new game
    *                   : availableChoice[1] = wait lobby
    *                   : availableChoice[2] = started game
    * @param startedLobbies Name of started game
    * @param waitingLobbies Games are going to begin
    * @return event that contains player's choice
    */
   Event welcomeChoice(boolean[] available, ArrayList<String> startedLobbies,ArrayList<String> waitingLobbies);
   /**
    * Print on screen the UpdateNotification message, requesting a choice if necessary;
    * @return the answer that will be sent to the server.
    */
   Event printUserNotification(UsernameModificationEvent usernameEvent);



}
