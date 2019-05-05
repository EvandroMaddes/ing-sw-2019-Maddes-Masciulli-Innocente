package it.polimi.ingsw.model.game_components.ammo;

import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Teleporter;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class AmmoTileTest {
    private AmmoTile testedCubeTile;
    private AmmoTile testedPowerUpTile;
    private  Player testedPlayer;
    PowerUp testedPowerUp = new Teleporter(CubeColour.Red);

    @Before
    public void setUp(){
        testedPlayer = new Player("TestUser", Character.BANSHEE, "TestCry\nAHAAAAAAAH!!");
        AmmoCube[] testedCube = new AmmoCube[3];
        testedCube[0] = new AmmoCube(CubeColour.Blue);
        testedCube[1] = new AmmoCube(CubeColour.Yellow);
        testedCube[2] = new AmmoCube(CubeColour.Red);
        testedCubeTile = new CubeAmmoTile(testedCube[0],testedCube[1],testedCube[2]);
        testedPowerUpTile = new PowerUpAmmoTile(testedCube[0],testedCube[1],testedPowerUp);
    }

    /**
     * Test that, according to setUp, the Player Grab 3 different coloured AmmoCubes;
     *      (expected = 2 for each colour, the grabbed and the initial Cubes)
     */
    @Test
    public void testCubeAmmoTile(){
        testedCubeTile.pickAmmo(testedPlayer);
        Assert.assertEquals(2, testedPlayer.getCubeColourNumber(testedCubeTile.getFirstAmmo().getColour()));
        Assert.assertEquals(2, testedPlayer.getCubeColourNumber(testedCubeTile.getSecondAmmo().getColour()));
        Assert.assertEquals(2, testedPlayer.getCubeColourNumber(((CubeAmmoTile)testedCubeTile).getThirdAmmo().getColour()));
        System.out.println("Tested CubeAmmoTile");
    }

    @Test
    public void testPowerUpTile(){
        testedPowerUpTile.pickAmmo(testedPlayer);
        Assert.assertEquals(2, testedPlayer.getCubeColourNumber(testedCubeTile.getFirstAmmo().getColour()));
        Assert.assertEquals(2, testedPlayer.getCubeColourNumber(testedCubeTile.getSecondAmmo().getColour()));
        Assert.assertEquals(testedPowerUp,testedPlayer.getPowerUps().get(0));
        System.out.println("Tested PowerUpTile\n"+testedPlayer.getBattleCry());
    }
}
