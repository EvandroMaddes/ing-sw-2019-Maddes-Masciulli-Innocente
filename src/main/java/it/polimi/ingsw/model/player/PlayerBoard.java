package it.polimi.ingsw.model.player;

import it.polimi.ingsw.event.modelviewevent.PlayerBoardUpdateEvent;
import it.polimi.ingsw.utils.Encoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

/**
 * Class to manage the player board
 *
 * @author Federico Innocente
 */
public class PlayerBoard extends Observable implements Serializable {

    /**
     * The max number of damages that a player could take before his death, following the game rules is final and set to 12
     */
    private static final int MAX_DAMAGE = 12;
    /**
     * The max number of mark that a player could take from another player, following the game rules is final and set to 3
     */
    private static final int MAX_MARKS = 3;
    /**
     * This array contains the points that a player could earn killing the owner of this PlayerBoard,
     * following the game rules is final and set as done; after each death of the player, the lefter element is skipped
     */
    protected static final int[] POINTS = new int[]{8, 6, 4, 2, 1, 1};
    /**
     * An array containing the DamageTokens that represent all of the damages set on the playerBoard and the players that inflicted them
     */
    private DamageToken[] damageReceived;
    /**
     * Is the number of DamageToken set, that represent the damage inflicted to the player
     */
    private int damageAmount;
    /**
     * Is the number of skulls on the PlayerBoard, is incremented after ever player death
     */
    private int skullsNumber;
    /**
     * An ArrayList that contains all of the marks inflicted by each player to the owner of the PlayerBoard
     */
    private ArrayList<DamageToken> marks;
    /**
     * Is the PlayerBoard owner's character
     */
    private Character character;

    /**
     * Constructor: initialize the damageReceived, damageAmount, skullsNumber and marks attributes;
     * set the character with the given one
     *
     * @param character is the character chosen by the player that own the PlayerBoard
     */
    PlayerBoard(Character character) {
        damageReceived = new DamageToken[11];
        damageAmount = 0;
        skullsNumber = 0;
        marks = new ArrayList<>();
        this.character = character;
    }

    /**
     * Getter method:
     *
     * @return the damageAmount number
     */
    public int getDamageAmount() {
        return damageAmount;
    }

    /**
     * Getter method:
     *
     * @return the ArrayList that contains the marks DamageTokens
     */
    public ArrayList<DamageToken> getMarks() {
        return marks;
    }

    /**
     * Getter method
     *
     * @return the array that contains the damages DamageTokens
     */
    public DamageToken[] getDamageReceived() {
        return damageReceived;
    }

    /**
     * Getter method:
     *
     * @return the skullsNumber value
     */
    public int getSkullsNumber() {
        return skullsNumber;
    }


    /**
     * This method notifies the VirtualViews with a PlayerBoardUpdateEvent
     */
    private void notifyViews() {
        PlayerBoardUpdateEvent message = new PlayerBoardUpdateEvent(character, skullsNumber, Encoder.encodeDamageTokenList(marks), Encoder.encodeDamagesTokenArray(damageReceived, damageAmount));
        setChanged();
        notifyObservers(message);
    }


    /**
     * Give a skull to a player (Max 6 for each PlayerBoard) and notifies the change
     */
    public void addSkull() {
        if (skullsNumber < 6) {
            skullsNumber += 1;
        }
        notifyViews();
    }

    /**
     * Add the given numberOfDamage amount of token that represents the given player to the PlayerBoard
     *
     * @param player         is the the enemy who damage the player
     * @param numberOfDamage is the number of damage tokens
     */
    public void addDamages(Player player, int numberOfDamage) {
        while (damageAmount < MAX_DAMAGE - 1 && numberOfDamage > 0) {
            damageAmount++;
            damageReceived[damageAmount - 1] = new DamageToken(player);
            numberOfDamage--;
        }
        inflictMarks(player);
        notifyViews();
    }

    /**
     * Clear the damage received by a player, setting it to 0
     */
    public void resetDamages() {
        damageAmount = 0;
        notifyViews();
    }


    /**
     * Remove the marks from the list and add them into the damage receved
     *
     * @param player is the player which marks we have to add
     */
    void inflictMarks(Player player) {
        Iterator iterator = marks.iterator();
        DamageToken mark;

        while (iterator.hasNext()) {
            mark = (DamageToken) iterator.next();

            if (mark.getPlayer() == player) {
                if (damageAmount < 11) {
                    damageAmount++;
                    damageReceived[damageAmount - 1] = mark;
                }
                iterator.remove();
            }
        }
        notifyViews();
    }


    /**
     * Add the marks given by player. The marks that a player has can't be more than MAX_MARKS (=3)
     *
     * @param player        is the player who place the marks
     * @param numberOfMarks is the number of marks given
     */
    public void addMarks(Player player, int numberOfMarks) {
        int previousMarks = checkNumberOfMarks(player);

        if (previousMarks + numberOfMarks > MAX_MARKS) {
            numberOfMarks = MAX_MARKS - previousMarks;
        }
        for (int i = 0; i < numberOfMarks; i++) {
            this.marks.add(new DamageToken(player));
        }
        notifyViews();
    }


    /**
     * count the number of marks inflicted by a player
     *
     * @param player is the player of whom we want to count the marks
     * @return the number of marks inflict by player
     */
    int checkNumberOfMarks(Player player) {
        Iterator iterator = marks.iterator();
        DamageToken mark;
        int numberOfMarks = 0;

        while (iterator.hasNext()) {
            mark = (DamageToken) iterator.next();
            if (mark.getPlayer() == player)
                numberOfMarks++;
        }

        return numberOfMarks;
    }

    /**
     * Check if, as explained in the game rules, the player could use the Adrenalinic grab action
     *
     * @return is true if damageAmount is greater than 2
     */
    boolean checkAdrenalinicGrab() {
        return damageAmount > 2;
    }

    /**
     * Check if, as explained in the game rules, the player could use the Adrenalinic shot action
     *
     * @return true if damageAmount is greater than 5
     */
    boolean checkAdrenalinicShot() {
        return damageAmount > 5;
    }

    /**
     * Check the player adrenalinic state
     * @return 2 for teh adrenalinic shot, 1 for adrenalinic grab, 0 for not adrenalinic state
     */
    public int getAdrenalinicState() {
        if (checkAdrenalinicShot())
            return 2;
        else if (checkAdrenalinicGrab())
            return 1;
        else
            return 0;
    }

    /**
     * In the final frenzy phase, if the player has 0 damage, set the score board of the player at 2-1-1;
     * is done setting the skulls number to 3, so the check on POINTS array will create the indicated number of points
     */
    void setFinalFrenzyScoreBoard() {
        if (damageAmount == 0) {
            skullsNumber = 3;
        }
    }


}
