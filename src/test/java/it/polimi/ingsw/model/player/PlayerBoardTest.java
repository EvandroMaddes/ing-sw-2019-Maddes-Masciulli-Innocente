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


}
