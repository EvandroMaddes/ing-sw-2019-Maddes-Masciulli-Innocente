package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class PowerUpTest {
    private PowerUp testedPowerUp;
    private Player testPlayer;
    private DamageToken[] damages;

    @Before
    public void setUp(){
        testPlayer = new Player("TestUser", Character.SPROG);
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
        System.out.println("Tested Teleporter!\nExpected position is:"+expectedPosition.toString() +
                            "\tFinal position is");
    }

    @Test
    public void testTargetingScope(){
        testedPowerUp = new TargetingScope(CubeColour.Blue);
        testedPowerUp.setOwner(testPlayer);
        testedPowerUp.setTarget(testPlayer);
        ((TargetingScope)testedPowerUp).chooseCube=true;
        ((TargetingScope)testedPowerUp).cubeChoice= testPlayer.getAmmo().get(0);
        CubeColour payedColour = ((TargetingScope) testedPowerUp).cubeChoice.getColour();
        testedPowerUp.useEffect();
        Assert.assertEquals(0,testPlayer.getCubeColourNumber(payedColour));
        System.out.println("Tested Ammo payed TargetingScope");


        Assert.assertNull(testedPowerUp.getOwner());
        testedPowerUp.setOwner(testPlayer);
        testPlayer.addPowerUp(new TargetingScope(CubeColour.Blue));
        ((TargetingScope) testedPowerUp).powerUpChoice = testPlayer.getPowerUps().get(0);
        ((TargetingScope) testedPowerUp).chooseCube = false;
        testedPowerUp.useEffect();
        Assert.assertTrue(testPlayer.getPowerUps().isEmpty());

        evaluateDamageTokenPlayer();
        System.out.println("Tested PowerUp payed TargetingScope");
    }

    @Test
    public void testTagbackGrenade(){
        testedPowerUp = new TagbackGrenade(CubeColour.Red);
        testedPowerUp.setOwner(testPlayer);
        testedPowerUp.setTarget(testPlayer);
        testedPowerUp.useEffect();
        testPlayer.getPlayerBoard().addDamages(testPlayer, 1);
        evaluateDamageTokenPlayer();
        System.out.println("Tested TagbackGrenade");


    }


    /**
     *
     */
/*    @Test
    public void testNewton(){
        testedPowerUp = new Newton(CubeColour.Blue);
        Square startPosition = new BasicSquare(0,0);
        Square endPosition = new BasicSquare(0,2);
        Square middleSquare = new BasicSquare(0,1);
        Square wrongPosition = new BasicSquare(1,0);
        startPosition.setNearSquares(null,null,middleSquare,null);
        startPosition.setSquareReachable(false, false,true,false);
        wrongPosition.setNearSquares(null,null, null, endPosition);
        wrongPosition.setSquareReachable(false,false,false,false);
        middleSquare.setNearSquares(null, null, endPosition, startPosition);
        middleSquare.setSquareReachable(false,false,true,true);
        endPosition.setNearSquares(null,null , wrongPosition, middleSquare);
        endPosition.setSquareReachable(false,false,false,true);
        testPlayer.setPosition(startPosition);
        testedPowerUp.setOwner(testPlayer);
        testedPowerUp.setTarget(testPlayer);
        ((Newton)testedPowerUp).setDirection(2);
        ((Newton) testedPowerUp).setTimes(2);
        testedPowerUp.useEffect();
        Assert.assertEquals(endPosition,testPlayer.getPosition());
        System.out.println("Tested valid movement");
        testedPowerUp.setOwner(testPlayer);
        ((Newton) testedPowerUp).setTimes(1);
        try {
            testedPowerUp.useEffect();
        }catch (IllegalArgumentException illegalPosition){
            System.out.println("Exception Catched:  "+illegalPosition.getMessage());
        }

        Assert.assertNotEquals(wrongPosition,testPlayer.getPosition());
        Assert.assertEquals(endPosition,testPlayer.getPosition());
        System.out.println("Tested Newton");



    }
*/

    private void evaluateDamageTokenPlayer(){
        damages = testPlayer.getPlayerBoard().getDamageReceived();
        Assert.assertNotNull(damages[0]);
        Assert.assertNotNull(damages[1]);
        Assert.assertEquals(testPlayer, damages[0].getPlayer());
        Assert.assertEquals(testPlayer, damages[1].getPlayer());
    }


}
