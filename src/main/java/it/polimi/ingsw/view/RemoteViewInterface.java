package it.polimi.ingsw.view;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.modelviewevent.EndGameUpdate;
import it.polimi.ingsw.event.serverviewevent.UsernameModificationEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;

/**
 * It menages interaction with user
 */
public interface RemoteViewInterface {

    /**
     * it catches user choices for username,connection(RMI/SOCKET) and IP address
     *
     * @return user choices
     */
    String[] gameInit();

    /**
     * it shows game board updated: map, player boards and game track
     */
    void printScreen();

    /**
     * setter
     *
     * @param mapNumber is the map chosen
     */
    void setGame(int mapNumber);

    /**
     * It checks if game is ready to start(display ready)
     *
     * @return game set or not
     */
    boolean isGameSet();

    //+ + + + + following methods manages Request event + + + + +//

    /**
     * User choice for map
     *
     * @return event that contains player's choice
     */
    Event gameChoice();

    /**
     * user choice for character
     *
     * @param availableCharacters character available
     * @return event that contains player's choice
     */
    Event characterChoice(ArrayList<Character> availableCharacters);

    /**
     * User chooses an action at the beginning of his own turn
     *
     * @param fireEnable true if player can use action fire
     * @return event that contains player's choice
     */
    Event actionChoice(boolean fireEnable);

    /**
     * user chooses weapon to reload
     *
     * @param reloadableWeapons list of weapon that can be reloaded
     * @return event that contains player's choice
     */
    Event reloadChoice(ArrayList<String> reloadableWeapons);

    /**
     * User chooses which powerUp discard
     *
     * @param powerUpNames   list of powerUp
     * @param powerUpColours color of powerUp
     * @return event that contains player's choice
     */
    Event respawnChoice(String[] powerUpNames, CubeColour[] powerUpColours);

    /**
     * user chooses where move his character
     *
     * @param possibleSquareX column
     * @param possibleSquareY row
     * @return event that contains player's choice
     */
    Event positionMoveChoice(int[] possibleSquareX, int[] possibleSquareY);

    /**
     * User chooses where move his character and grab item on new position
     *
     * @param possibleSquareX column
     * @param possibleSquareY row
     * @return event that contains player's choice
     */
    Event positionGrabChoice(int[] possibleSquareX, int[] possibleSquareY);

    /**
     * User chooses weapon to fire
     *
     * @param availableWeapons weapon loaded and ready to fire
     * @return event that contains player's choice
     */
    Event weaponChoice(ArrayList<String> availableWeapons);//to fire

    /**
     * User selects one weapon to grab
     *
     * @param availableWeapon weapon on spawn square
     * @return event that contains player's choice
     */
    Event weaponGrabChoice(ArrayList<String> availableWeapon);

    /**
     * User chooses one weapon to discard
     *
     * @param yourWeapon player's weapon
     * @return event that contains player's choice
     */
    Event weaponDiscardChoice(ArrayList<String> yourWeapon);

    /**
     * It returns how a player pay the weapon GRAB cost
     *
     * @param powerUpNames          list of power up's name available to use
     * @param powerUpColours        list of power up's colour available to use
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return event that contains player's choice
     */
    Event weaponGrabPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest);

    /**
     * User selects how to reload his weapon: power Up, ammo or both
     *
     * @param powerUpNames          list of power up's name available to use
     * @param powerUpColours        list of power up's colour available to use
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return event that contains player's choice
     */
    Event weaponReloadPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest);

    /**
     * User chooses one destination square for action move
     *
     * @param possibleSquareX row of possible destination square
     * @param possibleSquareY column of possible destination square
     * @return
     */
    Event shotMoveChoiceEvent(int[] possibleSquareX, int[] possibleSquareY);

    /**
     * User select target to hit(characters)
     *
     * @param availableTargets characters that can be hit
     * @param numTarget        number of target
     * @return event that contains player's choice
     */
    Event weaponTargetChoice(ArrayList<Character> availableTargets, int numTarget);

    /**
     * User chooses at least one effect for his own weapon
     *
     * @param availableWeaponEffects effect available for selected weapon
     * @return event that contains player's choice
     */
    Event weaponEffectChoice(boolean[] availableWeaponEffects);

    /**
     * it returns how a player pay the cost of effect selected(weapon effect)
     *
     * @param powerUpNames          list of power up's name available to use
     * @param powerUpColours        list of power up's colour available to use
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return event that contains player's choice
     */
    Event weaponEffectPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest);

    /**
     * User chooses square target for his selected effect
     *
     * @param possibleSquareX column of available square
     * @param possibleSquareY row of available square
     * @return event that contains player's choice
     */
    Event weaponEffectSquareChoice(int[] possibleSquareX, int[] possibleSquareY);

    /**
     * User chooses one powerUp to use
     *
     * @param powerUpNames   name of powerUp available
     * @param powerUpColours color of powerUp available
     * @return event that contains player's choice
     */
    Event powerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours);

    /**
     * User chooses target of his power up
     *
     * @param availableTargets character available to hit
     * @param maxTarget        max number of target
     * @return event that contains player's choice
     */
    Event newtonTargetChoice(ArrayList<Character> availableTargets, int maxTarget);

    /**
     * it selects the destination square of target of newton powerUp
     *
     * @param possibleSquareX column of possible square
     * @param possibleSquareY row of possible square
     * @return event that contains player's choice
     */
    Event newtonTeleporterTargetSquareChoice(int[] possibleSquareX, int[] possibleSquareY);

    /**
     * it shows how to pay when player can choose one item from ammo or powerUp
     *
     * @param usableAmmo     available ammo
     * @param powerUpsType   list of power up's name available to use
     * @param powerUpsColour list of power up's colour available to use
     * @return event that contains player's choice
     */
    Event genericPaymentChoice(boolean[] usableAmmo, String[] powerUpsType, CubeColour[] powerUpsColour);

    /**
     * It shows the possibility to use one power up at the end of turn(no your turn)
     *
     * @param powerUpNames      list of  power up's name available to use
     * @param powerUpColours    list of power up's colour available to use
     * @param maxUsablePowerUps max powerUp that you can use
     * @return event that contains player's choice
     */
    Event endRoundPowerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours, int maxUsablePowerUps);

    /**
     * User chooses a target character
     *
     * @param possibleTargets character available to hit
     * @return event that contains player's choice
     */
    Event targetingScopeTargetChoice(ArrayList<Character> possibleTargets);

    /**
     * User chooses to use one of his power up during an action
     *
     * @param powerUpNames   available power up
     * @param powerUpColours colour of power up
     * @return event that contains player's choice
     */
    Event whileActionPowerUpRequestEvent(String[] powerUpNames, CubeColour[] powerUpColours);

    // + + + + + following methods manage UpdateEvent + + + + +//

    /**
     * Every time one player joins the game it's notified to other player
     *
     * @param newPlayer       new player username
     * @param characterChoice new player character choose
     * @return message notify the success of updating
     */
    Event newPlayerJoinedUpdate(String newPlayer, Character characterChoice);

    /**
     * It updates map by adding an ammo tile
     *
     * @param x            row
     * @param y            column
     * @param fistColour   ammo cube
     * @param secondColour ammo cube
     * @param thirdColour  ammo cube or power up
     * @return message notify the success of updating
     */
    Event addAmmoTileUpdate(int x, int y, String fistColour, String secondColour, String thirdColour);

    /**
     * It updates map by deleting an ammo tile
     *
     * @param x row
     * @param y column
     * @return message notify the success of updating
     */
    Event removeAmmoTileUpdate(int x, int y);

    /**
     * it sets character on a new position on map
     *
     * @param currCharacter modified player position
     * @param x             row: if sets at 404, player must be removed
     * @param y             column: if sets at 404, players must be removed
     * @return message notify the success of updating
     */
    Event positionUpdate(Character currCharacter, int x, int y);

    /**
     * Modification of player Board(damage, mark, skull)
     *
     * @param character   modified player board
     * @param skullNumber number of skull
     * @param marks       number of marks and who did it
     * @param damages     number of damage and who did it
     * @return message notify the success of updating
     */
    Event playerBoardUpdate(Character character, int skullNumber, Character[] marks, Character[] damages);

    /**
     * It shows power up available
     *
     * @param currCharacter modified player board
     * @param powerUp       power up of player
     * @param color         colour of power up
     * @return message notify the success of updating
     */
    Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, CubeColour[] color);

    /**
     * It shows ammo available
     *
     * @param currCharacter modified player board
     * @param ammo          ammo of player
     * @return message notify the success of updating
     */
    Event playerAmmoUpdate(Character currCharacter, ArrayList<AmmoCube> ammo);

    /**
     * It shows weapon available
     *
     * @param currCharacter modified player board
     * @param weapons       weapons of player
     * @return message notify the success of updating
     */
    Event playerWeaponUpdate(Character currCharacter, String[] weapons, boolean[] load);

    /**
     * It updates number of skull on the game track
     *
     * @param killerCharacter player who takes the skull
     * @param skullNumber     number of skull left:
     *                        0-one skull;
     *                        1-one damage;
     *                        2-two damage;
     * @return message notify the success of updating
     */
    Event gameTrackSkullUpdate(Character[] killerCharacter, int[] skullNumber);

    /**
     * It replaces weapons on spawn square
     *
     * @param x      coordinate x (row)
     * @param y      coordinate y (column)
     * @param weapon weapon to add
     * @return message notify the success of updating
     */

    Event weaponReplaceUpdate(int x, int y, String[] weapon);

    /**
     * It shows winner of the game and his point
     *
     * @param endGameUpdate
     */
    // TODO: 03/07/2019 fra
    Event winnerUpdate(EndGameUpdate endGameUpdate);

    /**
     * Print a reconnection or disconnection (depending on disconnected value) update notifying the user and character disconnected
     *
     * @param user         is the involved user
     * @param character    is the involved user's character
     * @param disconnected is true if the user was disconnected, false if was reconnected
     * @return event that contains player's choice
     */
    Event playerReconnectionNotify(String user, Character character, boolean disconnected);

    // + + + + +folliwing method manage server-view even + + + + +//

    /**
     * User chooses which game would like to join
     *
     * @param available      : availableChoice[0] = new game
     *                       : availableChoice[1] = wait lobby
     *                       : availableChoice[2] = started game
     * @param startedLobbies Name of started game
     * @param waitingLobbies Games are going to begin
     * @return event that contains player's choice
     */
    Event welcomeChoice(boolean[] available, ArrayList<String> startedLobbies, ArrayList<String> waitingLobbies);

    /**
     * it prints on screen the UpdateNotification message, requesting a choice if necessary;
     *
     * @param usernameEvent notify modification of a user name
     * @return the answer that will be sent to the server.
     */
    Event printUserNotification(UsernameModificationEvent usernameEvent);


}
