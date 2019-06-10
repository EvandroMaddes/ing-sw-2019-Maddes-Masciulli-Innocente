package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.BasicSquare;
import it.polimi.ingsw.model.board.SpawnSquare;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.game_components.cards.power_ups.Newton;
import it.polimi.ingsw.model.game_components.cards.power_ups.TagbackGrenade;
import it.polimi.ingsw.model.game_components.cards.power_ups.TargetingScope;
import it.polimi.ingsw.model.game_components.cards.weapons.Electroscythe;
import it.polimi.ingsw.model.game_components.cards.weapons.LockRifle;
import it.polimi.ingsw.model.game_components.cards.weapons.MachineGun;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.DamageToken;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class EncoderTest {

    @Test
    public void encodePlayersTest(){
        Player player1 = new Player("Federico", Character.BANSHEE);
        Player player2 = new Player("Francesco", Character.D_STRUCT_OR);
        Player player3 = new Player("Evandro", Character.SPROG);
        ArrayList<Player> playersList = new ArrayList<>();
        playersList.add(player1);
        playersList.add(player2);
        playersList.add(player3);
        ArrayList<Character> playerListLite = Encoder.encodePlayerTargets(playersList);
        ArrayList<Character> expectedLiteList = new ArrayList<>();
        expectedLiteList.add(Character.BANSHEE);
        expectedLiteList.add(Character.D_STRUCT_OR);
        expectedLiteList.add(Character.SPROG);
        Assert.assertEquals(expectedLiteList, playerListLite);
    }

    @Test
    public void encodeSquaresXYTest(){
        Square  square1 = new BasicSquare(0,1);
        Square  square2 = new BasicSquare(2, 2);
        Square  square3 = new SpawnSquare(1,1);
        ArrayList<Square> squaresList = new ArrayList<>();
        squaresList.add(square1);
        squaresList.add(square2);
        squaresList.add(square3);
        int[] squareX= Encoder.encodeSquareTargetsX(squaresList);
        int[] squareY = Encoder.encodeSquareTargetsY(squaresList);
        int[] expectedSquareX = new int[]{0,2,1};
        int[] expectedSquareY = new int[]{1,2,1};
        for (int i = 0; i < squareX.length; i++) {
            Assert.assertEquals(expectedSquareX[i], squareX[i]);
            Assert.assertEquals(expectedSquareY[i], squareY[i]);
        }
    }

    @Test
    public void encodeWeaponsTest() {
        Weapon  weapon1 = new LockRifle(CubeColour.Blue, "LockRifle", new AmmoCube[]{}, new AmmoCube[]{});
        Weapon  weapon2 = new Electroscythe(CubeColour.Yellow, "Electroscythe", new AmmoCube[]{}, new AmmoCube[]{});
        Weapon  weapon3 = new MachineGun(CubeColour.Blue, "MachineGun", new AmmoCube[]{}, new AmmoCube[]{}, new AmmoCube[]{});
        ArrayList<Weapon> weaponsList = new ArrayList<>();
        weaponsList.add(weapon1);
        weaponsList.add(weapon2);
        weaponsList.add(weapon3);
        ArrayList<String> weaponsListLite = Encoder.encodeWeaponsList(weaponsList);
        String[] weaponsArrayLite = Encoder.encodeWeaponsIntoArray(weaponsList);
        ArrayList<String> expectedListLite = new ArrayList<>();
        expectedListLite.add("LockRifle");
        expectedListLite.add("Electroscythe");
        expectedListLite.add("MachineGun");
        String[] expectedArrayLite = new String[]{"LockRifle", "Electroscythe", "MachineGun"};
        Assert.assertEquals(expectedListLite, weaponsListLite);
        for (int i = 0; i < weaponsArrayLite.length; i++) {
            Assert.assertEquals(weaponsArrayLite[i], expectedArrayLite[i]);
        }
    }

    @Test
    public void encodePowerUpsTest(){
        PowerUp powerUp1 = new TagbackGrenade(CubeColour.Blue);
        PowerUp powerUp2 = new TargetingScope(CubeColour.Red);
        PowerUp powerUp3 = new Newton(CubeColour.Blue);
        ArrayList<PowerUp> powerUpsList = new ArrayList<>();
        powerUpsList.add(powerUp1);
        powerUpsList.add(powerUp2);
        powerUpsList.add(powerUp3);
        String[] powerUpType = Encoder.encodePowerUpsType(powerUpsList);
        CubeColour[] powerUpColour = Encoder.encodePowerUpColour(powerUpsList);
        String[] expectedType = new String[]{"TagbackGranade", "TargetingScope", "Newton"};
        CubeColour[] expectedColour = new CubeColour[]{CubeColour.Blue, CubeColour.Red, CubeColour.Blue,};
        for (int i = 0; i < powerUpType.length; i++) {
            Assert.assertEquals(expectedType[i], powerUpType[i]);
            Assert.assertEquals(expectedColour[i], powerUpColour[i]);
        }
    }

    @Test
    public void encodeDamageTokensTest(){
        Player player1 = new Player("Federico", Character.BANSHEE);
        Player player2 = new Player("Francesco", Character.D_STRUCT_OR);
        Player player3 = new Player("Evandro", Character.SPROG);
        DamageToken damageToken1 = new DamageToken(player1);
        DamageToken damageToken2 = new DamageToken(player2);
        DamageToken damageToken3 = new DamageToken(player1);
        Character[] expectedResult = new Character[]{Character.BANSHEE, Character.D_STRUCT_OR, Character.BANSHEE};
        Character[] damageTokensLite = new Character[expectedResult.length];

        ArrayList<DamageToken> damageTokensList = new ArrayList<>();
        damageTokensList.add(damageToken1);
        damageTokensList.add(damageToken2);
        damageTokensLite = Encoder.encodeDamageTokenList(damageTokensList);
        for (int i = 0; i < damageTokensLite.length; i++) {
            Assert.assertEquals(expectedResult[i], damageTokensLite[i]);
        }

        DamageToken[] damageTokensArray = new DamageToken[]{damageToken1, damageToken2};
        int damageTokensArraySize = 2;
        damageTokensLite = Encoder.encodeDamagesTokenArray(damageTokensArray, damageTokensArraySize);
        for (int i = 0; i < damageTokensArraySize; i++) {
            Assert.assertEquals(expectedResult[i], damageTokensLite[i]);
        }
    }

}
