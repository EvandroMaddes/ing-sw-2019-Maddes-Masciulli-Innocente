package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.Map;

public class GUI extends RemoteView {


    @Override
    public Event positionUpdate(Character currCharacter, int x, int y) {
        return null;
    }





    @Override
    public String[] gameInit() {
        return new String[0];
    }

    @Override
    public Event characterChoice(ArrayList<Character> availableCharacters) {
        return null;
    }

    @Override
    public Event weaponTargetChoice(ArrayList<Character> availableTargets, int numTarget) {
        return null;
    }

    @Override
    public Event weaponEffectSquareChoice(int[] possibleSquareX, int[] possibleSquareY) {
        return null;
    }

    @Override
    public Event weaponGrabChoice(ArrayList<String> availableWeapon) {
        return null;
    }

    @Override
    public Event removeAmmoTileUpdate(int x, int y) {
        return null;
    }

    @Override
    public Event addAmmoTileUpdate(int x, int y, String fistColour, String secondColour, String thirdColour) {
        return null;
    }

    @Override
    public Event weaponDiscardChoice(ArrayList<String> yourWeapon) {
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
    public Event respawnChoice(String[] powerUpNames, CubeColour[] powerUpColours) {
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

    @Override
    public Event newPlayerJoinedUpdate(String newPlayer) {
        return null;
    }

    @Override
    public Event PlayerBoardUpdate(Character currCharacter, Character hittingCharacter, int damageToken, int markNumber) {
        return null;
    }

    @Override
    public Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, String[] color) {
        return null;
    }

    @Override
    public Event playerAmmoUpdate(Character currCharacter, ArrayList<AmmoCube> ammo) {
        return null;
    }

    @Override
    public Event playerWeaponUpdate(Character currCharacter, String[] weapons) {
        return null;
    }

    @Override
    public Event gameTrackSkullUpdate(Character currCharacter, int skullNumber, Character killerCharacter) {
        return null;
    }
}
