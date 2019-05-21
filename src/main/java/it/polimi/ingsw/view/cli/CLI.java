package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.CharacterChoiceEvent;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.graph.CLIMap;
import it.polimi.ingsw.view.cli.graph.Color;
import it.polimi.ingsw.view.cli.graph.Title;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;
//todo deve resettare i colori del terminale!
public class CLI extends RemoteView {


    private CLIMap map = new CLIMap();
    private Map<Character, String> mapCharacterNameColors = new EnumMap<Character,String>(Character.class);



    public CLI() {
        mapCharacterNameColors.put(Character.D_STRUCT_OR, Color.ANSI_YELLOW.escape());
        mapCharacterNameColors.put(Character.BANSHEE, Color.ANSI_BLUE.escape());
        mapCharacterNameColors.put(Character.DOZER, Color.ANSI_WHITE.escape());
        mapCharacterNameColors.put(Character.VIOLET, Color.ANSI_PURPLE.escape());
        mapCharacterNameColors.put(Character.SPROG, Color.ANSI_GREEN.escape());
    }



    @Override
    public String[] gameInit() {
        Title.printTitle();
        String[] userInput = new String[3];
        userInput[0] = CLIHandler.printAndRead("Insert your Username:");
        setUser(userInput[0]);
        userInput[1]="";
        while(!(userInput[1].equalsIgnoreCase("RMI")||userInput[1].equalsIgnoreCase("SOCKET"))) {
            userInput[1] = CLIHandler.printAndRead("Choose one of the available connection, type:\n\nRMI\tor\tSOCKET\n");
        }
        userInput[2] = CLIHandler.printAndRead("Insert the Server IP Address:");
        return userInput;
    }


    @Override
    public Event characterChoice(ArrayList<Character> availableCharacters) {

        ArrayList<String> cliCharacters = new ArrayList<>();
        for (Character currCharacter:availableCharacters) {
            cliCharacters.add(mapCharacterNameColors.get(currCharacter)+currCharacter.name());
        }
        Character chosenCharacter = null;
        while(chosenCharacter==null) {
            try{
                String chosenStringCharacter = CLIHandler.arrayPrintAndRead(cliCharacters);
                chosenCharacter = Character.valueOf(chosenStringCharacter.toUpperCase());
            }catch(IllegalArgumentException e){
                chosenCharacter = null;
            }

        }
        return new CharacterChoiceEvent(getUser(),chosenCharacter);
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
        map.plot();
    }
}
