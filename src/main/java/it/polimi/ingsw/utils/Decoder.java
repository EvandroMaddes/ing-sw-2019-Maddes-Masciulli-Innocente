package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.model.gamecomponents.cards.Weapon;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to decode the lite version of the object coming from the client to the controller
 */
public class Decoder {

    private Decoder() {
    }

    /**
     * Decode a list of powerUps type and the associated powerUps to a list of powerUps, knowing their owner
     *
     * @param powerUpsOwner is the powerUps owner
     * @param powerUpType   are the powerUps type
     * @param powerUpColour are the powerUps colour
     * @return a list of powerUps of the player
     */
    public static ArrayList<PowerUp> decodePowerUpsList(Player powerUpsOwner, String[] powerUpType, CubeColour[] powerUpColour) {
        ArrayList<PowerUp> decodedPowerUps = new ArrayList<>();
        for (int i = 0; i < powerUpType.length; i++) {
            for (PowerUp p : powerUpsOwner.getPowerUps()) {
                if (p.getName().equals(powerUpType[i]) && p.getColour() == powerUpColour[i]) {
                    decodedPowerUps.add(p);
                    powerUpType[i] = "";
                }
            }
        }
        return decodedPowerUps;
    }

    /**
     * Decode a single powerUps, knowing his type, colour and owner
     *
     * @param powerUpOwner  is the powerUp owner
     * @param powerUpType   is the powerUp type
     * @param powerUpColour is the powerUp colour
     * @return the reference to the player's powerUp
     */
    public static PowerUp decodePowerUp(Player powerUpOwner, String powerUpType, CubeColour powerUpColour) {
        for (PowerUp p : powerUpOwner.getPowerUps()) {
            if (p.getName().equals(powerUpType) && p.getColour() == powerUpColour)
                return p;
        }
        throw new UnsupportedOperationException("Something wrong in powerUp decode");
    }

    /**
     * Decode a square from his coordinates
     *
     * @param squareX is the square row
     * @param squareY is teh square column
     * @param map     is the game map
     * @return the decoded square
     */
    public static Square decodeSquare(int squareX, int squareY, Map map) {
        return map.getSquareMatrix()[squareX][squareY];
    }

    /**
     * Decode a list of character into a list of object with the reference of their player
     *
     * @param charactersList is teh list of character to decode
     * @param playerList     is the list of all the players
     * @return a list of player
     */
    public static ArrayList<Object> decodePlayerListAsObject(List<Character> charactersList, List<Player> playerList) {
        ArrayList<Object> decodedList = new ArrayList<>();
        for (Player p : playerList) {
            if (charactersList.contains(p.getCharacter()))
                decodedList.add(p);
        }
        return decodedList;
    }

    /**
     * Decode a player from his character
     *
     * @param character  is the player character
     * @param playerList is a list of all the players in the game
     * @return the player that use the chosen character
     */
    public static Player decodePlayerFromCharacter(Character character, List<Player> playerList) {
        for (Player p : playerList) {
            if (p.getCharacter() == character)
                return p;
        }
        throw new UnsupportedOperationException("Something wrong in player decode");
    }

    /**
     * Decode a player from his username
     *
     * @param username   is the player username
     * @param playerList is a list of all the players in teh game
     * @return the player with the username
     */
    public static Player decodePlayerFromUsername(String username, List<Player> playerList) {
        for (Player p : playerList) {
            if (p.getUsername().equals(username))
                return p;
        }
        throw new UnsupportedOperationException("Something wrong in player decode");
    }

    /**
     * Decode a weapon from it's name knowing its owner
     *
     * @param weaponOwner is the weapon owner
     * @param weaponName  is the weapon name
     * @return the decoded weapon
     */
    public static Weapon decodePlayerWeapon(Player weaponOwner, String weaponName) {
        for (int i = 0; i < weaponOwner.getNumberOfWeapons(); i++) {
            if (weaponOwner.getWeapons()[i].getName().equals(weaponName))
                return weaponOwner.getWeapons()[i];
        }
        throw new UnsupportedOperationException("Player doesn't have the weapon");
    }
}
