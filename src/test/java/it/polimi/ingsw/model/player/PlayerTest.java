package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.model.gamecomponents.cards.Weapon;
import it.polimi.ingsw.model.gamecomponents.cards.power_ups.Newton;
import it.polimi.ingsw.model.gamecomponents.cards.power_ups.TagbackGrenade;
import it.polimi.ingsw.model.gamecomponents.cards.power_ups.TargetingScope;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Electroscythe;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.Heatseeker;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.LockRifle;
import it.polimi.ingsw.model.gamecomponents.cards.weapons.PlasmaGun;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp(){
        player = new Player("Federico", Character.SPROG);
    }

    @Test
    public void setPositionTest(){
        Square square = new BasicSquare(1, 2);
        player.setPosition(square);
        ArrayList<Player> expectedPlayerList = new ArrayList<>();
        expectedPlayerList.add(player);

        Assert.assertEquals(square, player.getPosition());
        Assert.assertEquals(expectedPlayerList, square.getSquarePlayers());
    }

    @Test
    public void getCubeColourNumberTest(){
        player.discardAmmo(new AmmoCube(CubeColour.Blue));
        player.addAmmo(new AmmoCube(CubeColour.Yellow));
        player.addAmmo(new AmmoCube(CubeColour.Yellow));
        player.addAmmo(new AmmoCube(CubeColour.Yellow));
        int blue = player.getCubeColourNumber(CubeColour.Blue);
        int yellow = player.getCubeColourNumber(CubeColour.Yellow);
        int red = player.getCubeColourNumber(CubeColour.Red);

        Assert.assertEquals(0, blue);
        Assert.assertEquals(1, red);
        Assert.assertEquals(3, yellow);
    }

    @Test
    public void ammoChange(){
        AmmoCube ammo = new AmmoCube(CubeColour.Blue);
        player.addAmmo(ammo);

        Assert.assertEquals(4, player.getAmmo().size());
        Assert.assertEquals(ammo, player.getAmmo().get(3));

        player.discardAmmo(ammo);
        player.discardAmmo(ammo);
        for (AmmoCube a:player.getAmmo()) {
            Assert.assertNotEquals(ammo.getColour(), a.getColour());
        }
    }

    @Test
    public void canAffortCostTest(){
        player.discardAmmo(new AmmoCube(CubeColour.Blue));
        player.addPowerUp(new Newton(CubeColour.Red));

        AmmoCube[] unpayableCost = new AmmoCube[]{new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Yellow)};
        AmmoCube[] payableCost = new AmmoCube[]{new AmmoCube(CubeColour.Red), new AmmoCube(CubeColour.Yellow)};
        AmmoCube[] payableWithPowerUp = new AmmoCube[]{new AmmoCube(CubeColour.Red), new AmmoCube(CubeColour.Red)};

        Assert.assertFalse(player.canAffortCost(unpayableCost));
        Assert.assertTrue(player.canAffortCost(payableCost));
        Assert.assertTrue(player.canAffortCost(payableWithPowerUp));
    }

    @Test
    public void discardWeaponTest(){
        Weapon weapon1 = new LockRifle();
        Weapon weapon2 = new Electroscythe();
        Weapon weapon3 = new PlasmaGun();
        Weapon weapon4 = new Heatseeker();
        Square spawnSquare = new SpawnSquare(1,1);
        player.setPosition(spawnSquare);

        player.addWeapon(weapon1);
        player.addWeapon(weapon2);
        player.addWeapon(weapon3);
        Assert.assertEquals(weapon2, player.getWeapons()[1]);

        player.discardWeapon(weapon3);
        Assert.assertNotEquals(weapon3, player.getWeapons()[2]);

        player.addWeapon(weapon3);
        player.discardWeapon(weapon1);

        Assert.assertNull(player.getWeapons()[2]);
        for (int i = 0; i < 4; i++) {
            Assert.assertNotEquals(weapon1, player.getWeapons()[i]);
        }

        player.addWeapon(weapon1);
        player.addWeapon(weapon4);
        player.discardWeapon(weapon1);

        Assert.assertEquals(3, player.getNumberOfWeapons());
        for (int i = 0; i < 3; i++) {
            Assert.assertNotEquals(weapon1, player.getWeapons()[i]);
        }
    }

    @Test
    public void getWhileActionPowerUp(){
        PowerUp powerUp1 = new TargetingScope(CubeColour.Blue);
        PowerUp powerUp2 = new TargetingScope(CubeColour.Red);
        PowerUp powerUp3 = new Newton(CubeColour.Blue);
        PowerUp powerUp4 = new TagbackGrenade(CubeColour.Blue);
        player.addPowerUp(powerUp1);
        player.addPowerUp(powerUp2);
        player.addPowerUp(powerUp3);

        ArrayList<PowerUp> expectedPowerUp = new ArrayList<>();
        expectedPowerUp.add(powerUp1);
        expectedPowerUp.add(powerUp2);
        ArrayList<PowerUp> whileActionPowerUp = player.getWhileActionPowerUp();

        Assert.assertEquals(expectedPowerUp, whileActionPowerUp);
        player.discardPowerUp(powerUp1);
        player.addPowerUp(powerUp4);
        expectedPowerUp.remove(powerUp1);
        whileActionPowerUp = player.getWhileActionPowerUp();

        Assert.assertEquals(expectedPowerUp, whileActionPowerUp);
    }
}
