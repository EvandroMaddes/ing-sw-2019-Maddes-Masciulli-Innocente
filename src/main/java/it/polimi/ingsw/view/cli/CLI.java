package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.modelviewevent.EndGameUpdate;
import it.polimi.ingsw.event.serverviewevent.ReconnectionRequestEvent;
import it.polimi.ingsw.event.serverviewevent.UsernameModificationEvent;
import it.polimi.ingsw.event.viewcontrollerevent.*;
import it.polimi.ingsw.event.viewserverevent.LobbyChoiceEvent;
import it.polimi.ingsw.event.viewserverevent.NewGameChoiceEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.graph.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

/**
 * It menages interaction with user throw command line
 */
public class CLI extends RemoteView {

    /**
     * It's command line screen
     */
    private CLIDisplay display;
    /**
     * link between character and color on screen
     */
    private Map<Character, String> mapCharacterNameColors = new EnumMap<>(Character.class);


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
     * getter
     *
     * @return DISPLAY
     */
    private CLIDisplay getDisplay() {
        return display;
    }

    /**
     * RemoteViewInterface implementation: It checks if game is ready to start(display ready)
     *
     * @return game set or not
     */
    @Override
    public boolean isGameSet() {
        return getDisplay().getMap() != null;
    }

    /**
     * RemoteViewInterface implementation: it prints on screen the UpdateNotification message, requesting a choice if necessary;
     *
     * @param usernameEvent notify modification of a user name
     * @return the answer that will be sent to the server.
     */
    @Override
    public Event printUserNotification(UsernameModificationEvent usernameEvent) {
        String newUser = usernameEvent.getNewUser();
        Event returnedEvent;
        if (newUser.equals(usernameEvent.getUser())) {
            ArrayList<String> disconnectedClients = ((ReconnectionRequestEvent) usernameEvent).getDisconnectedUsers();
            while (!disconnectedClients.contains(newUser)) {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() +
                        "The disconnected clients are:");
                CLIHandler.arrayPrint(((ReconnectionRequestEvent) usernameEvent).getDisconnectedUsers());
                System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() +
                        "Type your old username:\t");
                newUser = CLIHandler.stringRead();
            }
            usernameEvent.setNewUser(newUser);
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() +
                    "Reconnecting as " + newUser);
            returnedEvent = new ReconnectedEvent(newUser);
        } else {
            System.out.println("Username already connected, yours is now:\t" + newUser);

            returnedEvent = null;
        }
        return returnedEvent;
    }

    /**
     * RemoteViewInterface implementation: it catches user choices for username,connection(RMI/SOCKET) and IP address
     *
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
     * RemoteViewInterface implementation: setter
     *
     * @param mapNumber is the map chosen
     */
    @Override
    public void setGame(int mapNumber) {
        display.setMap(new CLIMap(mapNumber));
        CLIGameTrack gameTrack = new CLIGameTrack();
        gameTrack.createGameTrack();
        display.setGameTrack(gameTrack);
    }

    /**
     * RemoteViewInterface implementation: user choice for character
     *
     * @param availableCharacters character available
     * @return event that contains player's choice
     */
    @Override
    public Event characterChoice(ArrayList<Character> availableCharacters) {
        ArrayList<String> stringCharacter = new ArrayList<>();
        ArrayList<String> cliCharacters = new ArrayList<>();
        String chosenStringCharacter = "init";
        int index;
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
                CustomLogger.logException(e);
            }

        }
        index = stringCharacter.indexOf(chosenStringCharacter.toUpperCase());
        chosenCharacter = availableCharacters.get(index);

        return new CharacterChoiceEvent(getUser(), chosenCharacter);
    }

    /**
     * RemoteViewInterface implementation: User choice for map
     *
     * @return event that contains player's choice
     */
    @Override
    public Event gameChoice() {
        int map = 404;
        while (map == 404) {
            try {

                while (map < 0 || map > 3) {

                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "option 0 for twelve squares" +
                            "\noption 1 for eleven squares" +
                            "\noption 2 for eleven squares" +
                            "\noption 3 for ten squares(recommended for three players)");
                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Choose a map from the following(select number):");
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
        return new GameChoiceEvent(getUser(), map);
    }

    /**
     * RemoteViewInterface implementation: User chooses an action at the beginning of his own turn
     *
     * @param fireEnable true if player can use action fire
     * @return event that contains player's choice
     */
    @Override
    public Event actionChoice(boolean fireEnable) {
        int chosenAction = 404;
        while (chosenAction < 1 || chosenAction >= 5 || (!fireEnable && chosenAction == 3)) {
            try {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() +
                        "option 1 for MOVE"
                        + "\noption 2 for GRAB");
                if (fireEnable) {
                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "option 3 for SHOT");
                }
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "option 4 for SKIP YOUR TURN" + "\nSelect one action:");

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
     * RemoteViewInterface implementation: user chooses weapon to reload
     *
     * @param reloadableWeapons list of weapon that can be reloaded
     * @return event that contains player's choice
     */
    @Override
    public Event reloadChoice(ArrayList<String> reloadableWeapons) {
        int selected;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Select one weapon to reload or type other to skip action.");

        reloadableWeapons.add("Skip action");
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
     * RemoteViewInterface implementation: User chooses square target for his selected effect
     *
     * @param possibleSquareX column of available square
     * @param possibleSquareY row of available square
     * @return event that contains player's choice
     */
    @Override
    public Event weaponEffectSquareChoice(int[] possibleSquareX, int[] possibleSquareY) {
        PositionChoiceEvent message = (PositionChoiceEvent) positionMoveChoice(possibleSquareX, possibleSquareY);
        return new WeaponSquareTargetChoiceEvent(getUser(), message.getPositionX(), message.getPositionY());

    }

    /**
     * RemoteViewInterface implementation: User chooses which powerUp discard
     *
     * @param powerUpNames   list of powerUp
     * @param powerUpColours color of powerUp
     * @return event that contains player's choice
     */
    @Override
    public Event respawnChoice(String[] powerUpNames, CubeColour[] powerUpColours) {

        int chosenPowerUp = 404;
        while (chosenPowerUp < 0 || chosenPowerUp > powerUpNames.length) {
            try {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "It's time to respawn!");
                for (int i = 0; i < powerUpNames.length; i++) {
                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + findColorEscape(powerUpColours[i].toString()) + powerUpNames[i] + " OPTION " + i);
                }
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Discard your powerUp:[option number]");
                chosenPowerUp = CLIHandler.intRead();
            } catch (IllegalArgumentException e) {
                chosenPowerUp = 404;
            }

        }
        return new SpawnChoiceEvent(getUser(), powerUpNames[chosenPowerUp], powerUpColours[chosenPowerUp]);
    }

    /**
     * RemoteViewInterface implementation: user chooses where move his character
     *
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
                CustomLogger.logException(e);
            }
        }
        return new MoveChoiceEvent(getUser(), chosenSquare[0], chosenSquare[1]);
    }

    /**
     * RemoteViewInterface implementation: User chooses where move his character and grab item on new position
     *
     * @param possibleSquareX column
     * @param possibleSquareY row
     * @return event that contains player's choice
     */
    @Override
    public Event positionGrabChoice(int[] possibleSquareX, int[] possibleSquareY) {
        int[] chosenSquare = null;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Position for grab some item ");

        while (chosenSquare == null) {
            try {
                chosenSquare = CLIHandler.coordinatePrintAndRead(possibleSquareX, possibleSquareY);
            } catch (IllegalArgumentException e) {
                CustomLogger.logException(e);
            }
        }
        return new GrabChoiceEvent(getUser(), chosenSquare[0], chosenSquare[1]);
    }

    /**
     * RemoteViewInterface implementation: User chooses one weapon to discard
     *
     * @param yourWeapon player's weapon
     * @return event that contains player's choice
     */
    @Override
    public Event weaponDiscardChoice(ArrayList<String> yourWeapon) {
        int weaponSelected;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "You choose to discard one weapon:");
        weaponSelected = CLIHandler.arraylistPrintRead(yourWeapon);
        return new WeaponDiscardChoiceEvent(getUser(), yourWeapon.get(weaponSelected));
    }

    /**
     * RemoteViewInterface implementation: User chooses weapon to fire
     *
     * @param availableWeapons weapon loaded and ready to fire
     * @return event that contains player's choice
     */
    @Override
    public Event weaponChoice(ArrayList<String> availableWeapons) {
        int weaponSelected;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "You choose to fire ");
        weaponSelected = CLIHandler.arraylistPrintRead(availableWeapons);
        return new WeaponChoiceEvent(getUser(), availableWeapons.get(weaponSelected));
    }

    /**
     * RemoteViewInterface implementation: User selects one weapon to grab
     *
     * @param availableWeapon weapon on spawn square
     * @return event that contains player's choice
     */
    @Override
    public Event weaponGrabChoice(ArrayList<String> availableWeapon) {
        int weaponSelected;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "You choose to garb ");
        weaponSelected = CLIHandler.arraylistPrintRead(availableWeapon);
        return new WeaponGrabChoiceEvent(getUser(), availableWeapon.get(weaponSelected));
    }

    /**
     * RemoteViewInterface implementation: It shows winner of the game and his point
     *
     * @param endGameUpdate is the end-game notification
     */
    @Override
    public Event winnerUpdate(EndGameUpdate endGameUpdate) {
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + endGameUpdate.getEndGameMessage());
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface implementation: User chooses at least one effect for his own weapon
     *
     * @param availableWeaponEffects effect available for selected weapon
     * @return event that contains player's choice
     */
    @Override
    public Event weaponEffectChoice(boolean[] availableWeaponEffects) {
        int effectChoice = 40;
        int first = 404;
        int second = 404;
        int third = 404;
        Event message;

        String choice = "";
        while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Would you like to use one of weapon EFFECT?[Y/N]");
            choice = CLIHandler.stringRead();
        }
        if (choice.equalsIgnoreCase("Y")) {
            if (availableWeaponEffects[0]) {
                first = 0;
            }

            if (availableWeaponEffects[1]) {
                second = 1;
            }

            if (availableWeaponEffects[2]) {
                third = 2;
            }


            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Available effect for your weapon:");

            for (int i = 0; i < availableWeaponEffects.length; i++) // print only available effects
            {
                if (availableWeaponEffects[i]) {
                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "effect " + i);
                }
            }
            while (!(effectChoice == first || effectChoice == second || effectChoice == third)) {
                try {
                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Select at least one effect[number]");

                    System.out.flush();

                    effectChoice = CLIHandler.intRead();
                } catch (IllegalArgumentException e) {
                    effectChoice = 404;
                }
            }
            effectChoice++;
            message = new WeaponEffectChioceEvent(getUser(), effectChoice);
        } else {
            message = new SkipActionChoiceEvent(getUser());
        }
        return message;
    }

    /**
     * RemoteViewInterface implementation: User select target to hit(characters)
     *
     * @param availableTargets characters that can be hit
     * @param numTarget        number of target
     * @return event that contains player's choice
     */
    @Override
    public Event weaponTargetChoice(ArrayList<Character> availableTargets, int numTarget) {
        ArrayList<String> cliColorCharacters = new ArrayList<>();
        ArrayList<String> cliStringCharacter = new ArrayList<>();
        ArrayList<Character> targetCharacter = new ArrayList<>();
        for (Character currCharacter : availableTargets) {
            cliColorCharacters.add(mapCharacterNameColors.get(currCharacter) + currCharacter.name().toUpperCase());
            cliStringCharacter.add(currCharacter.name().toUpperCase());
        }
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Select yor targets to hit,max targets: " + numTarget + "[type END to select less then " + numTarget + " targets]");
        CLIHandler.arrayPrint(cliColorCharacters);

        Character chosenCharacter = null;
        String chosenStringCharacter = "init";

        for (int i = 0; i <= numTarget; i++) {
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "next choice");

            do {
                try {
                    chosenStringCharacter = CLIHandler.stringRead();
                } catch (IllegalArgumentException e) {
                    availableTargets = null;

                }

            } while (!cliStringCharacter.contains(chosenStringCharacter.toUpperCase()) && !chosenStringCharacter.equalsIgnoreCase("end"));
            if (chosenStringCharacter.equalsIgnoreCase("end")) {
                i = numTarget + 1;
            } else if (cliStringCharacter.contains(chosenStringCharacter.toUpperCase())) {
                int index = cliStringCharacter.indexOf(chosenStringCharacter.toUpperCase());
                chosenCharacter = availableTargets.get(index);
                targetCharacter.add(chosenCharacter);
            }
        }

        return new WeaponPlayersTargetChoiceEvent(getUser(), targetCharacter);
    }

    /**
     * RemoteViewInterface implementation: User chooses one powerUp to use
     *
     * @param powerUpNames   name of powerUp available
     * @param powerUpColours color of powerUp available
     * @return event that contains player's choice
     */
    @Override
    public Event powerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours) {
        Event message;
        int chosenPowerUp = 404;
        while (chosenPowerUp < 0 || chosenPowerUp > powerUpNames.length) {
            try {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Your powerUp!");
                for (int i = 0; i < powerUpNames.length; i++) {
                    System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + findColorEscape(powerUpColours[i].toString()) + powerUpNames[i] + " OPTION " + i);
                }
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape().concat(Color.ANSI_GREEN.escape().concat("Skip Choice OPTION " + powerUpNames.length)));
                System.out.println("Select one powerUp:[option number]");
                chosenPowerUp = CLIHandler.intRead();

            } catch (IllegalArgumentException e) {
                chosenPowerUp = 404;
            }

        }
        if (chosenPowerUp == powerUpNames.length) {
            message = new SkipActionChoiceEvent(getUser());
        } else {
            message = new PowerUpChoiceEvent(getUser(), powerUpNames[chosenPowerUp], powerUpColours[chosenPowerUp]);
        }
        return message;
    }

    /**
     * RemoteViewInterface implementation: User chooses one destination square for action move
     *
     * @param possibleSquareX row of possible destination square
     * @param possibleSquareY column of possible destination square
     * @return a ShotMoveChoiceEvent that contains the chosen position
     */
    @Override
    public Event shotMoveChoiceEvent(int[] possibleSquareX, int[] possibleSquareY) {
        System.out.println("Select one square before shooting:");
        PositionChoiceEvent message = (PositionChoiceEvent) positionMoveChoice(possibleSquareX, possibleSquareY);
        return new ShotMoveChoiceEvent(getUser(), message.getPositionX(), message.getPositionY());
    }

    /**
     * RemoteViewInterface implementation: it shows game board updated: map, player boards and game track
     */
    @Override
    public void printScreen() {
        display.createDisplay();
        display.printDisplay();
    }

    /**
     * RemoteViewInterface implementation: Print a reconnection or disconnection (depending on disconnected value) update notifying the user and character disconnected
     *
     * @param user         is the involved user
     * @param character    is the involved user's character
     * @param disconnected is true if the user was disconnected, false if was reconnected
     * @return event that contains player's choice
     */
    @Override
    public Event playerReconnectionNotify(String user, Character character, boolean disconnected) {
        String optional;
        String suffix;
        if (disconnected) {
            optional = " disconnected: ";
            suffix = " isn't anymore in the arena!";
        } else {
            optional = " reconnected: ";
            suffix = " is back in the arena!";
        }
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + user + optional + Color.ANSI_BLACK_BACKGROUND.escape() + mapCharacterNameColors.get(character) + character.name().concat(suffix));
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface implementation: Every time one player joins the game it's notified to other player
     *
     * @param newPlayer       new player username
     * @param characterChoice new player character choose
     * @return message notify the success of updating
     */
    @Override
    public Event newPlayerJoinedUpdate(String newPlayer, Character characterChoice) {
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "New player joined the game:" + newPlayer + " with " + Color.ANSI_BLACK_BACKGROUND.escape() + mapCharacterNameColors.get(characterChoice) + characterChoice.name());
        CLIPlayerBoard player = new CLIPlayerBoard(newPlayer, characterChoice, mapCharacterNameColors);
        display.setPlayerBoard(player);
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface implementation: It updates map by adding an ammo tile
     *
     * @param x            row
     * @param y            column
     * @param fistColour   ammo cube
     * @param secondColour ammo cube
     * @param thirdColour  ammo cube or power up
     * @return message notify the success of updating
     */
    @Override
    public Event addAmmoTileUpdate(int x, int y, String fistColour, String secondColour, String thirdColour) {
        String[] color = {fistColour, secondColour, "PowerUp"};
        CLIPrintableElement currElement;
        if (thirdColour.equalsIgnoreCase("POWERUP")) {
            currElement = new CLIPrintableElement(true, color);
        } else {
            color[2] = thirdColour;
            currElement = new CLIPrintableElement(false, color);
        }
        display.getMap().updateResource(currElement, x, y);
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface implementation: It updates map by deleting an ammo tile
     *
     * @param x row
     * @param y column
     * @return message notify the success of updating
     */
    @Override
    public Event removeAmmoTileUpdate(int x, int y) {
        CLIPrintableElement currElement = new CLIPrintableElement(false);
        display.getMap().updateResource(currElement, x, y);
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface implementation: it sets character on a new position on map
     *
     * @param currCharacter modified player position
     * @param x             row: if sets at 404, player must be removed
     * @param y             column: if sets at 404, players must be removed
     * @return message notify the success of updating
     */
    @Override
    public Event positionUpdate(Character currCharacter, int x, int y) {

        CLIPrintableElement currElement = new CLIPrintableElement(currCharacter, mapCharacterNameColors.get(currCharacter));
        if (x == 404 && y == 404) {
            display.getMap().removePlayer(currElement.getResource());
        } else {
            display.getMap().updateResource(currElement, x, y);
        }
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface implementation: Modification of player Board(damage, mark, skull)
     *
     * @param character   modified player board
     * @param skullNumber number of skull
     * @param marks       number of marks and who did it
     * @param damages     number of damage and who did it
     * @return message notify the success of updating
     */
    @Override
    public Event playerBoardUpdate(Character character, int skullNumber, Character[] marks, Character[] damages) {
        display.getPlayerBoard(character).clean(2);//MARKS
        display.getPlayerBoard(character).clean(3);//DAMAGE
        display.getPlayerBoard(character).clean(8);//SKULL
        int j = 10;
        for (int i = 0; i < marks.length; i++) {

            display.getPlayerBoard(character).markUpdate(1, marks[i], j);
            j = j + 4;
        }
        j = 10;
        for (int i = 0; i < damages.length; i++) {
            display.getPlayerBoard(character).damageUpdate(1, damages[i], j);
            j = j + 4;
        }

        display.getPlayerBoard(character).skullUpdate(skullNumber);

        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface implementation: It shows power up available
     *
     * @param currCharacter modified player board
     * @param powerUp       power up of player
     * @param colour        colour of power up
     * @return message notify the success of updating
     */
    @Override
    public Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, CubeColour[] colour) {

        for (int i = 0; i < powerUp.length; i++) {
            powerUp[i] = findColorEscape(colour[i].name()) + powerUp[i];
        }
        display.getPlayerBoard(currCharacter).gadgetsUpdate('P', powerUp);

        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface implementation: It shows weapon available
     *
     * @param currCharacter modified player board
     * @param weapons       weapons of player
     * @return message notify the success of updating
     */
    @Override
    public Event playerWeaponUpdate(Character currCharacter, String[] weapons, boolean[] load) {
        for (int i = 0; i < load.length; i++) {
            if (load[i]) {
                weapons[i] = Color.ANSI_GREEN.escape() + weapons[i];
            } else
                weapons[i] = Color.ANSI_RED.escape() + weapons[i];
        }

        display.getPlayerBoard(currCharacter).gadgetsUpdate('W', weapons);

        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface implementation: It shows ammo available
     *
     * @param currCharacter modified player board
     * @param ammo          ammo of player
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

        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface implementation: It updates number of skull on the game track
     *
     * @param damageTokenNumber player who takes the skull
     * @param skullNumber       number of skull left:
     *                          0-one skull;
     *                          1-one damage;
     *                          2-two damage;
     * @return message notify the success of updating
     */
    @Override
    public Event gameTrackSkullUpdate(Character[] damageTokenNumber, int[] skullNumber) {

        int column = 2;
        // Si assegnano i teschi al player esatto
        for (int i = 0; i < damageTokenNumber.length; i++) {

            display.getGameTrack().removeSkull(skullNumber[i], mapCharacterNameColors.get(damageTokenNumber[i]), column);
            column = column + 4;
        }
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface implementation: It replaces weapons on spawn square
     *
     * @param x      coordinate x (row)
     * @param y      coordinate y (column)
     * @param weapon weapon to add
     * @return message notify the success of updating
     */
    @Override
    public Event weaponReplaceUpdate(int x, int y, String[] weapon) {
        display.weaponsSpawnSquare(x, y, weapon);
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface implementation: User chooses target of his power up
     *
     * @param availableTargets character available to hit
     * @param maxTarget        max number of target
     * @return event that contains player's choice
     */
    @Override
    public Event newtonTargetChoice(ArrayList<Character> availableTargets, int maxTarget) {
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Select target of newton:");
        CharacterChoiceEvent message = (CharacterChoiceEvent) characterChoice(availableTargets);
        return new NewtonPlayerTargetChoiceEvent(getUser(), message.getChosenCharacter());
    }

    /**
     * RemoteViewInterface implementation: it selects the destination square of target of newton powerUp
     *
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
     * RemoteViewInterface implementation: it returns how a player pay the cost of effect selected(weapon effect)
     * int arrays are built like that:
     * [0] - # Red
     * [1] - # Yellow
     * [2] - # Blue
     *
     * @param powerUpNames          list of power up's name available to use
     * @param powerUpColours        list of power up's colour available to use
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return event that contains player's choice
     */
    @Override
    public Event weaponEffectPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        Event message;
        int[] index = payment(powerUpNames, powerUpColours, minimumPowerUpRequest, maximumPowerUpRequest);
        String[] nameSelected = new String[index.length];
        CubeColour[] colourSelected = new CubeColour[index.length];
        if (index[0] == 404) {
            message = new WeaponEffectPaymentChoiceEvent(getUser(), new String[]{}, new CubeColour[]{});
        } else {
            for (int i = 0; i < index.length; i++) {
                nameSelected[i] = powerUpNames[index[i]];
                colourSelected[i] = powerUpColours[index[i]];
            }
            message = new WeaponEffectPaymentChoiceEvent(getUser(), nameSelected, colourSelected);
        }
        return message;
    }


    /**
     * RemoteViewInterface implementation: It returns how a player pay the weapon GRAB cost
     *
     * @param powerUpNames          list of power up's name available to use
     * @param powerUpColours        list of power up's colour available to use
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return event that contains player's choice
     */
    @Override
    public Event weaponGrabPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        Event message;
        int[] index = payment(powerUpNames, powerUpColours, minimumPowerUpRequest, maximumPowerUpRequest);
        String[] nameSelected = new String[index.length];
        CubeColour[] colourSelected = new CubeColour[index.length];
        if (index[0] == 404) {
            message = new WeaponGrabPaymentChoiceEvent(getUser(), new String[]{}, new CubeColour[]{});
        } else {
            for (int i = 0; i < index.length; i++) {
                nameSelected[i] = powerUpNames[index[i]];
                colourSelected[i] = powerUpColours[index[i]];
            }
            message = new WeaponGrabPaymentChoiceEvent(getUser(), nameSelected, colourSelected);
        }
        return message;
    }

    /**
     * RemoteViewInterface implementation: User selects how to reload his weapon: power Up, ammo or both
     *
     * @param powerUpNames          list of power up's name available to use
     * @param powerUpColours        list of power up's colour available to use
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return event that contains player's choice
     */
    @Override
    public Event weaponReloadPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        Event message;
        int[] index = payment(powerUpNames, powerUpColours, minimumPowerUpRequest, maximumPowerUpRequest);
        String[] nameSelected = new String[index.length];
        CubeColour[] colourSelected = new CubeColour[index.length];

        if (index[0] == 404) {
            message = new WeaponReloadPaymentChoiceEvent(getUser(), new String[]{}, new CubeColour[]{});
        } else {
            for (int i = 0; i < index.length; i++) {
                nameSelected[i] = powerUpNames[index[i]];
                colourSelected[i] = powerUpColours[index[i]];
            }
            message = new WeaponReloadPaymentChoiceEvent(getUser(), nameSelected, colourSelected);
        }
        return message;
    }

    /**
     * RemoteViewInterface implementation: it shows how to pay when player can choose one item from ammo or powerUp
     *
     * @param usableAmmo     available ammo
     * @param powerUpsType   list of power up's name available to use
     * @param powerUpsColour list of power up's colour available to use
     * @return event that contains player's choice
     */
    @Override
    public Event genericPaymentChoice(boolean[] usableAmmo, String[] powerUpsType, CubeColour[] powerUpsColour) {
        int payChoice = 404;
        boolean[] ammoChoice = {false, false, false};
        String powerUpChoice = null;
        CubeColour colourChoice = null;

        System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "You have follow ammo: \n");
        if (usableAmmo[0]) {
            //RED
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_RED.escape() + "RED option 0\t");
        }
        if (usableAmmo[1]) {
            //YELLOW
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_YELLOW.escape() + "YELLOW option 1\t");
        }
        if (usableAmmo[2]) {
            //BLUE
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_BLUE.escape() + "BLUE option 2\t");
        }
        if (!usableAmmo[0] && !usableAmmo[1] && !usableAmmo[2]) {
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "no ammo in your bag");
        }


        System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "\n\nOr you can choose one powerUp:  \n");

        for (int i = 3; i < powerUpsType.length + 3; i++) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + findColorEscape(powerUpsColour[i - 3].toString()) + powerUpsType[i - 3] + " OPTION " + i);
        }

        System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "\nSelect one item for pay: ");

        while (payChoice == 404) {
            try {

                System.out.flush();

                payChoice = CLIHandler.intRead();
                if (payChoice < 0 || payChoice > (2 + powerUpsType.length)) {
                    System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "no item for this option");
                    payChoice = 404;
                }
                if (-1 < payChoice && payChoice < 3) {
                    if (!usableAmmo[payChoice]) {
                        System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "no item for this option");
                        payChoice = 404;
                    }
                }

            } catch (IllegalArgumentException e) {
                payChoice = 404;
            }
        }
        if (-1 < payChoice && payChoice < 3) {
            if (usableAmmo[payChoice]) {
                ammoChoice[payChoice] = true;
                powerUpChoice = null;
                colourChoice = null;
            }
        } else {
            powerUpChoice = powerUpsType[payChoice - 3];
            colourChoice = powerUpsColour[payChoice - 3];
        }


        return new GenericPayChoiceEvent(getUser(), ammoChoice, powerUpChoice, colourChoice);
    }

    /**
     * RemoteViewInterface implementation: User chooses a target character
     *
     * @param possibleTargets character available to hit
     * @return event that contains player's choice
     */
    @Override
    public Event targetingScopeTargetChoice(ArrayList<Character> possibleTargets) {
        CharacterChoiceEvent message = (CharacterChoiceEvent) characterChoice(possibleTargets);
        return new TargetingScopeTargetChoiceEvent(getUser(), message.getChosenCharacter());
    }

    /**
     * RemoteViewInterface implementation: User chooses to use one of his power up during an action
     *
     * @param powerUpNames   available power up
     * @param powerUpColours colour of power up
     * @return event that contains player's choice
     */
    @Override
    public Event whileActionPowerUpRequestEvent(String[] powerUpNames, CubeColour[] powerUpColours) {
        String choice = answerControl();
        Event selected = null;
        if (choice.equalsIgnoreCase("Y")) {
            PowerUpChoiceEvent message = (PowerUpChoiceEvent) powerUpChoice(powerUpNames, powerUpColours);
            selected = new WhileActionPowerUpChoiceEvent(getUser(), true, message.getCard(), message.getPowerUpColour());
        } else {
            selected = new WhileActionPowerUpChoiceEvent(getUser(), false, null, null);
        }

        return selected;
    }

    /**
     * RemoteViewInterface implementation: It shows the possibility to use one power up at the end of turn(no your turn)
     *
     * @param powerUpNames      list of  power up's name available to use
     * @param powerUpColours    list of power up's colour available to use
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

        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "You receive damage");


        choice = answerControl();

        if (choice.equalsIgnoreCase("y")) {
            for (int i = 0; i < powerUpNames.length; i++) {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + findColorEscape(powerUpColours[i].toString()) + powerUpNames[i] + " OPTION " + i);
            }

            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "You can choose at most " + maxUsablePowerUps + " option,type 404 to end: ");

            for (int i = 0; i < maxUsablePowerUps; i++) {
                index = 600;
                while (index == 600) {
                    try {

                        System.out.flush();

                        index = CLIHandler.intRead();


                    } catch (IllegalArgumentException e) {
                        index = 404;
                    }

                    if (index == 404) {
                        i = powerUpNames.length;
                    } else {
                        if (index < 0 || index >= maxUsablePowerUps) {
                            System.out.print("Invalid choice \n");
                            i--;

                        } else {
                            powerUpChoice[i] = powerUpNames[index];
                            colourChoice[i] = powerUpColours[index];
                        }
                    }
                }
            }

            message = new EndRoundPowerUpChoiceEvent(getUser(), powerUpChoice, powerUpColours);


        } else if (choice.equalsIgnoreCase("n")) {
            message = new EndRoundPowerUpChoiceEvent(getUser(), new String[]{}, new CubeColour[]{});
        }
        return message;
    }

    /**
     * RemoteViewInterface implementation: User chooses which game would like to join
     *
     * @param available      : availableChoice[0] = new game
     *                       : availableChoice[1] = wait lobby
     *                       : availableChoice[2] = started game
     * @param startedLobbies Name of started game
     * @param waitingLobbies Games are going to begin
     * @return event that contains player's choice
     */
    @Override
    public Event welcomeChoice(boolean[] available, ArrayList<String> startedLobbies, ArrayList<String> waitingLobbies) {
        int choice = 404;
        int lobbyChoice = 404;
        int userChoice = 404;
        Event messageChoice = null;

        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "WELCOME!" +
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

        do {

            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Select a correct option:");
            choice = CLIHandler.intRead();

        } while (choice < 0 || choice > 3 || !available[choice]);

        if (choice == 0) {
            messageChoice = new NewGameChoiceEvent(getUser());
        }

        if (choice == 1) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "\n-Waiting lobbies:");
            lobbyChoice = CLIHandler.arraylistPrintRead(waitingLobbies);
            messageChoice = new LobbyChoiceEvent(getUser(), waitingLobbies.get(lobbyChoice));

        }

        if (choice == 2) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "\n-Started lobbies:");
            lobbyChoice = CLIHandler.arraylistPrintRead(startedLobbies);
            messageChoice = new LobbyChoiceEvent(getUser(), startedLobbies.get(lobbyChoice));

        }
        return messageChoice;

    }

    /**
     * RemoteViewInterface implementation: User choose one way for pay
     *
     * @param powerUpNames          list of power up's name available to use
     * @param powerUpColours        list of power up's colour available to use
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return index of powerUp selected
     */
    private int[] payment(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        ArrayList<Integer> selected = new ArrayList<Integer>();
        int index;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "It's time to pay:");
        String choice = answerControl();

        if (choice.equalsIgnoreCase("Y")) {

            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Minimum powerUP request:\n");
            colourPowerUpRequest(minimumPowerUpRequest);
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Max powerUP request:\n ");
            colourPowerUpRequest(maximumPowerUpRequest);
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + "\n");

            for (int i = 0; i < powerUpNames.length; i++) {
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + findColorEscape(powerUpColours[i].toString()) + powerUpNames[i] + " OPTION " + i);
            }

            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Select from your powerUp:[type" +
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
                    if (index == 404) {
                        i = powerUpNames.length;
                    } else {
                        if (index < 0 || index >= powerUpNames.length) {
                            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Invalid choice \n");
                            i--;

                        } else {
                            selected.add(index);
                            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Select another power Up or type 404 to terminate");
                        }
                    }
                }
            }
        } else if (choice.equalsIgnoreCase("N")) {
            return new int[]{404};
        }

        int[] indexChoose = new int[selected.size()];
        for (int i = 0; i < selected.size(); i++) {
            indexChoose[i] = selected.get(i);

        }
        return indexChoose;
    }

    /**
     * RemoteViewInterface implementation: it finds color by name
     *
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
     * RemoteViewInterface implementation: it prints request of power up
     *
     * @param powerUpRequest pos 0-RED
     *                       pos 1-YELLOW
     *                       pos 2-BLUE
     */
    private void colourPowerUpRequest(int[] powerUpRequest) {
        if (powerUpRequest[0] != 0) {
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_RED.escape() + powerUpRequest[0] + " RED\n");
        }
        if (powerUpRequest[1] != 0) {
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_YELLOW.escape() + powerUpRequest[1] + " YELLOW\n");

        }
        if (powerUpRequest[2] != 0) {
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_BLUE.escape() + powerUpRequest[2] + " BLUE\n");
        }
    }

    /**
     * RemoteViewInterface implementation: It checks if input is equals to Y (yes) or N (no)
     *
     * @return user choice
     */
    private String answerControl() {
        String choice = "";
        while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Would you like to use one of your power up?[Y/N]");
            choice = CLIHandler.stringRead();
        }
        return choice;
    }


}