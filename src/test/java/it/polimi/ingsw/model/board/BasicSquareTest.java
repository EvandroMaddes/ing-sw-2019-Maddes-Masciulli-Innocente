package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.AmmoTile;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BasicSquareTest {
    private Map gameMap;
    private Square[][] map;

    @Before
    public void setUp(){
        gameMap = new Map("leftFirst", "rightSecond");
        map = gameMap.getSquareMatrix();
    }

    @Test
    public void tileTest() {
        AmmoTile testTile = new AmmoTile(new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Yellow), false);
        Player player1 = new Player("Evandro", Character.BANSHEE);
        BasicSquare testedSquare = new BasicSquare(0, 0);
        Assert.assertFalse(testedSquare.checkAmmo());
        testedSquare.replaceAmmoTile(testTile);
        Assert.assertEquals(testTile, testedSquare.getAmmo());
        testedSquare.grabAmmoTile(player1);
        Assert.assertFalse(testedSquare.checkAmmo());
    }


}
