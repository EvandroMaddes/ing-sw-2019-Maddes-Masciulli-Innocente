package it.polimi.ingsw.model.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerBoardTest {
    private PlayerBoard testedPlayerBoard;
    private Player testPlayer;

    @Before
    public  void setUp(){}{
        testPlayer = new Player("TestUser", Character.BANSHEE);
        testedPlayerBoard = testPlayer.getPlayerBoard();

    }
    @Test
    public void testSkullAndFrenzy(){
        for(int i=0; i<6; i++) {
            testedPlayerBoard.addSkull();
        }
        Assert.assertEquals(6, testedPlayerBoard.getSkullsNumber());
        testedPlayerBoard.addSkull();
        Assert.assertEquals(6, testedPlayerBoard.getSkullsNumber());
        System.out.println("Tested Skull handler methods");
        testedPlayerBoard.setFinalFrenzyScoreBoard();
        Assert.assertEquals(3, testedPlayerBoard.getSkullsNumber());
        System.out.println("Tested Final Frenzy Skull settings");
    }

    @Test
    public void testDamageAndMark(){
        testedPlayerBoard.addMarks(testPlayer,4);
        testedPlayerBoard.addDamages(testPlayer,10);
        Assert.assertTrue(testedPlayerBoard.checkAdrenalinicGrab()&&testedPlayerBoard.checkAdrenalinicShot());
        DamageToken[] damageToken = testedPlayerBoard.getDamageReceived();
        DamageToken currToken = damageToken[0];
        int tokenNumber = 0;
        while(currToken !=null && tokenNumber < 11  ){
            Assert.assertEquals(testPlayer,currToken.getPlayer());
            currToken= damageToken[tokenNumber];
            tokenNumber++;
        }
        Assert.assertEquals(11, tokenNumber);
        testedPlayerBoard.addDamages(testPlayer,7);
        Assert.assertEquals(11, tokenNumber);
        testedPlayerBoard.resetDamages();
        System.out.println("Tested mark and damage method");
    }

    @Test
    public void addMarksTest(){
        Player player2 = new Player("Federico", Character.SPROG);
        Player player3 = new Player("Chiara", Character.VIOLET);
        testedPlayerBoard.addMarks(player2, 3);
        testedPlayerBoard.addMarks(player3, 5);
        int player2Marks = testedPlayerBoard.checkNumberOfMarks(player2);
        int player3Marks = testedPlayerBoard.checkNumberOfMarks(player3);

        Assert.assertEquals(3, player2Marks);
        Assert.assertEquals(3, player3Marks);
    }

    @Test
    public void inflictMarksTest(){
        Player player2 = new Player("Federico", Character.DOZER);
        testedPlayerBoard.addMarks(player2, 3);
        testedPlayerBoard.inflictMarks(player2);

        Assert.assertEquals(0, testedPlayerBoard.checkNumberOfMarks(player2));
        Assert.assertEquals(3, testedPlayerBoard.getDamageAmount());

        int inflictedDamages = 0;
        for (int i = 0; i < testedPlayerBoard.getDamageAmount(); i++) {
            if (testedPlayerBoard.getDamageReceived()[i].getPlayer() == player2)
                inflictedDamages++;
        }
        Assert.assertEquals(3, inflictedDamages);
    }


}
