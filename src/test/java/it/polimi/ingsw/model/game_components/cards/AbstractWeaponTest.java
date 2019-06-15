package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.weapons.Zx2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AbstractWeaponTest {
    private Weapon weapon1;

    @Before
    public void setUp(){
        weapon1 = new Weapon(CubeColour.Blue, "Test", new AmmoCube[]{new AmmoCube(CubeColour.Blue), new AmmoCube(CubeColour.Blue)}) {
            @Override
            public void performEffectOne(List<Object> targets) {
            }
            @Override
            public ControllerViewEvent getTargetEffectOne() {
                return null;
            }
        };
    }

    @Test
    public void getGrabCostTest(){
        AmmoCube[] expectedCost = new AmmoCube[]{new AmmoCube(CubeColour.Blue)};
        AmmoCube[] actualCost = weapon1.getGrabCost();
        Assert.assertEquals(1, actualCost.length);
        for (int i = 0; i < actualCost.length; i++){
            Assert.assertEquals(expectedCost[i].getColour(), actualCost[i].getColour());
        }
    }

    @Test
    public void enableEffectsTEst(){
        boolean[] expected = new boolean[]{true, false, false};
        boolean[] actual = weapon1.getEffectsEnable();
        Assert.assertEquals(3, actual.length);
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void effectControlFlowTest(){
        Assert.assertTrue(weapon1.getUsableEffect()[0]);
        weapon1.effectControlFlow(1);
        boolean[] actual = weapon1.getUsableEffect();
        Assert.assertEquals(3, actual.length);
        for (int i = 0; i < 3; i++) {
            Assert.assertFalse(actual[i]);
        }
    }

    @Test
    public void checkEmptyTargetTest(){
        Zx2 zx2 = new Zx2();
        ArrayList<Object> test = new ArrayList<>();
        try{
            zx2.performEffect(1, test);
        }
        catch (IllegalArgumentException e){
            Assert.assertEquals("No targets found", e.getMessage());
        }
    }
}
