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
    private Player Player1;

    @Before

    public void setUp(){
        testTile = new AmmoTile(new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Blue),new AmmoCube(CubeColour.Yellow), false);
        Player1 = new Player("Evandro",Character.BANSHEE);
    }
    @Test
    public void testBasicSquare(){
        testedSquare= new BasicSquare(0,0);
        Assert.assertFalse(((BasicSquare)testedSquare).checkAmmo());
        ((BasicSquare) testedSquare).replaceAmmoTile(testTile);
        Assert.assertEquals(testTile,((BasicSquare) testedSquare).getAmmo());
        ((BasicSquare) testedSquare).grabAmmoTile(Player1);
        Assert.assertFalse(((BasicSquare)testedSquare).checkAmmo());

    }

    @Test
    public void testSpawnSquare(){
        testedSquare = new SpawnSquare(0,0);
        AmmoCube[] cubes = testTile.getAmmoCubes();
       LockRifle distructor = new LockRifle(CubeColour.Blue, "Distructor",
                cubes, null);
        ((SpawnSquare)testedSquare).replaceWeapon(distructor);
        ((SpawnSquare) testedSquare).grabWeapon(distructor,Player1);
        Assert.assertNotEquals(-1, Player1.getWeaponIndex(distructor));
        Assert.assertFalse(((SpawnSquare) testedSquare) .getWeapons().contains(distructor));
        ((SpawnSquare) testedSquare).replaceWeapon(distructor);
        System.out.println(((SpawnSquare) testedSquare).getWeapons().size());
        Assert.assertTrue(((SpawnSquare) testedSquare) .getWeapons().contains(distructor));

    }

}
