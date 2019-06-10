package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.board.Square;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.PowerUp;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.game_components.cards.power_ups.Newton;
import it.polimi.ingsw.model.game_components.cards.weapons.Electroscythe;
import it.polimi.ingsw.model.game_components.cards.weapons.LockRifle;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DecoderTest {
    private Player player1;
    private Player player2;
    private Player player3;
    ArrayList<Player> playersList;

    @Before
    public void setUp(){
        player1 = new Player("Federico", Character.SPROG);
        player2 = new Player("Francesco", Character.D_STRUCT_OR);
        player3 = new Player("Evandro", Character.DOZER);
        playersList = new ArrayList<>();
        playersList.add(player1);
        playersList.add(player2);
        playersList.add(player3);
    }

    @Test
    public void decodePowerUpTest(){
        PowerUp powerUp1 = new Newton(CubeColour.Blue);
        PowerUp powerUp2 = new Newton(CubeColour.Blue);
        PowerUp powerUp3 = new Newton(CubeColour.Red);
        player1.addPowerUp(powerUp1);
        player1.addPowerUp(powerUp2);
        player1.addPowerUp(powerUp3);
        String[] powerUpType = new String[]{"Newton", "Newton"};
        CubeColour[] powerUpColour = new CubeColour[]{CubeColour.Red, CubeColour.Blue};

        ArrayList<PowerUp> decodedPowerUpList = Decoder.decodePowerUpsList(player1, powerUpType, powerUpColour);
        ArrayList<PowerUp> expectedList = new ArrayList<>();
        expectedList.add(powerUp3);
        expectedList.add(powerUp1);
        Assert.assertEquals(expectedList, decodedPowerUpList);

        PowerUp decodedPowerUp = Decoder.decodePowerUp(player1, "Newton", CubeColour.Red);
        PowerUp expectedPowerUp = powerUp3;
        Assert.assertEquals(decodedPowerUp, expectedPowerUp);
    }

    @Test
    public void decodeSquareTest(){
        Map map = new Map("leftFirst", "rightSecond");
        int toDecodeX = 2;
        int toDecodeY = 1;
        Square expectedSquare = map.getSquareMatrix()[toDecodeX][toDecodeY];
        Square decodedSquare = Decoder.decodeSquare(toDecodeX, toDecodeY, map);
        Assert.assertEquals(expectedSquare, decodedSquare);
    }

    @Test
    public void decodePlayerListTest(){
        ArrayList<Character> characterList = new ArrayList<>();
        characterList.add(Character.SPROG);
        characterList.add(Character.DOZER);
        ArrayList<Object> decodedPlayers = Decoder.decodePlayerListAsObject(characterList, playersList);
        ArrayList<Object> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(player1);
        expectedPlayers.add(player3);
        Assert.assertEquals(expectedPlayers, decodedPlayers);
    }

    @Test
    public void decodePlayerTest(){
        Player decodedPlayer = Decoder.decodePlayerFromUsername("Federico", playersList);
        Assert.assertEquals(player1, decodedPlayer);
        decodedPlayer = Decoder.decodePlayerFromCharacter(Character.D_STRUCT_OR, playersList);
        Assert.assertEquals(player2, decodedPlayer);
    }

    @Test
    public void decodePlayerWeaponTest(){
        Weapon weapon1 = new LockRifle(CubeColour.Blue, "LockRifle", new AmmoCube[]{}, new AmmoCube[]{});
        Weapon weapon2 = new Electroscythe(CubeColour.Blue, "Electroscythe", new AmmoCube[]{}, new AmmoCube[]{});
        player1.addWeapon(weapon1);
        player1.addWeapon(weapon2);
        Weapon decodedWeapon = Decoder.decodePlayerWeapon(player1, "Electroscythe");
        Assert.assertEquals(weapon2, decodedWeapon);
    }


}
