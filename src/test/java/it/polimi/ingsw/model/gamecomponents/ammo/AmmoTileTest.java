package it.polimi.ingsw.model.gamecomponents.ammo;

import it.polimi.ingsw.model.gamecomponents.cards.powerups.TagbackGrenade;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class AmmoTileTest {
    private AmmoTile testedCubeTile;
    private AmmoTile testedPowerUpTile;
    private Player testedPlayer;


    @Before
    public void setUp() {
        testedPlayer = new Player("TestUser", Character.BANSHEE);
        AmmoCube[] testedCube = new AmmoCube[3];
        testedCube[0] = new AmmoCube(CubeColour.Blue);
        testedCube[1] = new AmmoCube(CubeColour.Yellow);
        testedCube[2] = new AmmoCube(CubeColour.Red);
        testedCubeTile = new AmmoTile(testedCube[0], testedCube[1], testedCube[2], false);
        testedPowerUpTile = new AmmoTile(testedCube[0], testedCube[1], null, true);
    }

    /**
     * Test that, according to setUp, the Player Grab 3 different coloured AmmoCubes;
     * (expected = 2 for each colour, the grabbed and the initial Cubes)
     */
    @Test
    public void testCubeAmmoTile() {
        testedCubeTile.pickAmmo(testedPlayer);
        Assert.assertEquals(2, testedPlayer.getCubeColourNumber(testedCubeTile.getAmmoCubes()[0].getColour()));
        Assert.assertEquals(2, testedPlayer.getCubeColourNumber(testedCubeTile.getAmmoCubes()[1].getColour()));
        Assert.assertEquals(2, testedPlayer.getCubeColourNumber(testedCubeTile.getAmmoCubes()[2].getColour()));
        System.out.println("Tested CubeAmmoTile");
    }

    /**
     * Test that the powerUps ammo tile are correctly build and grabbed
     */
    @Test
    public void testPowerUpTile() {
        Assert.assertTrue(testedPowerUpTile.isPowerUpTile());
        testedPowerUpTile.setPowerUp(new TagbackGrenade(CubeColour.Blue));
        testedPowerUpTile.pickAmmo(testedPlayer);
        Assert.assertEquals(2, testedPlayer.getCubeColourNumber(testedCubeTile.getAmmoCubes()[0].getColour()));
        Assert.assertEquals(2, testedPlayer.getCubeColourNumber(testedCubeTile.getAmmoCubes()[1].getColour()));
        Assert.assertEquals(5, testedPlayer.getCubeColourNumber(CubeColour.Yellow)
                + testedPlayer.getCubeColourNumber(CubeColour.Red) + testedPlayer.getCubeColourNumber(CubeColour.Yellow));
        Assert.assertEquals(1, testedPlayer.getPowerUps().size());
        Assert.assertEquals("TagbackGrenade", testedPlayer.getPowerUps().get(0).getName());
        System.out.println("Tested PowerUpTile");
    }
}
