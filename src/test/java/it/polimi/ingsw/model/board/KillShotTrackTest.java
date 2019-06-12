package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KillShotTrackTest {
    private GameTrack testedTrack;

    @Before
    public void setUp(){
        testedTrack = new KillShotTrack();
    }

    @Test
    public void evaluateDamageTest(){
        Player testPlayer = new Player("TestUser", Character.D_STRUCT_OR);
        testedTrack.evaluateDamage(new DamageToken(testPlayer),2);
        Assert.assertEquals(2, ((KillShotTrack)testedTrack).getTokenTrack().size());
        Assert.assertEquals(testPlayer, ((KillShotTrack) testedTrack).getTokenTrack().get(0).getPlayer());
        Assert.assertEquals(2, testedTrack.getTokenSequence()[0]);
        Assert.assertEquals(0, testedTrack.getTokenSequence()[1]);
        for (int i = 0; i < 9; i++)
            testedTrack.evaluateDamage(new DamageToken(testPlayer), 1);
        Assert.assertTrue(testedTrack.checkEndTrack());
        System.out.println("Tested KillShotTrack methods");

    }

    @Test
    public void collectTrackPointsTest(){
        Player player1 = new Player("Federico", Character.BANSHEE);
        Player player2 = new Player("Evandro", Character.SPROG);
        Player player3 = new Player("Francesco", Character.DOZER);
        Player player4 = new Player("Chiara", Character.VIOLET);
        testedTrack.evaluateDamage(new DamageToken(player1), 2);
        testedTrack.evaluateDamage(new DamageToken(player1), 1);
        testedTrack.evaluateDamage(new DamageToken(player2), 2);
        testedTrack.evaluateDamage(new DamageToken(player3), 2);
        testedTrack.evaluateDamage(new DamageToken(player2), 2);
        testedTrack.collectGameTrackPoints();
        Assert.assertEquals(8, player2.getPoints());
        Assert.assertEquals(6, player1.getPoints());
        Assert.assertEquals(4, player3.getPoints());
        Assert.assertEquals(0, player4.getPoints());
    }

    @Test
    public void collectTrackPointsWithTiesTest(){
        Player player1 = new Player("Federico", Character.BANSHEE);
        Player player2 = new Player("Evandro", Character.SPROG);
        Player player3 = new Player("Francesco", Character.DOZER);
        Player player4 = new Player("Chiara", Character.VIOLET);
        testedTrack.evaluateDamage(new DamageToken(player1), 2);
        testedTrack.evaluateDamage(new DamageToken(player1), 2);
        testedTrack.evaluateDamage(new DamageToken(player2), 2);
        testedTrack.evaluateDamage(new DamageToken(player3), 2);
        testedTrack.evaluateDamage(new DamageToken(player2), 2);
        testedTrack.evaluateDamage(new DamageToken(player3), 2);
        testedTrack.evaluateDamage(new DamageToken(player4), 2);
        testedTrack.evaluateDamage(new DamageToken(player4), 2);
        testedTrack.evaluateDamage(new DamageToken(player4), 2);
        testedTrack.evaluateDamage(new DamageToken(player4), 2);
        testedTrack.collectGameTrackPoints();
        Assert.assertEquals(8, player4.getPoints());
        Assert.assertEquals(6, player1.getPoints());
        Assert.assertEquals(4, player2.getPoints());
        Assert.assertEquals(2, player3.getPoints());
    }


}
