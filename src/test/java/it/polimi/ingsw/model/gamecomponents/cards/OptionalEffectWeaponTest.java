package it.polimi.ingsw.model.gamecomponents.cards;

import it.polimi.ingsw.event.controllerviewevent.ControllerViewEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class OptionalEffectWeaponTest {
    private Weapon oneOptionalTest;
    private Weapon twoOptionalTest;

    @Before
    public void setUp(){
        oneOptionalTest = new OneOptionalEffectWeapon(CubeColour.Blue, "Test", new AmmoCube[]{}, new AmmoCube[]{}) {
            @Override public ControllerViewEvent getTargetEffectTwo() { return null; }
            @Override public void performEffectTwo(List<Object> targets) { }
            @Override public void performEffectOne(List<Object> targets) { }
            @Override public ControllerViewEvent getTargetEffectOne() { return null; }
        };
        twoOptionalTest = new TwoOptionalEffectWeapon(CubeColour.Blue, "Test", new AmmoCube[]{}, new AmmoCube[]{}, new AmmoCube[]{}) {
            @Override public ControllerViewEvent getTargetEffectThree() { return null; }
            @Override public void performEffectThree(List<Object> targets) { }
            @Override public ControllerViewEvent getTargetEffectTwo() { return null; }
            @Override public void performEffectTwo(List<Object> targets) { }
            @Override public void performEffectOne(List<Object> targets) { }
            @Override public ControllerViewEvent getTargetEffectOne() { return null; }
        };
    }

    @Test
    public void oneOptionalControlFlowEffectOneTest(){
        oneOptionalTest.effectControlFlow(1);
        Assert.assertFalse(oneOptionalTest.getUsableEffect()[0]);
        Assert.assertTrue(oneOptionalTest.getUsableEffect()[1]);
    }

    @Test
    public void oneOptionalControlFlowEffectTwoTest() {
        oneOptionalTest.effectControlFlow(2);
        Assert.assertFalse(oneOptionalTest.getUsableEffect()[1]);
        Assert.assertTrue(oneOptionalTest.getUsableEffect()[2]);
    }

    @Test
    public void twoOptionalControlFlowEffectOneTest(){
        twoOptionalTest.effectControlFlow(1);
        Assert.assertFalse(twoOptionalTest.getUsableEffect()[0]);
        Assert.assertTrue(twoOptionalTest.getUsableEffect()[1]);
        Assert.assertTrue(twoOptionalTest.getUsableEffect()[2]);
    }

    @Test
    public void twoOptionalControlFlowEffectTwoTest(){
        twoOptionalTest.effectControlFlow(3);
        Assert.assertFalse(twoOptionalTest.getUsableEffect()[2]);
        Assert.assertTrue(twoOptionalTest.getUsableEffect()[1]);
        Assert.assertTrue(twoOptionalTest.getUsableEffect()[0]);
    }

}
