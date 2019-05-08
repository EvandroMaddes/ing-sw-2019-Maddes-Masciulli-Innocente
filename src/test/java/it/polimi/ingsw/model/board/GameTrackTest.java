package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameTrackTest {
    private GameTrack testedTrack;
    private Player testPlayer;

    @Before
    public void setUp(){
        testedTrack = new KillShotTrack();
        testPlayer = new Player("TestUser", Character.D_STRUCT_OR);
    }

    @Test
    public void testKillShotTrack(){
        ((KillShotTrack)testedTrack).evaluateDamage(new DamageToken(testPlayer),10);
        Assert.assertTrue(testedTrack.checkEndTrack());
        Assert.assertEquals(10, ((KillShotTrack)testedTrack).getTokenTrack().size());
        Assert.assertEquals(testPlayer, ((KillShotTrack) testedTrack).getTokenTrack().get(0).getPlayer());
        System.out.println("Tested KillShotTrack methods");
    }


}
