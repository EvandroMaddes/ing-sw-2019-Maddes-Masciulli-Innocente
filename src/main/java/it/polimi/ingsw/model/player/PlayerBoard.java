package it.polimi.ingsw.model.player;

import java.util.ArrayList;

public class PlayerBoard {

    private DamageToken[] damageReceved;
    private int skullsNumber;
    private ArrayList<DamageToken> marks;
    final static int points[] = new int[]{ 8,6,4,2,1,1 };

    public void addDamages( Player player, int numberOfDamage )
    {

    }

    public void addMarks( Player player, int numberOfMarks )
    {

    }

    public int getSkullsNumber()
    {
        return skullsNumber;
    }

    public void resetDamages()
    {

    }

    public void addSkull()
    {
        skullsNumber += 1;
    }

    public boolean checkAdrenalinicGrab()
    {
        boolean i =true;
        return i;
    }

    public boolean checkAdrenalinicShot()
    {
        boolean i =true;
        return i;
    }

    public void  setFinalFrenzyBoard()
    {

    }

    // contiene un return di default per poter eseguire sonar
    public int checkNumberOfMarks(Player enemy)
    {
        return 1;
    }

    public void removeMarks (Player enemy)
    {

    }

}
