package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.server_view_event.LobbySettingsEvent;
import it.polimi.ingsw.event.server_view_event.ReconnectionRequestEvent;
import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
import it.polimi.ingsw.event.view_controller_event.*;
import it.polimi.ingsw.event.view_server_event.LobbyChoiceEvent;
import it.polimi.ingsw.event.view_server_event.NewGameChoiceEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;

import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.graph.*;

import java.util.*;

public class CLI extends RemoteView {

    private CLIDisplay display;
    private Map<Character, String> mapCharacterNameColors = new EnumMap<Character, String>(Character.class);
    private static final String BROADCASTSTRING = "BROADCAST";

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
     *getter
     * @return DISPLAY
     */
    public CLIDisplay getDisplay() {
        return display;
    }

    /**
     * getter of map between character and colour
     * @return map(character-colour)
     */
    public Map<Character, String> getMapCharacterNameColors() {
        return mapCharacterNameColors;
    }

    /**
     * It checks if game is ready to start(display ready)
     * @return game set or not
     */
    @Override
    public boolean isGameSet() {
        if( getDisplay().getMap()!=null){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Print on screen the UpdateNotification message, requesting a choice if necessary;
     * @return the answer that will be sent to the server.
     */
    @Override
    public Event printUserNotification(UsernameModificationEvent usernameEvent) {
        String newUser = usernameEvent.getNewUser();
        Event returnedEvent;
        if(newUser.equals(usernameEvent.getUser())){
            ArrayList<String> disconnectedClients = ((ReconnectionRequestEvent)usernameEvent).getDisconnectedUsers();
            while(!disconnectedClients.contains(newUser)){
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+
                        "The disconnected clients are:");
                CLIHandler.arrayPrint(((ReconnectionRequestEvent)usernameEvent).getDisconnectedUsers());
                System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+
                        "Type your old username:\t");
                newUser =CLIHandler.stringRead();
            }
            usernameEvent.setNewUser(newUser);
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+
                    "Reconnecting as "+newUser);
            returnedEvent = new ReconnectedEvent(newUser);
        }
        else{
            System.out.println("Username already connected, yours is now:\t"+newUser);

            returnedEvent = null;
        }
        return returnedEvent;
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
     *setter
     * @param lobbySettings contains information of map and gametrack
     */
    @Override
    public void setGame(LobbySettingsEvent lobbySettings) {
        display.setMap(new CLIMap(lobbySettings.getMapNumber()));
        CLIGameTrack gameTrack = new CLIGameTrack();
        gameTrack.createGameTrack();
        display.setGameTrack(gameTrack);

        // TODO: 24/06/2019 settare la playerBoard dello user
    }

    /**
     * user choice for character
     * @param availableCharacters character available
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
                CLIGameTrack gameTrack = new CLIGameTrack();
                gameTrack.createGameTrack();
                display.setGameTrack(gameTrack);
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
        while (chosenAction <1 || chosenAction>=5) {
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
        int selected ;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape() +"Select one weapon to reload or type other to skip action.");

        reloadableWeapons.add("Skip action ");
        selected = CLIHandler.arraylistPrintRead(reloadableWeapons);


        Event message;
        if (reloadableWeapons.get(selected).equalsIgnoreCase("Skip action")) {
            message = new SkipActionChoiceEvent(getUser());
        } else {
            message = new WeaponReloadChoiceEvent(getUser(), reloadableWeapons.get(selected));
        }

        return message;
    }

    /**
     *User choose square target for his selected effect
     * @param possibleSquareX column of available square
     * @param possibleSquareY row of available square
     * @return event that contains player's choice
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
        return new SpawnChoiceEvent(getUser(), powerUpNames[chosenPowerUp], powerUpColours[chosenPowerUp]);
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
        int weaponSelected ;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You choose to discard one weapon:");
        weaponSelected = CLIHandler.arraylistPrintRead(yourWeapons);
        return new WeaponDiscardChoiceEvent(getUser(), yourWeapons.get(weaponSelected));
    }

    /**
     * User choose weapon to fire
     * @param availableWeapons weapon loaded and ready to fire
     * @return event that contains player's choice
     */
    @Override
    public Event weaponChoice(ArrayList<String> availableWeapons) {
        int weaponSelected;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You choose to fire ");
        weaponSelected = CLIHandler.arraylistPrintRead(availableWeapons);
        return new WeaponChoiceEvent(getUser(), availableWeapons.get(weaponSelected));
    }

    /**
     * User select one weapon to grab
     * @param availableWeapon weapon on spawn square
     * @return event that contains player's choice
     */
    @Override
    public Event weaponGrabChoice(ArrayList<String> availableWeapon) {
        int weaponSelected;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You choose to garb ");
        weaponSelected = CLIHandler.arraylistPrintRead(availableWeapon);
        return new WeaponGrabChoiceEvent(getUser(), availableWeapon.get(weaponSelected));
    }

    /**
     * iT show whio whin the game and his point
     * @param user winner
     * @param point total winner's points
     * @return message notify the success of updating
     */
    @Override
    public Event winnerUpdate(String user, int point) {
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+" Player "+user+" win this game with "+point);
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }

    /**
     * User choose at least one effect for his own weapon
     * @param availableWeaponEffects effect available for selected weapon
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
     *User choose one powerUp to use
     * @param powerUpNames name of powerUp available
     * @param powerUpColours color of powerUp available
     * @return event that contains player's choice
     */
    @Override
    public Event powerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours) {
        Event message;
        int chosenPowerUp = 404;
        while (chosenPowerUp<0 || chosenPowerUp>powerUpNames.length) {
            try {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Your powerUp!");
                for(int i=0;i<powerUpNames.length;i++){
                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+findColorEscape(powerUpColours[i].toString())+powerUpNames[i]+" OPTION "+i);
                }
                System.out.println("Skip Choice OPTION "+powerUpNames.length);
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Select one powerUp:[option number]");
                chosenPowerUp = CLIHandler.intRead();

            } catch (IllegalArgumentException e) {
                chosenPowerUp = 404;
            }

        }
        if (chosenPowerUp == powerUpNames.length){
            message = new SkipActionChoiceEvent(getUser());
        }
        else {
            message = new PowerUpChoiceEvent(getUser(),powerUpNames[chosenPowerUp], powerUpColours[chosenPowerUp]);
        }
        return message;
    }

    /**
     * Print screen updated
     */
    @Override
    public void printScreen() {
        display.createDisplay();
        display.printDisplay();
    }

    /**
     * Every time one player joins the game it's notified to other player
     * @param newPlayer  new player username
     * @param characterChoice new player character choose
     * @return message notify the success of updating
     */
    @Override
    public Event newPlayerJoinedUpdate(String newPlayer, Character characterChoice) {
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"New player joined the game:" + newPlayer+" with "+Color.ANSI_BLACK_BACKGROUND.escape()+mapCharacterNameColors.get(characterChoice)+characterChoice.name());
        CLIPlayerBoard player = new CLIPlayerBoard(newPlayer,characterChoice,mapCharacterNameColors);
        display.setPlayerBoard(player);
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }

    /**
     * It updates map by adding an ammo tile
     * @param x row
     * @param y column
     * @param fistColour ammo cube
     * @param secondColour ammo cube
     * @param thirdColour ammo cube or power up
     * @return message notify the success of updating
     */
    @Override
    public Event addAmmoTileUpdate(int x, int y, String fistColour, String secondColour, String thirdColour) {
        String[] color = {fistColour, secondColour, "PowerUp"};
        CLIPrintableElement currElement;
        if (thirdColour.equalsIgnoreCase("POWERUP")) {
            currElement = new CLIPrintableElement(true, color);
        }
        else {
            color[2] = thirdColour;
            currElement = new CLIPrintableElement(false, color);
        }
        display.getMap().updateResource(currElement, x, y);
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }

    /**
     * It updates map by deleting an ammo tile
     * @param x row
     * @param y column
     * @return message notify the success of updating
     */
    @Override
    public Event removeAmmoTileUpdate(int x, int y) {
        CLIPrintableElement currElement = new CLIPrintableElement(false);
        display.getMap().updateResource(currElement, x, y);
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }

    /**
     * @param currCharacter modified player position
     * @param x row
     * @param y column
     * @return message notify the success of updating
     */
    @Override
    public Event positionUpdate(Character currCharacter, int x, int y) {
        CLIPrintableElement currElement = new CLIPrintableElement(currCharacter, mapCharacterNameColors.get(currCharacter));
        display.getMap().updateResource(currElement, x, y);
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }

    /**
     * modification of player Board(damage, mark, skull)
     * @param character modified player board
     * @param skullNumber number of skull
     * @param marks number of marks and who did it
     * @param damages number of damage and who did it
     * @return message notify the success of updating
     */
    @Override
    public Event playerBoardUpdate(Character character, int skullNumber, Character[] marks, Character[] damages) {
        display.getPlayerBoard(character).clean(2);//MARKS
        display.getPlayerBoard(character).clean(3);//DAMAGE
        display.getPlayerBoard(character).clean(5);//WEAPON
        display.getPlayerBoard(character).clean(6);//POWERUP
        display.getPlayerBoard(character).clean(7);//AMMO
        display.getPlayerBoard(character).clean(8);//SKULL
        int j=10;
        for (int i=0; i<marks.length;i++) {

            display.getPlayerBoard(character).markUpdate(1,marks[i],j);
            j=j+4;
        }
        j=10;
        for (int i=0; i<damages.length;i++) {
            display.getPlayerBoard(character).damageUpdate(1, damages[i],j);
            j=j+4;
        }

        display.getPlayerBoard(character).skullUpdate(skullNumber);

        return new UpdateChoiceEvent(BROADCASTSTRING);
    }

    /**
     *It shows power up available on player board
     * @param currCharacter  modified player board
     * @param powerUp power up of player
     * @param colour colour of power up
     * @return message notify the success of updating
     */
    @Override
    public Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, CubeColour[] colour) {

        for (int i = 0; i < powerUp.length; i++) {
            powerUp[i] = findColorEscape(colour[i].name()) + powerUp[i];
        }
        display.getPlayerBoard(currCharacter).gadgetsUpdate('P', powerUp);

        return new UpdateChoiceEvent(BROADCASTSTRING);
    }

    /**
     *It shows weapon available on player board
     * @param currCharacter  modified player board
     * @param weapons weapons of player
     * @return message notify the success of updating
     */
    @Override
    public Event playerWeaponUpdate(Character currCharacter, String[] weapons) {

        display.getPlayerBoard(currCharacter).gadgetsUpdate('W', weapons);

        return new UpdateChoiceEvent(BROADCASTSTRING);
    }

    /**
     *It shows ammo available on player board
     * @param currCharacter  modified player board
     * @param ammo ammo of player
     * @return message notify the success of updating
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

        return new UpdateChoiceEvent(BROADCASTSTRING);
    }

    /**
     * It updates number of skull on the game track
     * @param damageTokenNumber player who takes the skull
     * @param skullNumber number of skull left:
     *                    0-one skull;
     *                    1-one damage;
     *                    2-two damage;
     *
     * @return message notify the success of updating
     */
    @Override
    public Event gameTrackSkullUpdate( Character[] damageTokenNumber, int[] skullNumber) {

        int column=2;
        // Si assegnano i teschi al player esatto
        for (int i =0; i<damageTokenNumber.length;i++) {

                display.getGameTrack().removeSkull(skullNumber[i], mapCharacterNameColors.get(damageTokenNumber[i]),column);
                column = column+4;
            }
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }

    /**
     * It replaces weapons on spawn square
     * @param x      coordinate (row)
     * @param y      coordinate(column)
     * @param weapon weapon to add
     * @return message notify the success of updating
     */
    @Override
    public Event weaponReplaceUpdate(int x, int y, String[] weapon) {
        display.weaponsSpawnSquare(x, y, weapon);
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }



    /**
     * user choose target of his power up
     * @param availableTargets character available to hit
     * @param numTarget max numeber of target
     * @return event that contains player's choice
     */
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
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
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
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
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
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
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
     * it shows how to pay when player can choose one item from ammo or powerUp
     * @param usableAmmo available ammo
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
     * @param possibleTargets character available to hit
     * @return event that contains player's choice
     */
    @Override
    public Event targetingScopeTargetChoice(ArrayList<Character> possibleTargets) {
        CharacterChoiceEvent message =(CharacterChoiceEvent) characterChoice(possibleTargets);
        Event choice = new TargetingScopeTargetChoiceEvent(getUser(), message.getChosenCharacter());
        return choice;
    }

    /**
     * User choose to use one of his power up during an action
     * @param powerUpNames available power up
     * @param powerUpColours colour of power up
     * @return event that contains player's choice
     */
    @Override
    public Event whileActionPowerUpRequestEvent(String[] powerUpNames, CubeColour[] powerUpColours) {
        String choice =answerControl();
        Event selected = null;
        if(choice.equalsIgnoreCase("Y")) {
            PowerUpChoiceEvent message = (PowerUpChoiceEvent) powerUpChoice(powerUpNames, powerUpColours);
             selected = new WhileActionPowerUpChoiceEvent(getUser(), true, message.getCard(), message.getPowerUpColour());
        }else {
            selected = new WhileActionPowerUpChoiceEvent(getUser(), false, null,null);
        }

          return selected;
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

        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You receive damage");


        choice = answerControl();

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
     * User choose which game would like to join
     * @param available  : availableChoice[0] = new game
     *                   : availableChoice[1] = wait lobby
     *                   : availableChoice[2] = started game
     * @param startedLobbies Name of started game
     * @param waitingLobbies Games are going to begin
     * @return event that contains player's choice
     */
    @Override
    public Event welcomeChoice(boolean[] available, ArrayList<String> startedLobbies, ArrayList<String> waitingLobbies) {
        int choice = 404;
        int lobbyChoice = 404;
        int userChoice= 404;
        Event messageChoice = null;

        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape() +"WELCOME!"+
                "\nYOU CAN:");
        if (available[0]) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "-select a new game option 0");
        }
        if (available[1]) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "-select a waiting lobby option 1");
        }
        if (available[2]) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "-select a started game option 2");
        }

        do{

            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Select a corrent option:");
            choice = CLIHandler.intRead();

        } while(choice<0 || choice>3 || !available[choice]);

        if(choice == 0){
            messageChoice =  new NewGameChoiceEvent(getUser());
        }

        if(choice == 1){
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "\n-Waiting lobbies:");
            lobbyChoice=CLIHandler.arraylistPrintRead(waitingLobbies);
            messageChoice = new LobbyChoiceEvent(getUser(),waitingLobbies.get(lobbyChoice));

        }

        if(choice == 2){
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "\n-Started lobbies:");
            lobbyChoice= CLIHandler.arraylistPrintRead(startedLobbies);
            messageChoice = new LobbyChoiceEvent(getUser(), startedLobbies.get(lobbyChoice));

        }
        return messageChoice;

    }

    /**
     * User choose one way for pay
     * @param powerUpNames list of power up's name available to use
     * @param powerUpColours list of power up's colour available to use
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return index of powerUp selected
     */
    private int[] payment(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest){
        int[] selected = new int[powerUpNames.length];
        int index ;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape() +"It's time to pay...");
        String choice = answerControl();

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

    /**
     * it found color by name
     * @param colourString name of colour
     * @return colour escape
     */
    private String findColorEscape(String colourString) {
        String colourEscape = Color.ANSI_BLACK_BACKGROUND.escape();
        if (colourString.equalsIgnoreCase("RED")) {
            colourEscape = colourEscape + Color.ANSI_RED.escape();
        } else if (colourString.equalsIgnoreCase("BLUE")) {
            colourEscape = colourEscape + Color.ANSI_BLUE.escape();
        } else {
            colourEscape = colourEscape + Color.ANSI_YELLOW.escape();
        }
        return colourEscape;
    }

    /**
     * It checks if imput is equals to Y or N
     * @return user choice
     */
        private String answerControl(){
        String choice = new String();
            while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")){
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Would you like to use one of your power up?[Y/N]");
                choice = CLIHandler.stringRead();
            }
        return choice;
        }


}