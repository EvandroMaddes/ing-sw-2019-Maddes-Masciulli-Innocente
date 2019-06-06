package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.*;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.graph.*;

import java.util.*;

//todo deve resettare i colori del terminale!
public class CLI extends RemoteView {

    private CLIDisplay display;//la map sara sostiyuita da display.getmap, idem per playerBoard


    // private ArrayList<CLIPlayerBoard> playerBoards;//una per ogni player
    private Map<Character, String> mapCharacterNameColors = new EnumMap<Character, String>(Character.class);
    //todo map tra colore arma e nome arma
    // todo map tra powerUp e relativo colore


    public CLI() {
        mapCharacterNameColors.put(Character.D_STRUCT_OR, Color.ANSI_YELLOW.escape());
        mapCharacterNameColors.put(Character.BANSHEE, Color.ANSI_BLUE.escape());
        mapCharacterNameColors.put(Character.DOZER, Color.ANSI_WHITE.escape());
        mapCharacterNameColors.put(Character.VIOLET, Color.ANSI_PURPLE.escape());
        mapCharacterNameColors.put(Character.SPROG, Color.ANSI_GREEN.escape());
        display = new CLIDisplay();
    }


    public Map<Character, String> getMapCharacterNameColors() {
        return mapCharacterNameColors;
    }

    /**
     * @return
     */
    @Override
    public String[] gameInit() {
        Title.printTitle();
        String[] userInput = new String[3];
        userInput[0] = CLIHandler.stringPrintAndRead("Insert your Username:");
        setUser(userInput[0]);
        userInput[1] = "";
        while (!(userInput[1].equalsIgnoreCase("RMI") || userInput[1].equalsIgnoreCase("SOCKET"))) {
            userInput[1] = CLIHandler.stringPrintAndRead("Choose one of the available connection, type:\n\nRMI\tor\tSOCKET\n");
        }
        userInput[2] = CLIHandler.stringPrintAndRead("Insert the Server IP Address:");
        return userInput;
    }

    /**
     * @param availableCharacters
     * @return
     */
    @Override
    public Event characterChoice(ArrayList<Character> availableCharacters) {

        ArrayList<String> cliCharacters = new ArrayList<>();
        for (Character currCharacter : availableCharacters) {
            cliCharacters.add(mapCharacterNameColors.get(currCharacter) + currCharacter.name());
        }
        Character chosenCharacter = null;
        while (chosenCharacter == null) {
            try {
                CLIHandler.arrayPrint(cliCharacters);
                String chosenStringCharacter = CLIHandler.stringRead();
                chosenCharacter = Character.valueOf(chosenStringCharacter.toUpperCase());
            } catch (IllegalArgumentException e) {
                chosenCharacter = null;
            }

        }
        return new CharacterChoiceEvent(getUser(), chosenCharacter);
    }

    /**
     * @return
     */
    @Override
    public Event gameChoice() {
        int map = 404;
        while (map == 404) {
            try {

                while (map < 0 || map > 3) {

                    System.out.println("option 0 for twelve squares" +
                            "\noption 1 for eleven squares" +
                            "\noption 2 for eleven squares" +
                            "\noption 3 for ten squares(recommended for three players)");
                    System.out.println("Choose a map from the following(select number):");
                    System.out.flush();

                    map = CLIHandler.intRead();
                }
                display.setMap(new CLIMap(map));
            } catch (IllegalArgumentException e) {

                map = 404;
            }
        }


        //todo modalità selezionata
        GameChoiceEvent message = new GameChoiceEvent(getUser(), map, 0);
        return message;
    }


    /**
     * @param fireEnable
     * @return
     */
    @Override
    public Event actionChoice(boolean fireEnable) {
        int chosenAction = 404;
        while (chosenAction == 404) {
            try {
                System.out.println(
                        "option 1 for MOVE"
                                + "\noption 2 for GRAB");
                if (fireEnable) {
                    System.out.println("option 3 for SHOT");
                }
                System.out.println("option 4 for SKIP YOUR TURN" + "\nSelect one action:");

                System.out.flush();

                chosenAction = CLIHandler.intRead();
            } catch (IllegalArgumentException e) {
                chosenAction = 404;
            }
        }

        Event message;
        if (chosenAction == 4) {
            message = new SkipActionChoiceEvent(getUser());
        } else message = new ActionChoiceEvent(getUser(), chosenAction);
        ;

        return message;
    }

    /**
     * @param reloadableWeapons
     * @return
     */
    @Override
    public Event reloadChoice(ArrayList<String> reloadableWeapons) {
        String weaponSelected = null;
        while (weaponSelected == null) {
            try {
                CLIHandler.arrayPrint(reloadableWeapons);
                System.out.println("Choose weapon to reload:  ");
                weaponSelected = CLIHandler.stringRead().toUpperCase();
            } catch (IllegalArgumentException e) {
                weaponSelected = null;

            }
        }

        Event message;
        if (!reloadableWeapons.contains(weaponSelected)) {
            message = new SkipActionChoiceEvent(getUser());
        } else {
            message = new WeaponReloadChoiceEvent(getUser(), weaponSelected);
        }

        return message;
    }

    @Override
    public Event weaponEffectSquareChoice(int[] possibleSquareX, int[] possibleSquareY) {
        return null;
    }

    /**
     * @param powerUpNames
     * @param powerUpColours
     * @return
     */
    @Override
    public Event respawnChoice(String[] powerUpNames, CubeColour[] powerUpColours) {
        //todo assocciare colore alle carte
        ArrayList<String> powerUpList = new ArrayList<>(Arrays.asList(powerUpNames));
        ArrayList<CubeColour> coloursList = new ArrayList<>(Arrays.asList(powerUpColours));
        String chosenPowerUp = null;
        while (chosenPowerUp == null) {
            try {
                System.out.println("You should respawn");
                CLIHandler.arrayPrint(powerUpList);
                chosenPowerUp = CLIHandler.stringRead().toUpperCase();
            } catch (IllegalArgumentException e) {
                chosenPowerUp = null;
            }

        }
        return new SpownChoiceEvent(getUser(), chosenPowerUp, coloursList.get(powerUpList.indexOf(chosenPowerUp)));
    }

    /**
     * @param possibleSquareX
     * @param possibleSquareY
     * @return
     */
    @Override
    public Event positionMoveChoice(int[] possibleSquareX, int[] possibleSquareY) {
        int[] chosenSquare = null;
        while (chosenSquare == null) {
            try {


                chosenSquare = CLIHandler.coordinatePrintAndRead(possibleSquareX, possibleSquareY);
            } catch (IllegalArgumentException e) {
                chosenSquare = null;

            }

        }
        return new MoveChoiceEvent(getUser(), chosenSquare[0], chosenSquare[1]);
    }

    /**
     * @param possibleSquareX
     * @param possibleSquareY
     * @return
     */
    @Override
    public Event positionGrabChoice(int[] possibleSquareX, int[] possibleSquareY) {
        int[] chosenSquare = null;
        while (chosenSquare == null) {
            try {


                chosenSquare = CLIHandler.coordinatePrintAndRead(possibleSquareX, possibleSquareY);
            } catch (IllegalArgumentException e) {
                chosenSquare = null;

            }

        }
        return new GrabChoiceEvent(getUser(), chosenSquare[0], chosenSquare[1]);
    }

    /**
     * @param yourWeapons
     * @return
     */
    @Override
    public Event weaponDiscardChoice(ArrayList<String> yourWeapons) {
        String weaponSelected = null;
        while (weaponSelected == null) {
            try {
                System.out.println("You choose to reload ");
                CLIHandler.arrayPrint(yourWeapons);
                weaponSelected = CLIHandler.stringRead();
            } catch (IllegalArgumentException e) {
                weaponSelected = null;

            }
        }
        return new WeaponDiscardChoice(getUser(), weaponSelected);
    }

    /**
     * @param availableWeapons
     * @return
     */
    @Override
    public Event weaponChoice(ArrayList<String> availableWeapons) {
        String weaponSelected = null;
        while (weaponSelected == null) {
            try {
                System.out.println("You choose to fire ");
                CLIHandler.arrayPrint(availableWeapons);
                weaponSelected = CLIHandler.stringRead();
            } catch (IllegalArgumentException e) {
                weaponSelected = null;

            }
        }
        return new WeaponChoiceEvent(getUser(), weaponSelected);
    }

    /**
     * @param availableWeapon
     * @return
     */
    @Override
    public Event weaponGrabChoice(ArrayList<String> availableWeapon) {
        String weaponSelected = null;
        while (weaponSelected == null) {
            try {
                System.out.println("You choose to grab");
                CLIHandler.arrayPrint(availableWeapon);
                weaponSelected = CLIHandler.stringRead();
            } catch (IllegalArgumentException e) {
                weaponSelected = null;

            }
        }
        return new WeaponGrabChoiceEvent(getUser(), weaponSelected);
    }

    /**
     * @param availableWeaponEffects
     * @return
     */
    @Override
    public Event weaponEffectChoice(boolean[] availableWeaponEffects) {
        int effectChoice = 404;

        for (int i = 0; i < availableWeaponEffects.length && availableWeaponEffects[i] == true; i++) // print only available effects
        {
            System.out.println("effect " + i);
        }
        while (effectChoice == 404) {
            try {

                System.out.flush();

                effectChoice = CLIHandler.intRead();
            } catch (IllegalArgumentException e) {
                effectChoice = 404;
            }
        }

        return new WeaponEffectChioceEvent(getUser(), effectChoice);
    }

    /**
     * @param availableTargets
     * @return
     */
    @Override
    public Event weaponTargetChoice(ArrayList<Character> availableTargets, int numTarget) {
        ArrayList<String> cliCharacters = new ArrayList<>();
        ArrayList<Character> targetCharacter = new ArrayList<>();
        for (Character currCharacter : availableTargets) {
            cliCharacters.add(mapCharacterNameColors.get(currCharacter) + currCharacter.name());
        }
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Max targets: " + numTarget);
        CLIHandler.arrayPrint(cliCharacters);
        Character chosenCharacter = null;
        for (int i = 0; i < numTarget; i++) {
            chosenCharacter = null;
            while (chosenCharacter == null) {
                try {
                    String chosenStringCharacter = CLIHandler.stringRead();
                    chosenCharacter = Character.valueOf(chosenStringCharacter.toUpperCase());

                } catch (IllegalArgumentException e) {
                    chosenCharacter = null;
                }
                targetCharacter.add(chosenCharacter);
            }
        }

        return new WeaponPlayersTargetChoiceEvent(getUser(), targetCharacter);
    }

    @Override
    public Event powerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours) {

        String choice;
        boolean done = false;
        int index = powerUpNames.length;

        ArrayList<String> powerUpList = new ArrayList<>(Arrays.asList(powerUpNames));
        CLIHandler.arrayPrint(powerUpList);
        System.out.println("select your PowerUp: ");
        choice = CLIHandler.stringRead();

        while (!done) {
            if (powerUpNames[index].equals(choice)) {
                done = true;
            } else {
                index--;
            }
        }

        return new PowerUpChoiceEvent(getUser(), choice, powerUpColours[index]);
    }


    @Override
    public void printScreen() {
        //todo interazione con aggiornamento armi sullo spqwn square
        display.printDisplay();
    }

    /**
     * Every tima one player joins the game it's notified to other player
     *
     * @param newPlayer
     * @return
     */
    @Override
    public Event newPlayerJoinedUpdate(String newPlayer) {


        System.out.println("New player joined the game:" + newPlayer);
        //TODO   "character choice:"+mapCharacterNameColors.get(newPlayer));

        return new UpdateChoiceEvent(getUser());
    }

    /**
     * @param x
     * @param y
     * @param fistColour
     * @param secondColour
     * @param thirdColour
     * @return
     */
    @Override
    public Event addAmmoTileUpdate(int x, int y, String fistColour, String secondColour, String thirdColour) {
        String[] color = {fistColour, secondColour, null};
        CLIPrintableElement currElement;
        if (thirdColour.equals("POWERUP")) {

            currElement = new CLIPrintableElement(true, color);
        } else {
            color[2] = thirdColour;
            currElement = new CLIPrintableElement(false, color);
        }
        display.getMap().updateResource(currElement, x, y);
        return new UpdateChoiceEvent(getUser());
    }

    /**
     * @param x
     * @param y
     * @return
     */
    @Override
    public Event removeAmmoTileUpdate(int x, int y) {
        CLIPrintableElement currElement = new CLIPrintableElement(false);
        display.getMap().updateResource(currElement, x, y);
        return new UpdateChoiceEvent(getUser());
    }

    /**
     * @param currCharacter
     * @param x
     * @param y
     * @return
     */
    @Override
    public Event positionUpdate(Character currCharacter, int x, int y) {
        CLIPrintableElement currElement = new CLIPrintableElement(currCharacter, mapCharacterNameColors.get(currCharacter));
        display.getMap().updateResource(currElement, x, y);
        return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event playerBoardUpdate(Character currCharacter, Character hittingCharacter, int damageToken, int markNumber) {
        display.getPlayerBoard(currCharacter).markDamageUpdate(damageToken, markNumber, hittingCharacter);
        return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, CubeColour[] colour) {
        for (int i = 0; i < powerUp.length; i++) {
            powerUp[i] = findColorEscape(colour[i].name()) + powerUp[i];
        }
        display.getPlayerBoard(currCharacter).gadgetsUpdate('P', powerUp);

        return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event playerWeaponUpdate(Character currCharacter, String[] weapons) {

        display.getPlayerBoard(currCharacter).gadgetsUpdate('W', weapons);

        return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event playerAmmoUpdate(Character currCharacter, ArrayList<AmmoCube> ammo) {
        int size = ammo.size();

        String[] ammoString = new String[size];
        int i = 0;

        for (AmmoCube ammoCube : ammo
        ) {

            ammoString[i] = findColorEscape(ammoCube.getColour().name()) + "█";
            i++;
        }

        display.getPlayerBoard(currCharacter).gadgetsUpdate('A', ammoString);

        return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event gameTrackSkullUpdate(Character currCharacter, int damageTokenNumber, Character killerCharacter) {
        String[] skull = new String[1];
        skull[0] = Color.ANSI_RED.escape() + "☠";

        /*if(skullNumber==2){
            skull[1]= "☠";
        }
        */
        display.getPlayerBoard(currCharacter).gadgetsUpdate('S', skull);
        display.getGameTrack().removeSkull(damageTokenNumber, mapCharacterNameColors.get(killerCharacter));
        return new UpdateChoiceEvent(getUser());
    }

    /**
     * It repaces weapons on spawn square
     *
     * @param x      coordinate (row)
     * @param y      coordinate(column)
     * @param weapon
     * @return
     */
    @Override
    public Event weaponReplaceUpdate(int x, int y, String[] weapon) {
        display.weaponsSpawnSquare(x, y, weapon);
        return new UpdateChoiceEvent(getUser());
    }

    private String findColorEscape(String colourString) {
        String colourEscape = Color.ANSI_BLACK_BACKGROUND.escape();
        if (colourString.equalsIgnoreCase("RED")) {
            colourEscape = colourEscape + Color.ANSI_RED.escape();
        } else if (colourString.equalsIgnoreCase("BLUE")) {
            colourEscape = colourEscape + Color.ANSI_BLUE.escape();
        } else {
            colourEscape = colourEscape + Color.ANSI_YELLOW;
        }
        return colourEscape;
    }

    @Override
    public Event newtonTargetChoice(ArrayList<Character> availableTargets, int numTarget) {
        ArrayList<String> cliCharacters = new ArrayList<>();
        ArrayList<Character> targetCharacter = new ArrayList<>();
        for (Character currCharacter : availableTargets) {
            cliCharacters.add(mapCharacterNameColors.get(currCharacter) + currCharacter.name());
        }
        CLIHandler.arrayPrint(cliCharacters);
        Character chosenCharacter = null;
        while (chosenCharacter == null) {
            try {
                String chosenStringCharacter = CLIHandler.stringRead();
                chosenCharacter = Character.valueOf(chosenStringCharacter.toUpperCase());

            } catch (IllegalArgumentException e) {
                chosenCharacter = null;
            }
        }

        return new NewtonPlayerTargetChoiceEvent(getUser(), chosenCharacter);
    }


    /**
     * it selects the destination square of target or newton powerUp
     * @param possibleSquareX
     * @param possibleSquareY
     * @return
     */
    @Override
    public Event newtonTeleporterTargetSquareChoice(int[] possibleSquareX, int[] possibleSquareY) {
        int[] chosenSquare = null;
        while (chosenSquare == null) {
            try {


                chosenSquare = CLIHandler.coordinatePrintAndRead(possibleSquareX, possibleSquareY);
            } catch (IllegalArgumentException e) {
                chosenSquare = null;

            }

        }
        return new PowerUpSquareTargetChoiceEvent(getUser(), chosenSquare[0], chosenSquare[1]);

    }

    /**
     * it return how a player pay the cost of effect selected(weapon effect)
     * @param powerUpNames
     * @param powerUpColours
     * @param minimumPowerUpRequest
     * @param maximumPowerUpRequest
     * @return
     */
    /*****REMIND***
     * int arrays are built like that:
     * [0] - # Red
     * [1] - # Yellow
     * [2] - # Blue
     */
    @Override
    public Event weaponEffectPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest){
        Event message;
        int[] index = payment(powerUpNames,powerUpColours,minimumPowerUpRequest,maximumPowerUpRequest);
        String[] nameSelected = new String[maximumPowerUpRequest.length];
        CubeColour[] colourSelected = new CubeColour[maximumPowerUpRequest.length];
        if(index ==null){
            message = new WeaponEffectPaymentChoiceEvent(getUser(),null,null);
        }else {
            for (int i = 0; i < index.length; i++) {
                nameSelected[i] = powerUpNames[index[i]];
                colourSelected[i] = colourSelected[index[i]];
            }
            message = new WeaponEffectPaymentChoiceEvent(getUser(), nameSelected, colourSelected);
        }
        return message;
    }

    /**
     * it return how a player pay the weapon GRAB cost
     * @param powerUpNames
     * @param powerUpColours
     * @param minimumPowerUpRequest
     * @param maximumPowerUpRequest
     * @return
     */
    @Override
    public Event weaponGrabPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        Event message;
        int[] index = payment(powerUpNames,powerUpColours,minimumPowerUpRequest,maximumPowerUpRequest);
        String[] nameSelected = new String[maximumPowerUpRequest.length];
        CubeColour[] colourSelected = new CubeColour[maximumPowerUpRequest.length];
        if(index ==null){
            message = new WeaponGrabPaymentChoiceEvent(getUser(),null,null);
        }else {
            for (int i = 0; i < index.length; i++) {
                nameSelected[i] = powerUpNames[index[i]];
                colourSelected[i] = colourSelected[index[i]];
            }
            message = new WeaponGrabPaymentChoiceEvent(getUser(), nameSelected, colourSelected);
        }
        return message;
    }

    /**
     * use to catch player choice to pay
     * @param powerUpNames
     * @param powerUpColours
     * @param minimumPowerUpRequest
     * @param maximumPowerUpRequest
     * @return
     */
    private int[] payment(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest){
        int[] selected = new int[powerUpNames.length];
        int index ;
        String choice;


        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Would you Like to pay with PowerUP? [Y/N]");
        choice = CLIHandler.stringRead();

        if (choice.equals("Y")) {
            System.out.print(Color.ANSI_GREEN.escape() + "Minimum powerUP request: ");
            for (int i = 0; i < minimumPowerUpRequest.length; i++) {

                if (minimumPowerUpRequest[i] == 0) {
                    System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_RED.escape() + " RED");
                }else if (minimumPowerUpRequest[i] == 1){
                    System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_YELLOW.escape() + " YELLOW");

                }
                else if (minimumPowerUpRequest[i] == 2) {
                    System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_BLUE.escape() + " BLUE");
                }
            }

            System.out.print(Color.ANSI_GREEN.escape() + "\nMax powerUP request: ");
            for (int i = 0; i < maximumPowerUpRequest.length; i++) {

                if (maximumPowerUpRequest[i] == 0) {
                    System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_RED.escape() + " RED");
                }else if (maximumPowerUpRequest[i] == 1){
                    System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_YELLOW.escape() + " YELLOW");

                }
                else if (maximumPowerUpRequest[i] == 2) {
                    System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_BLUE.escape() + " BLUE");
                }
            }
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+"\n");

            for(int i=0;i<powerUpNames.length;i++){
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+findColorEscape(powerUpColours[i].toString())+powerUpNames[i]+" OPTION "+i);
            }

            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Select your powerUp:[N" +
                    "404 to terminate]");
            for (int i = 0; i < powerUpNames.length; i++) {
                index = 600;
                while (index == 600) {
                    try {
                        index = CLIHandler.intRead();


                    } catch (IllegalArgumentException e) {
                        index = 404;
                    }
                    if (index==404){
                        i=powerUpNames.length;
                    }else {
                        selected[i] = index;
                    }
                }
            }
        }
        else if(choice.equals("N"))
            selected = null;
        return selected;
    }

    /**
     *
     * @param powerUpNames
     * @param powerUpColours
     * @param minimumPowerUpRequest
     * @param maximumPowerUpRequest
     * @return
     */
    @Override
    public Event weaponReloadPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest){
        Event message;
        int[] index = payment(powerUpNames,powerUpColours,minimumPowerUpRequest,maximumPowerUpRequest);
        String[] nameSelected = new String[maximumPowerUpRequest.length];
        CubeColour[] colourSelected = new CubeColour[maximumPowerUpRequest.length];
        if(index ==null){
            message = new WeaponReloadPaymentChoiceEvent(getUser(),null,null);
        }else {
            for (int i = 0; i < index.length; i++) {
                nameSelected[i] = powerUpNames[index[i]];
                colourSelected[i] = colourSelected[index[i]];
            }
            message = new WeaponReloadPaymentChoiceEvent(getUser(), nameSelected, colourSelected);
        }
        return message;
    }

}