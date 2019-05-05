package it.polimi.ingsw.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class to manage the player board
 *
 * @author Federico Innocente
 *
 */
public class PlayerBoard implements Serializable {

    private static final int MAX_DAMAGE = 12;
    private static final int MAX_MARKS = 3;
    private static final int points[] = new int[]{ 8,6,4,2,1,1 };

    private DamageToken[] damageReceved;
    private int damageAmount;
    private int skullsNumber;
    private ArrayList<DamageToken> marks;


    /**
     *
     * @return skullsNumber
     */
    public int getSkullsNumber()
    {
        return skullsNumber;
    }

    /**
     * Give a skull to a player
     */
    public void addSkull()
    {
        skullsNumber += 1;
    }

    /**
     *
     * @param player is the the enemy who damage the player
     * @param numberOfDamage is the number of damage tokens
     *
     */
    public void addDamages( Player player, int numberOfDamage )
    {
        while (damageAmount < MAX_DAMAGE && numberOfDamage > 0)
        {
            damageAmount++;
            damageReceved[damageAmount] = new DamageToken(player);
            numberOfDamage--;
        }

        inflictMarks(player);
    }

    /**
     * Clear the damage receved by a player, setting them to 0
     */
    public void resetDamages()
    {
        damageAmount = 0;
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

            if (mark.getCharacter() == player.getCharacter())
            {
                if (damageAmount < 12)
                {
                    damageAmount++;
                    damageReceved[damageAmount] = mark;
                }
                iterator.remove();
            }
        }
    }


    /**
     * Add the marks given by player. The marks that a player has can't be more than MAX_MARKS (=3)
     *
     * @param player is the player who place the marks
     * @param numberOfMarks is the number of marks given
     */
    public void addMarks( Player player, int numberOfMarks )
    {
        int previousMarsk = checkNumberOfMarks(player);

        if (previousMarsk + numberOfMarks > MAX_MARKS)
        {
            numberOfMarks = MAX_MARKS - previousMarsk;
        }
        for (int i = 0; i < numberOfMarks; i++)
        {
            this.marks.add(new DamageToken(player));
        }
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
            if (mark.getCharacter() == player.getCharacter())
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
}
