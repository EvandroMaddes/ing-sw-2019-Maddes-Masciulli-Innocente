package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.*;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.graph.CLIMap;
import it.polimi.ingsw.view.cli.graph.Color;
import it.polimi.ingsw.view.cli.graph.Title;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

//todo deve resettare i colori del terminale!
public class CLI extends RemoteView {


    private CLIMap map = new CLIMap();
    private Map<Character, String> mapCharacterNameColors = new EnumMap<Character,String>(Character.class);
    //todo map tra colore arma e nome arma
    // todo map tra powerUp e relativo colore




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
        userInput[0] = CLIHandler.stringPrintAndRead("Insert your Username:");
        setUser(userInput[0]);
        userInput[1]="";
        while(!(userInput[1].equalsIgnoreCase("RMI")||userInput[1].equalsIgnoreCase("SOCKET"))) {
            userInput[1] = CLIHandler.stringPrintAndRead("Choose one of the available connection, type:\n\nRMI\tor\tSOCKET\n");
        }
        userInput[2] = CLIHandler.stringPrintAndRead("Insert the Server IP Address:");
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
                CLIHandler.arrayPrint(cliCharacters);
                String chosenStringCharacter = CLIHandler.stringRead();
                chosenCharacter = Character.valueOf(chosenStringCharacter.toUpperCase());
            }catch(IllegalArgumentException e){
                chosenCharacter = null;
            }

        }
        return new CharacterChoiceEvent(getUser(),chosenCharacter);
    }

    /**
     *
     * @return
     */
    @Override
    public Event gameChoice() {
        int map = 5;
        while (map == 404) {
            try {


                System.out.println("option 0 for twelve squares" +
                        "\noption 1 for eleven squares" +
                        "\noption 2 for eleven squares" +
                        "\noption 3 for ten squares(recommended for three players)");
                System.out.println("Choose a map from the following(select number):");
                        System.out.flush();

                map = CLIHandler.intRead();
            } catch (IllegalArgumentException e) {

                map = 404;
            }
        }


        //todo modalità selezionata
        GameChoiceEvent message = new GameChoiceEvent(getUser(), map,0);
        return message;
    }

    /**
     *
     * @param fireEnable
     * @return
     */
    @Override
    public Event actionChoice(boolean fireEnable) {
        int chosenAction = 404;
        while (chosenAction == 404){
            try {
                System.out.println(
                        "option 1 for MOVE"
                        +"\noption 2 for GRAB");
                if(fireEnable){
                    System.out.println("option 3 for SHOT");
                }
                System.out.println("option 4 for SKIP YOUR TURN"+"\nSelect one action:");

                System.out.flush();

                chosenAction = CLIHandler.intRead();
            }catch (IllegalArgumentException e){
                chosenAction = 404;
            }
        }

        Event message;
        if (chosenAction==4) {
            message = new SkipActionChoiceEvent(getUser());
        } else message = new ActionChoiceEvent(getUser(), chosenAction);;

        return message;
    }

    @Override
    public Event reloadChoice(ArrayList<String> reloadableWeapons) {
        String weaponSelected = null;
        while (weaponSelected==null) {
            try {
                System.out.println("You choose to reload ");
                CLIHandler.arrayPrint(reloadableWeapons);
                weaponSelected = CLIHandler.stringRead();
            } catch (IllegalArgumentException e) {
                weaponSelected = null;

            }
        }
        return new WeaponReloadChoiceEvent(getUser(),weaponSelected);
    }

    @Override
    public Event weaponEffectSquareChoice(int[] possibleSquareX, int[] possibleSquareY) {
        return null;
    }

    /**
     *
     * @param powerUpNames
     * @param powerUpColours
     * @return
     */
    @Override
    public Event respawnChoice(ArrayList<String> powerUpNames, ArrayList<CubeColour> powerUpColours) {
        //todo assocciare colore alle carte
        String chosenPowerUp = null;
        while (chosenPowerUp == null) {
            try {
                System.out.println("You should respwan");
                CLIHandler.arrayPrint(powerUpNames);
                chosenPowerUp =CLIHandler.stringRead().toUpperCase();
            } catch (IllegalArgumentException e) {
                chosenPowerUp = null;
            }

        }
        return new SpownChoiceEvent(getUser(), chosenPowerUp, powerUpColours.get(powerUpNames.indexOf(chosenPowerUp)));
    }

    /**
     *
     * @param possibleSquareX
     * @param possibleSquareY
     * @return
     */
    @Override
    public Event positionMoveChoice(int[] possibleSquareX, int[] possibleSquareY) {
        int[] chosenSquare = null;
        while (chosenSquare == null){
            try {


            chosenSquare = CLIHandler.coordinatePrintAndRead(possibleSquareX, possibleSquareY);
         }catch (IllegalArgumentException e){
                chosenSquare = null;

        }

        }
        return new MoveChoiceEvent(getUser(),chosenSquare[0],chosenSquare[1]);
    }

    /**
     *
     * @param possibleSquareX
     * @param possibleSquareY
     * @return
     */
    @Override
    public Event positionGrabChoice(int[] possibleSquareX, int[] possibleSquareY) {
        int[] chosenSquare = null;
        while (chosenSquare == null){
            try {


                chosenSquare = CLIHandler.coordinatePrintAndRead(possibleSquareX, possibleSquareY);
            }catch (IllegalArgumentException e){
                chosenSquare = null;

            }

        }
        return new GrabChoiceEvent(getUser(),chosenSquare[0],chosenSquare[1]);
    }

    @Override
    public Event weaponDiscardChoice(ArrayList<String> yourWeapons) {
        String weaponSelected = null;
        while (weaponSelected==null) {
            try {
                System.out.println("You choose to reload ");
                CLIHandler.arrayPrint(yourWeapons);
                weaponSelected = CLIHandler.stringRead();
            } catch (IllegalArgumentException e) {
                weaponSelected = null;

            }
        }
        return new WeaponDiscardChoice(getUser(),weaponSelected);
    }

    /**
     *
     * @param availableWeapons
     * @return
     */
    @Override
    public Event weaponChoice(ArrayList<String> availableWeapons) {
        String weaponSelected = null;
        while (weaponSelected==null) {
            try {
                System.out.println("You choose to fire ");
                CLIHandler.arrayPrint(availableWeapons);
                weaponSelected = CLIHandler.stringRead();
            } catch (IllegalArgumentException e) {
                weaponSelected = null;

            }
        }
        return new WeaponGrabChoiceEvent(getUser(),weaponSelected);
    }

    @Override
    public Event weaponEffectChoice(boolean[] availableWeaponEffects) {
        int effectChoice = 404;

        for (int i =0; i<= availableWeaponEffects.length;i++)
        {
            System.out.println("effetto "+i);
        }
        while (effectChoice == 404){
            try {

                System.out.flush();

                effectChoice = CLIHandler.intRead();
            }catch (IllegalArgumentException e){
                effectChoice = 404;
            }
        }

        return new WeaponEffectChioceEvent(getUser(),effectChoice);
    }

    /**
     *
     * @param availableTargets
     * @return
     */
    @Override
    public Event weaponTargetChoice(ArrayList<Character> availableTargets,int numTarget) {
        ArrayList<String> cliCharacters = new ArrayList<>();
        ArrayList<Character> targetCharacter = new ArrayList<>();
        for (Character currCharacter:availableTargets) {
            cliCharacters.add(mapCharacterNameColors.get(currCharacter)+currCharacter.name());
        }
        CLIHandler.arrayPrint(cliCharacters);
        Character chosenCharacter = null;
        for (int i=0; i<=numTarget; i++){
            while(chosenCharacter==null) {
                try {
                 String chosenStringCharacter = CLIHandler.stringRead();
                 chosenCharacter = Character.valueOf(chosenStringCharacter.toUpperCase());

             } catch (IllegalArgumentException e) {
                    chosenCharacter = null;
                }
                targetCharacter.add(chosenCharacter);
            }
        }

        return new WeaponTargetChoiceEvent(getUser(),targetCharacter );
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
