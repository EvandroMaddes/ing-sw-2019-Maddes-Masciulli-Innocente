package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.game_components.cards.weapons.LockRifle;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Test;

public class SpawnSquareTest {

    @Test
    public void isGrabbableTest(){
        SpawnSquare testSquare = new SpawnSquare(0,0);
        Player testPlayer = new Player("Federico", Character.SPROG);
        Weapon weapon1 = new LockRifle(CubeColour.Blue, "LR", new AmmoCube[]{new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Blue)}, new AmmoCube[]{});

        Assert.assertFalse(testSquare.isGrabbable(testPlayer));

        testSquare.getWeapons().add(weapon1);
        Assert.assertFalse(testSquare.isGrabbable(testPlayer));

        testPlayer.addAmmo(new AmmoCube(CubeColour.Blue));
        Assert.assertTrue(testSquare.isGrabbable(testPlayer));
    }
}
