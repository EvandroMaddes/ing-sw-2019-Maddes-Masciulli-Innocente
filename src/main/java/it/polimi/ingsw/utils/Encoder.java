package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.model.gamecomponents.cards.Weapon;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to encode in a lite version the object of the model to send them to the client threw the network
 *
 * @author Federico Innocente
 */
public class Encoder {

    private Encoder() {
    }

    /**
     * Encode a list of player in a list of their character
     *
     * @param targets is the list of the players to encode
     * @return a list of all the players character
     */
    public static ArrayList<Character> encodePlayerTargets(List<Player> targets) {
        ArrayList<Character> targetsLite = new ArrayList<>();
        for (Player p : targets) {
            targetsLite.add(p.getCharacter());
        }
        return targetsLite;
    }

    /**
     * Encode a list of square into an array of their row
     *
     * @param targets is a list of squares
     * @return an array of all the squares row
     */
    public static int[] encodeSquareTargetsX(List<Square> targets) {
        int[] targetsX = new int[targets.size()];
        for (int i = 0; i < targets.size(); i++) {
            targetsX[i] = targets.get(i).getRow();
        }
        return targetsX;
    }

    /**
     * Encode a list of square into an array of their column
     *
     * @param targets is a list of squares
     * @return an array of all the squares column
     */
    public static int[] encodeSquareTargetsY(List<Square> targets) {
        int[] targetsY = new int[targets.size()];
        for (int i = 0; i < targets.size(); i++) {
            targetsY[i] = targets.get(i).getColumn();
        }
        return targetsY;
    }

    /**
     * Encode a list of weapons in a list of their names (String)
     *
     * @param weapons is the list of weapon to decode
     * @return a list of all the weapons name
     */
    public static ArrayList<String> encodeWeaponsList(List<Weapon> weapons) {
        ArrayList<String> weaponsLite = new ArrayList<>();
        for (Weapon w : weapons) {
            weaponsLite.add(w.getName());
        }
        return weaponsLite;
    }

    /**
     * Encode a list of weapons in aa array of their names (String)
     *
     * @param weapons is the list of weapon to decode
     * @return an array of all the weapons name
     */
    public static String[] encodeWeaponsIntoArray(List<Weapon> weapons) {
        String[] weaponsLite = new String[weapons.size()];
        for (int i = 0; i < weapons.size(); i++) {
            weaponsLite[i] = weapons.get(i).getName();
        }
        return weaponsLite;
    }

    /**
     * Encode a list of powerUps in an array of their type (String)
     *
     * @param powerUps il the list of powerUps to encode
     * @return is teh array of encoded powerUps type
     */
    public static String[] encodePowerUpsType(List<PowerUp> powerUps) {
        String[] powerUpsType = new String[powerUps.size()];
        for (int i = 0; i < powerUps.size(); i++) {
            powerUpsType[i] = powerUps.get(i).getName();
        }
        return powerUpsType;
    }

    /**
     * Encode a list of powerUps in an array of their colour (CubeColour)
     *
     * @param powerUps il the list of powerUps to encode
     * @return is teh array of encoded powerUps colour
     */
    public static CubeColour[] encodePowerUpColour(List<PowerUp> powerUps) {
        CubeColour[] powerUpsColour = new CubeColour[powerUps.size()];
        for (int i = 0; i < powerUps.size(); i++) {
            powerUpsColour[i] = powerUps.get(i).getColour();
        }
        return powerUpsColour;
    }

    /**
     * Encode a damage token list into an array of the respective character
     *
     * @param damageTokensList is the list of damage tokens
     * @return the array of tokens character
     */
    public static Character[] encodeDamageTokenList(List<DamageToken> damageTokensList) {
        Character[] marksLite = new Character[damageTokensList.size()];
        for (int i = 0; i < damageTokensList.size(); i++) {
            marksLite[i] = damageTokensList.get(i).getPlayer().getCharacter();
        }
        return marksLite;
    }

    /**
     * Encode a damage token array into an array of the respective character
     *
     * @param damages         is the array of damage tokens
     * @param numberOfDamages is the number of damage tocken into the array
     * @return the array of tokens character
     */
    public static Character[] encodeDamagesTokenArray(DamageToken[] damages, int numberOfDamages) {
        Character[] damagesLite = new Character[numberOfDamages];
        for (int i = 0; i < numberOfDamages; i++) {
            damagesLite[i] = damages[i].getPlayer().getCharacter();
        }
        return damagesLite;
    }
}
