package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.weapons.LockRifle;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SquareTest {
    private Square testedSquare;
    private  AmmoTile testTile;

    @Before

    public void setUp(){
        testTile = new AmmoTile(new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Blue),
                new AmmoCube(CubeColour.Yellow), false);

    }
    @Test
    public void testBasicSquare(){
        testedSquare= new BasicSquare(0,0);
        Assert.assertFalse(((BasicSquare)testedSquare).checkAmmo());
        ((BasicSquare) testedSquare).replaceAmmoTile(testTile);
        Assert.assertEquals(testTile,((BasicSquare) testedSquare).getAmmo());
        System.out.println("Tested BasicSquare methods");
    }

    @Test
    public void testSpawnSquare(){
        testedSquare = new SpawnSquare(0,0);
        Player testPlayer = new Player("TestUser", Character.SPROG);
        AmmoCube[] cubes = testTile.getAmmoCubes();
       LockRifle distructor = new LockRifle(CubeColour.Blue, "Distructor",
                cubes, null);
        ((SpawnSquare)testedSquare).replaceWeapon(distructor);
        ((SpawnSquare) testedSquare).grabWeapon(distructor,testPlayer);
        Assert.assertNotEquals(-1, testPlayer.getWeaponIndex(distructor));
        Assert.assertFalse(((SpawnSquare) testedSquare) .getWeapons().contains(distructor));
        //((SpawnSquare) testedSquare).replaceWeapon(null);
        Assert.assertFalse(testedSquare.isGrabbable());

    }

}
