package it.polimi.ingsw.model.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerBoardTest {
    private PlayerBoard testedPlayerBoard;
    private Player testPlayer;

    @Before
    public void setUp() {
    }

    {
        testPlayer = new Player("TestUser", Character.BANSHEE);
        testedPlayerBoard = testPlayer.getPlayerBoard();

    }

    /**
     * Test the skull adding and the final fenzy setting of the player
     */
    @Test
    public void testSkullAndFrenzy() {
        for (int i = 0; i < 6; i++) {
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

    /**
     * Thest that damages and marks are correclty given to the playerboard
     */
    @Test
    public void testDamageAndMark() {
        testedPlayerBoard.addMarks(testPlayer, 4);
        Assert.assertEquals(3, testedPlayerBoard.getMarks().size());
        testedPlayerBoard.addDamages(testPlayer, 10);
        Assert.assertTrue(testedPlayerBoard.checkAdrenalinicGrab() && testedPlayerBoard.checkAdrenalinicShot());
        DamageToken[] damageToken = testedPlayerBoard.getDamageReceived();
        int tokenNumber = 0;
        while (tokenNumber < 11) {
            Assert.assertEquals(testPlayer, damageToken[tokenNumber].getPlayer());
            tokenNumber++;
        }
        Assert.assertEquals(11, tokenNumber);
        testedPlayerBoard.addDamages(testPlayer, 7);
        Assert.assertEquals(11, tokenNumber);
        testedPlayerBoard.resetDamages();
        System.out.println("Tested mark and damage method");
    }

    /**
     * check that marks from different player are correctly given to a playerboard and are refused if tehy are more than three
     */
    @Test
    public void addMarksTest() {
        Player player2 = new Player("Federico", Character.SPROG);
        Player player3 = new Player("Chiara", Character.VIOLET);
        testedPlayerBoard.addMarks(player2, 3);
        testedPlayerBoard.addMarks(player3, 5);
        int player2Marks = testedPlayerBoard.checkNumberOfMarks(player2);
        int player3Marks = testedPlayerBoard.checkNumberOfMarks(player3);

        Assert.assertEquals(3, player2Marks);
        Assert.assertEquals(3, player3Marks);
    }

    /**
     * Check that marks are corerctly inflicted to the player as damage and removed form the marks after that the player get damaged
     */
    @Test
    public void inflictMarksTest() {
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
