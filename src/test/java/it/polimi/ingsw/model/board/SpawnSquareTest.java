package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.Weapon;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Heatseeker;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Test;

public class SpawnSquareTest {

    @Test
    public void grabTest(){
        SpawnSquare testSquare = new SpawnSquare(0,0);
        Player testPlayer = new Player("Federico", Character.SPROG);
        Weapon weapon1 = new Heatseeker();

        Assert.assertFalse(testSquare.isGrabbable(testPlayer));

        testSquare.getWeapons().add(weapon1);
        testPlayer.discardAmmo(new AmmoCube(CubeColour.Red));
        Assert.assertFalse(testSquare.isGrabbable(testPlayer));

        testPlayer.addAmmo(new AmmoCube(CubeColour.Red));
        Assert.assertTrue(testSquare.isGrabbable(testPlayer));

        testSquare.grabWeapon(weapon1, testPlayer);
        Assert.assertFalse(testSquare.getWeapons().contains(weapon1));
        Assert.assertEquals(weapon1, testPlayer.getWeapons()[0]);
    }
}
