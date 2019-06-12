package it.polimi.ingsw.model.game_components.cards;

import it.polimi.ingsw.event.controller_view_event.ControllerViewEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AlternativeEffectWeaponTest {

    @Test
    public void effectControlFlowEffectOneTest(){
        Weapon weaponTest = new AlternateFireWeapon(CubeColour.Blue, "Test", new AmmoCube[]{}, new AmmoCube[]{}) {
            @Override
            public ControllerViewEvent getTargetEffectTwo() { return null; }
            @Override
            public void performEffectTwo(List<Object> targets) { }
            @Override
            public void performEffectOne(List<Object> targets) { }
            @Override
            public ControllerViewEvent getTargetEffectOne() { return null; }
        };
        Assert.assertTrue(weaponTest.getEffectsEnable()[0]);
        Assert.assertTrue(weaponTest.getEffectsEnable()[1]);
        Assert.assertFalse(weaponTest.getEffectsEnable()[2]);
        Assert.assertTrue(weaponTest.getUsableEffect()[0]);
        Assert.assertTrue(weaponTest.getUsableEffect()[1]);
        weaponTest.effectControlFlow(1);
        Assert.assertFalse(weaponTest.getUsableEffect()[0]);
        Assert.assertFalse(weaponTest.getUsableEffect()[1]);
        Assert.assertFalse(weaponTest.getUsableEffect()[2]);
    }

    @Test
    public void effectControlFlowEffectTwo(){
        Weapon weaponTest = new AlternateFireWeapon(CubeColour.Blue, "Test", new AmmoCube[]{}, new AmmoCube[]{}) {
            @Override
            public ControllerViewEvent getTargetEffectTwo() { return null; }
            @Override
            public void performEffectTwo(List<Object> targets) { }
            @Override
            public void performEffectOne(List<Object> targets) { }
            @Override
            public ControllerViewEvent getTargetEffectOne() { return null; }
        };
        weaponTest.effectControlFlow(2);
        Assert.assertFalse(weaponTest.getUsableEffect()[0]);
        Assert.assertFalse(weaponTest.getUsableEffect()[1]);
        Assert.assertFalse(weaponTest.getUsableEffect()[2]);
    }
}
