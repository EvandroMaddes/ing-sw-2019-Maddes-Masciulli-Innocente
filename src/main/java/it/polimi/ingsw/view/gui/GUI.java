package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class GUI extends RemoteView {

/*    public GUI(String user) {
        super(user);
    }

 */

    @Override
    public String[] gameInit() {
        return new String[0];
    }

    @Override
    public Event characterChoice(ArrayList<Character> availableCharacters) {
        return null;
    }

    @Override
    public Event weaponDiscardChoice(ArrayList<Weapon> yourWeapon) {
        return null;
    }

    @Override
    public Event gameChoice() {
        return null;
    }

    @Override
    public Event actionChoice(boolean fireEnable) {
        return null;
    }

    @Override
    public Event reloadChoice(ArrayList<String> reloadableWeapons) {
        return null;
    }

    @Override
    public Event respawnChoice(ArrayList<String> powerUpNames, ArrayList<CubeColour> powerUpColours) {
        return null;
    }

    @Override
    public Event positionMoveChoice(int[] possibleSquareX, int[] possibleSquareY) {
        return null;
    }

    @Override
    public Event positionGrabChoice(int[] possibleSquareX, int[] possibleSquareY) {
        return null;
    }

    @Override
    public Event weaponChoice(ArrayList<String> availableWeapons) {
        return null;
    }

    @Override
    public Event weaponEffectChoice(boolean[] availableWeaponEffects) {
        return null;
    }

    @Override
    public Event weaponTargetChoice(ArrayList<Character> availableTargets) {
        return null;
    }

    @Override
    public Event effectPaymentChoice() {
        return null;
    }

    @Override
    public Event targetPowerUpChoice() {
        return null;
    }

    @Override
    public Event powerUpChoice(ArrayList<String> powerUpNames, ArrayList<CubeColour> powerUpColours) {
        return null;
    }

    @Override
    public void positionChoice() {

    }
    @Override
    public void printScreen() {

    }

}
