package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PowerUpTest {
    private PowerUp testedPowerUp;
    private Player testPlayer;

    @Before
    public void setUp(){
        testPlayer = new Player("TestUser", Character.SPROG, "TestCry");
        testPlayer.setPosition(new BasicSquare(0,0));
    }


    @Test
    public void testTeleporter(){
        Square expectedPosition = new BasicSquare(1,1);
        testedPowerUp = new Teleporter(CubeColour.Blue);
        testedPowerUp.setOwner(testPlayer);
        testedPowerUp.setTarget(testPlayer);
        ((Teleporter)testedPowerUp).setDestination(expectedPosition);
        testedPowerUp.useEffect();
        Assert.assertEquals(expectedPosition,testPlayer.getPosition());
        System.out.println("Tested Teleporter useEffect()");
    }



}
