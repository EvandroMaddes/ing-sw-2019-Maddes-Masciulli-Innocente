package it.polimi.ingsw.model.player;

import it.polimi.ingsw.event.model_view_event.PlayerBoardUpdateEvent;
import it.polimi.ingsw.utils.Encoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

/**
 * Class to manage the player board
 *
 * @author Federico Innocente
 *
 */
public class PlayerBoard extends Observable implements Serializable{

    private static final int MAX_DAMAGE = 12;
    private static final int MAX_MARKS = 3;
    public static final int[] POINTS = new int[]{ 8,6,4,2,1,1 };

    private DamageToken[] damageReceived;
    private int damageAmount;
    private int skullsNumber;
    private ArrayList<DamageToken> marks;
    private Character character;


    public PlayerBoard(Character character) {
        damageReceived = new DamageToken[11];
        damageAmount = 0;
        skullsNumber = 0;
        marks = new ArrayList<>();
        this.character = character;
    }

    private void notifyViews(){
        PlayerBoardUpdateEvent message = new PlayerBoardUpdateEvent(character, skullsNumber, Encoder.encodeDamageTokenList(marks), Encoder.encodeDamagesTokenArray(damageReceived, damageAmount));
        notifyObservers(message);
    }

    public DamageToken[] getDamageReceived() {
        return damageReceived;
    }

    /**
     *
     * @return skullsNumber
     */
    public int getSkullsNumber()
    {
        return skullsNumber;
    }

    /**
     * Give a skull to a player (Max 6 for each PlayerBoard)
     */
    public void addSkull()
    {
        if(skullsNumber<6) {
            skullsNumber += 1;
        }
        notifyViews();
    }

    /**
     *
     * @param player is the the enemy who damage the player
     * @param numberOfDamage is the number of damage tokens
     *
     */
    public void addDamages( Player player, int numberOfDamage ) {
        while (damageAmount < MAX_DAMAGE - 1 && numberOfDamage > 0) {
            damageAmount++;
            damageReceived[damageAmount - 1] = new DamageToken(player);
            numberOfDamage--;
        }
        inflictMarks(player);
        notifyViews();
    }

    /**
     * Clear the damage receved by a player, setting them to 0
     */
    public void resetDamages()
    {
        damageAmount = 0;
        notifyViews();
    }


    /**
     * Remove the marks from the list and add them into the damage receved
     *
     * @param player is the player which marks we have to add
     *
     */
    public void inflictMarks(Player player)
    {
        Iterator iterator = marks.iterator();
        DamageToken mark;

        while (iterator.hasNext())
        {
            mark = (DamageToken)iterator.next();

            if (mark.getPlayer() == player)
            {
                if (damageAmount < 11)
                {
                    damageAmount++;
                    damageReceived[damageAmount-1] = mark;
                }
                iterator.remove();
            }
        }
        notifyViews();
    }


    /**
     * Add the marks given by player. The marks that a player has can't be more than MAX_MARKS (=3)
     *
     * @param player is the player who place the marks
     * @param numberOfMarks is the number of marks given
     */
    public void addMarks( Player player, int numberOfMarks )
    {
        int previousMarks = checkNumberOfMarks(player);

        if (previousMarks + numberOfMarks > MAX_MARKS)
        {
            numberOfMarks = MAX_MARKS - previousMarks;
        }
        for (int i = 0; i < numberOfMarks; i++)
        {
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
    public int checkNumberOfMarks(Player player)
    {
        Iterator  iterator = marks.iterator();
        DamageToken mark;
        int numberOfMarks = 0;

        while (iterator.hasNext())
        {
            mark = (DamageToken) iterator.next();
            if (mark.getPlayer() == player)
                numberOfMarks++;
        }

        return numberOfMarks;
    }

    /**
     *
     * @return is true if damageAmount is greater than 2
     */
    public boolean checkAdrenalinicGrab()
    {
        return damageAmount > 2;
    }

    /**
     *
     * @return true if damageAmount is greater than 5
     */
    public boolean checkAdrenalinicShot()
    {
        return damageAmount > 5;
    }

    public int getAdrenalinicState(){
        if (checkAdrenalinicShot())
            return 2;
        else if (checkAdrenalinicGrab())
            return 1;
        else
            return 0;
    }

    /**
     * In the final frenzy phase, if the player has 0 damage, set the score board of the player at 2-1-1...
     */
    public void  setFinalFrenzyScoreBoard()
    {
        if(damageAmount == 0)
        {
            skullsNumber = 3;
        }
    }

    public int getDamageAmount() {
        return damageAmount;
    }

    public ArrayList<DamageToken> getMarks() {
        return marks;
    }
}
