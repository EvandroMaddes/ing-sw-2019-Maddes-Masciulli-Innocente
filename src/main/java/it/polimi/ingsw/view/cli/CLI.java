package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.*;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.graph.*;

import java.util.*;

import static it.polimi.ingsw.model.player.Character.VIOLET;

//todo deve resettare i colori del terminale!
public class CLI extends RemoteView {

    private CLIDisplay display;//la map sara sostiyuita da display.getmap, idem per playerBoard


    // private ArrayList<CLIPlayerBoard> playerBoards;//una per ogni player
    private Map<Character, String> mapCharacterNameColors = new EnumMap<Character, String>(Character.class);
    //todo map tra colore arma e nome arma
    // todo map tra powerUp e relativo colore

    /**
     * Constructor:
     * -mapping between colour and character
     * -initialize display
     */
    public CLI() {
        mapCharacterNameColors.put(Character.D_STRUCT_OR, Color.ANSI_YELLOW.escape());
        mapCharacterNameColors.put(Character.BANSHEE, Color.ANSI_BLUE.escape());
        mapCharacterNameColors.put(Character.DOZER, Color.ANSI_WHITE.escape());
        mapCharacterNameColors.put(Character.VIOLET, Color.ANSI_PURPLE.escape());
        mapCharacterNameColors.put(Character.SPROG, Color.ANSI_GREEN.escape());
        display = new CLIDisplay();
    }

    /**
     * getter of map between character and colour
     * @return map(character-colour)
     */
    public Map<Character, String> getMapCharacterNameColors() {
        return mapCharacterNameColors;
    }

    /**
     * it prints title "ADRENALINE" and catch user choice for username and connection(RMI/SOCKET)
     * @return user choices
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
     * user choice for character
     * @param availableCharacters
     * @return event that contains player's choice
     */
    @Override
    public Event characterChoice(ArrayList<Character> availableCharacters) {
        ArrayList<String> stringCharacter = new ArrayList<>();
        ArrayList<String> cliCharacters = new ArrayList<>();
        String chosenStringCharacter= "init";
        int index = 404;
        for (Character currCharacter : availableCharacters) {
            cliCharacters.add(mapCharacterNameColors.get(currCharacter) + currCharacter.name());
            stringCharacter.add(currCharacter.name().toUpperCase());
        }
        Character chosenCharacter = null;
        while (!stringCharacter.contains(chosenStringCharacter.toUpperCase())) {
            try {
                CLIHandler.arrayPrint(cliCharacters);
                 chosenStringCharacter = CLIHandler.stringRead();
            } catch (IllegalArgumentException e) {
                chosenCharacter = null;
            }

        }
        index = stringCharacter.indexOf(chosenStringCharacter.toUpperCase());
        chosenCharacter = availableCharacters.get(index);

        return new CharacterChoiceEvent(getUser(), chosenCharacter);
    }

    /**
     * user choice for map a
     * @return event that contains player's choice
     */
    @Override
    public Event gameChoice() {
        int map = 404;
        while (map == 404) {
            try {

                while (map < 0 || map > 3) {

                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"option 0 for twelve squares" +
                            "\noption 1 for eleven squares" +
                            "\noption 2 for eleven squares" +
                            "\noption 3 for ten squares(recommended for three players)");
                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Choose a map from the following(select number):");
                    System.out.flush();

                    map = CLIHandler.intRead();
                }
                display.setMap(new CLIMap(map));
            } catch (IllegalArgumentException e) {

                map = 404;
            }
        }


        GameChoiceEvent message = new GameChoiceEvent(getUser(), map, 0);//TODO cambiare messaggio:togliere scelta mdalità
        return message;
    }


    /**
     *User choose an action at the beginning of his own turn
     * @param fireEnable true if player can use action fire
     * @return event that contains player's choice
     */
    @Override
    public Event actionChoice(boolean fireEnable) {
        int chosenAction = 404;
        while (chosenAction <=1 || chosenAction>=5) {
            try {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+
                        "option 1 for MOVE"
                                + "\noption 2 for GRAB");
                if (fireEnable) {
                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"option 3 for SHOT");
                }
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"option 4 for SKIP YOUR TURN" + "\nSelect one action:");

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


        return message;
    }

    /**
     * user choose weapon to reload
     * @param reloadableWeapons list of weapon that can be reloaded
     * @return event that contains player's choice
     */
    @Override
    public Event reloadChoice(ArrayList<String> reloadableWeapons) {
        String weaponSelected = null;
        while (weaponSelected == null) {
            try {
                CLIHandler.arrayPrint(reloadableWeapons);
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

    /**
     *User choose square target for his selected effect
     * @param possibleSquareX
     * @param possibleSquareY
     * @return
     */
    @Override
    public Event weaponEffectSquareChoice(int[] possibleSquareX, int[] possibleSquareY) {
       PositionChoiceEvent message =(PositionChoiceEvent) positionMoveChoice(possibleSquareX,possibleSquareY);
       Event choice = new WeaponSquareTargetChoiceEvent(getUser(), message.getPositionX(),message.getPositionY());

        return choice;

    }

    /**
     * User choose which powerUp discard
     * @param powerUpNames list of powerUp
     * @param powerUpColours color of powerUp
     * @return event that contains player's choice
     */
    @Override
    public Event respawnChoice(String[] powerUpNames, CubeColour[] powerUpColours) {

        int chosenPowerUp = 404;
        while (chosenPowerUp<0 || chosenPowerUp>powerUpNames.length) {
            try {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"It's time to respawn!");
                for(int i=0;i<powerUpNames.length;i++){
                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+findColorEscape(powerUpColours[i].toString())+powerUpNames[i]+" OPTION "+i);
                }
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Discard your powerUp:[option number]");
                chosenPowerUp = CLIHandler.intRead();
                if(chosenPowerUp<0 || chosenPowerUp>powerUpNames.length){

                }
            } catch (IllegalArgumentException e) {
                chosenPowerUp = 404;
            }

        }
        return new SpownChoiceEvent(getUser(), powerUpNames[chosenPowerUp], powerUpColours[chosenPowerUp]);
    }

    /**
     * user choose where move his character
     * @param possibleSquareX column
     * @param possibleSquareY row
     * @return event that contains player's choice
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
     *User choose where move his character and grab item on new position
     * @param possibleSquareX column
     * @param possibleSquareY row
     * @return event that contains player's choice
     */
    @Override
    public Event positionGrabChoice(int[] possibleSquareX, int[] possibleSquareY) {
        int[] chosenSquare = null;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Position for grab some item ");

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
     * user choose one weapon to discard
     * @param yourWeapons player's weapon
     * @return event that contains player's choice
     */
    @Override
    public Event weaponDiscardChoice(ArrayList<String> yourWeapons) {
        String weaponSelected = null;
        while (!yourWeapons.contains(weaponSelected)) {
            try {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You choose to discard one weapon:");
                CLIHandler.arrayPrint(yourWeapons);

                weaponSelected = CLIHandler.stringRead();

            } catch (IllegalArgumentException e) {
                weaponSelected = null;

            }
        }
        return new WeaponDiscardChoice(getUser(), weaponSelected);
    }

    /**
     * User choose weapon to fire
     * @param availableWeapons weapon loaded and ready to fire
     * @return event that contains player's choice
     */
    @Override
    public Event weaponChoice(ArrayList<String> availableWeapons) {
        String weaponSelected = null;
        while (!availableWeapons.contains(weaponSelected)) {
            try {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You choose to fire ");
                CLIHandler.arrayPrint(availableWeapons);
                weaponSelected = CLIHandler.stringRead();
            } catch (IllegalArgumentException e) {
                weaponSelected = null;

            }
        }
        return new WeaponChoiceEvent(getUser(), weaponSelected);
    }

    /**
     * User select one weapon to grab
     * @param availableWeapon weapon on spawn square
     * @return event that contains player's choice
     */
    @Override
    public Event weaponGrabChoice(ArrayList<String> availableWeapon) {
        String weaponSelected = null;
        while (!availableWeapon.contains(weaponSelected)) {
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
     * User choose at least one effect for his own weapon
     * @param availableWeaponEffects
     * @return event that contains player's choice
     */
    @Override
    public Event weaponEffectChoice(boolean[] availableWeaponEffects) {
        int effectChoice = 40;
        int first= 404, second=404 , third = 404;
        Event message;

        if(availableWeaponEffects[0] == true){
             first = 0;
        }

        if(availableWeaponEffects[1] == true){
             second = 1;
        }

        if(availableWeaponEffects[2] == true){
             third = 2;
        }


        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Available effect for your weapon:");

        for (int i = 0; i < availableWeaponEffects.length; i++) // print only available effects
        {
            if ( availableWeaponEffects[i] == true) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "effect " + i);
            }
        }
        while (!(effectChoice == first || effectChoice ==second || effectChoice== third)) {
            try {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Select at least one effect[number]");

                System.out.flush();

                effectChoice = CLIHandler.intRead();
            } catch (IllegalArgumentException e) {
                effectChoice = 404;
            }
        }
            message = new WeaponEffectChioceEvent(getUser(), effectChoice);

        return message;
    }

    /**
     * user select target to hit(characters)
     * @param availableTargets characters that can be hit
     * @return event that contains player's choice
     */
    @Override
    public Event weaponTargetChoice(ArrayList<Character> availableTargets, int numTarget) {
        ArrayList<String> cliColorCharacters = new ArrayList<String>();
        ArrayList<String > cliStringCharacter = new ArrayList<>();
        ArrayList<Character> targetCharacter = new ArrayList<Character>();
        for (Character currCharacter : availableTargets) {
            cliColorCharacters.add(mapCharacterNameColors.get(currCharacter) + currCharacter.name().toUpperCase());
            cliStringCharacter.add(currCharacter.name().toUpperCase());
        }
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Select yor targets to hit,max targets: " + numTarget+"[type END to select less then "+numTarget+" targets]");
        CLIHandler.arrayPrint(cliColorCharacters);

        Character chosenCharacter = null ;
        String chosenStringCharacter = "init";

        for(int i=0;i<=numTarget;i++){
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "next choice");

            do {
                try {
                    chosenStringCharacter = CLIHandler.stringRead();
                } catch (IllegalArgumentException e) {
                    availableTargets = null;

                }

            }while (!cliStringCharacter.contains(chosenStringCharacter.toUpperCase()) && !chosenStringCharacter.equalsIgnoreCase("end"));
            if (chosenStringCharacter.equalsIgnoreCase("end")) {
                    i = numTarget + 1;
                } else if(cliStringCharacter.contains(chosenStringCharacter.toUpperCase())){
                int index = cliStringCharacter.indexOf(chosenStringCharacter.toUpperCase());
                chosenCharacter = availableTargets.get(index);
                targetCharacter.add(chosenCharacter);
            }
        }

        return new WeaponPlayersTargetChoiceEvent(getUser(), targetCharacter);
    }

    /**
     *User choice one powerUp to use
     * @param powerUpNames name of powerUp available
     * @param powerUpColours color of powerUp available
     * @return event that contains player's choice
     */
    @Override
    public Event powerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours) {

        int chosenPowerUp = 404;
        while (chosenPowerUp<0 || chosenPowerUp>powerUpNames.length) {
            try {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Your powerUp!");
                for(int i=0;i<powerUpNames.length;i++){
                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+findColorEscape(powerUpColours[i].toString())+powerUpNames[i]+" OPTION "+i);
                }
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Select one powerUp:[option number]");
                chosenPowerUp = CLIHandler.intRead();
                if(chosenPowerUp<0 || chosenPowerUp>powerUpNames.length){

                }
            } catch (IllegalArgumentException e) {
                chosenPowerUp = 404;
            }

        }
        return new PowerUpChoiceEvent(getUser(),powerUpNames[chosenPowerUp], powerUpColours[chosenPowerUp]);
    }

    /**
     * Print screen updated
     */
    @Override
    public void printScreen() {
        //todo interazione con aggiornamento armi sullo spqwn square
        display.printDisplay();
    }

    /**
     * Every time one player joins the game it's notified to other player
     *
     * @param newPlayer
     * @return message notify the success of updating
     */
    @Override
    public Event newPlayerJoinedUpdate(String newPlayer) {

        //TODO questo messaggo viene inviato quando un player joina la partita o dopo aver scelto il proprio caracther?
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+mapCharacterNameColors.get(newPlayer)+"New player joined the game:" + newPlayer);

        return new UpdateChoiceEvent(getUser());
    }

    /**
     * @param x
     * @param y
     * @param fistColour
     * @param secondColour
     * @param thirdColour
     * @return message notify the success of updating
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

    /**
     *
     * @param currCharacter
     * @param hittingCharacter
     * @param damageToken
     * @param markNumber
     * @return
     */
    @Override
    public Event playerBoardUpdate(Character currCharacter, Character hittingCharacter, int damageToken, int markNumber) {
        display.getPlayerBoard(currCharacter).markDamageUpdate(damageToken, markNumber, hittingCharacter);
        return new UpdateChoiceEvent(getUser());
    }

    /**
     *
     * @param currCharacter
     * @param powerUp
     * @param colour
     * @return
     */
    @Override
    public Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, CubeColour[] colour) {
        for (int i = 0; i < powerUp.length; i++) {
            powerUp[i] = findColorEscape(colour[i].name()) + powerUp[i];
        }
        display.getPlayerBoard(currCharacter).gadgetsUpdate('P', powerUp);

        return new UpdateChoiceEvent(getUser());
    }

    /**
     *
     * @param currCharacter
     * @param weapons
     * @return
     */
    @Override
    public Event playerWeaponUpdate(Character currCharacter, String[] weapons) {

        display.getPlayerBoard(currCharacter).gadgetsUpdate('W', weapons);

        return new UpdateChoiceEvent(getUser());
    }

    /**
     *
     * @param currCharacter
     * @param ammo
     * @return
     */
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

    /**
     *
     * @param currCharacter
     * @param damageTokenNumber
     * @param killerCharacter
     * @return
     */
    @Override
    public Event gameTrackSkullUpdate(Character currCharacter, int damageTokenNumber, Character killerCharacter) {
        String[] skull = new String[1];
        skull[0] = Color.ANSI_RED.escape() + "☠";

        /*if(skullNumber==2){
            skull[1]= "☠";
        }
        */
        // Si assegnano i teschi al player esatto
        display.getPlayerBoard(currCharacter).gadgetsUpdate('S', skull);
        display.getGameTrack().removeSkull(damageTokenNumber, mapCharacterNameColors.get(killerCharacter));
        return new UpdateChoiceEvent(getUser());
    }

    /**
     * It replaces weapons on spawn square
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

       CharacterChoiceEvent message =(CharacterChoiceEvent)characterChoice(availableTargets);
       Event choice = new NewtonPlayerTargetChoiceEvent(getUser(),message.getChosenCharacter());


        return choice;
    }


    /**
     * it selects the destination square of target or newton powerUp
     * @param possibleSquareX column of possible square
     * @param possibleSquareY row of possible square
     * @return event that contains player's choice
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
     * @param powerUpNames list of power up's name available to use
     * @param powerUpColours list of power up's colour available to use
     * @param minimumPowerUpRequest if your ammo are not enough you must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return event that contains player's choice
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
     * @param powerUpNames list of power up's name available to use
     * @param powerUpColours list of power up's colour available to use
     * @param minimumPowerUpRequest if your ammo are not enough you must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return event that contains player's choice
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
     *
     * @param powerUpNames list of power up's name available to use
     * @param powerUpColours list of power up's colour available to use
     * @param minimumPowerUpRequest if your ammo are not enough you must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return event that contains player's choice
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

    /**
     * Select how to pay when you can choose on item from ammo or powerUp
     * @param usableAmmo
     * @param powerUpsType list of power up's name available to use
     * @param powerUpsColour list of power up's colour available to use
     * @return event that contains player's choice
     */
    @Override
    public Event genericPaymentChoice(boolean[] usableAmmo, String[] powerUpsType, CubeColour[] powerUpsColour) {
        int payChoice= 404 ;
        int validSelection = 1;
        boolean[] ammoChoice = {false,false,false};
        String powerUpChoice = null;
        CubeColour colourChoice = null;

        System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You have follow ammo: \n");
        if (usableAmmo[0]==true){
            //RED
            validSelection=0;
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_RED.escape()+"RED option 0\t");
        }
        if (usableAmmo[1]==true){
            //YELLOW
            validSelection=1;
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_YELLOW.escape()+"YELLOW option 1\t");
        }
        if (usableAmmo[2] == true){
            //BLUE
            validSelection=2;
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_BLUE.escape()+"BLUE option 2\t");
        }
        if (usableAmmo[0] == false && usableAmmo[1] == false && usableAmmo[2] ==false){
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"no ammo in your bag =(");
        }


        System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"\n\nOr yuo can choose one powerUp:  \n");

        for(int i=3;i<powerUpsType.length+3;i++){
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+findColorEscape(powerUpsColour[i-3].toString())+powerUpsType[i-3]+" OPTION "+i);
        }

        System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"\nSelect one item for pay: ");

        while (payChoice == 404) {
            try {

                System.out.flush();

                payChoice = CLIHandler.intRead();
                if ((payChoice != validSelection && payChoice<3) || payChoice >= powerUpsType.length + 3) {
                    System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "no item for this option");
                    payChoice = 404;

                }
            } catch (IllegalArgumentException e) {
                payChoice = 404;
            }
        }
            if (payChoice == validSelection ){
                ammoChoice[payChoice] = true;
                powerUpChoice = null;
                colourChoice = null;
            }
            else {
                powerUpChoice = powerUpsType[payChoice-3];
                colourChoice = powerUpsColour[payChoice-3];
            }


        return new GenericPayChoiceEvent(getUser(),ammoChoice,powerUpChoice,colourChoice);
    }

    /**
     * User choice a target character
     * @param possibleTargets
     * @return event that contains player's choice
     */
    @Override
    public Event targetingScopeTargetChoice(ArrayList<Character> possibleTargets) {
        CharacterChoiceEvent message =(CharacterChoiceEvent) characterChoice(possibleTargets);
        Event choice = new TargetingScopeTargetChoiceEvent(getUser(), message.getChosenCharacter());
        return choice;
    }

    /**
     * It show the possibility to use one power up at the end of turn(no yor turn)
     * @param powerUpNames list of  power up's name available to use
     * @param powerUpColours list of power up's colour available to use
     * @param maxUsablePowerUps max powerUp that you can use
     * @return event that contains player's choice
     */
    @Override
    public Event endRoundPowerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours, int maxUsablePowerUps) {
        String choice = null;
        Event message = null;
        int index;
        String[] powerUpChoice = new String[maxUsablePowerUps];
        CubeColour[] colourChoice = new CubeColour[maxUsablePowerUps];

        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You receive damage: would you like to use one PowerUp? [Y/N] ");


        choice = CLIHandler.stringRead();

        if (choice.equalsIgnoreCase("y")){
        for(int i=0;i<powerUpNames.length;i++){
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+findColorEscape(powerUpColours[i].toString())+powerUpNames[i]+" OPTION "+i);
        }

        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You can choose at most "+maxUsablePowerUps+" option,type 404 to end: ");

            for (int i = 0; i < maxUsablePowerUps; i++) {
                index = 600;
                while (index == 600) {
                    try {

                        System.out.flush();

                        index = CLIHandler.intRead();


                    } catch (IllegalArgumentException e) {
                        index = 404;
                    }

                    if (index==404){
                        i=powerUpNames.length;
                    }else {
                        if(index<0 || index>=maxUsablePowerUps){
                            System.out.print("Invalid choice \n");
                            i--;

                        }else {
                            powerUpChoice[i] = powerUpNames[index];
                            colourChoice[i] = powerUpColours[index];
                        }
                    }
                }
            }

            message = new EndRoundPowerUpChoiceEvent(getUser(),powerUpChoice,powerUpColours);


        } else if (choice.equalsIgnoreCase("n")) {
          message =  new EndRoundPowerUpChoiceEvent(getUser(),null,null);
        }
        return message;
    }

    /**
     * User choose one way for pay
     * @param powerUpNames list of power up's name available to use
     * @param powerUpColours list of power up's colour available to use
     * @param minimumPowerUpRequest if your ammo are not enough you must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return index of powerUp selected
     */
    private int[] payment(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest){
        int[] selected = new int[powerUpNames.length];
        int index ;
        String choice;


        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Would you like to pay with PowerUP? [Y/N]");
        choice = CLIHandler.stringRead();

        if (choice.equalsIgnoreCase("Y")) {
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

            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Select your powerUp:[type" +
                    "404 to terminate]");
            for (int i = 0; i < powerUpNames.length; i++) {
                index = 600;
                while (index == 600) {
                    try {

                        System.out.flush();

                        index = CLIHandler.intRead();


                    } catch (IllegalArgumentException e) {
                        index = 404;
                    }
                    if (index==404){
                        i=powerUpNames.length;
                    }else {
                        if(index<0 || index>=powerUpNames.length){
                            System.out.print("Invalid choice \n");
                            i--;

                        }else {
                            selected[i] = index;
                        }
                    }
                }
            }
        }
        else if(choice.equals("N"))
            selected = null;
        return selected;
    }

}