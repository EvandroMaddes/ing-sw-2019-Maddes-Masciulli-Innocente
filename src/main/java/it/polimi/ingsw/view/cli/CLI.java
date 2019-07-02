package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.model_view_event.EndGameUpdate;
import it.polimi.ingsw.event.server_view_event.ReconnectionRequestEvent;
import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
import it.polimi.ingsw.event.view_controller_event.*;
import it.polimi.ingsw.event.view_server_event.LobbyChoiceEvent;
import it.polimi.ingsw.event.view_server_event.NewGameChoiceEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.utils.CustomTimer;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.graph.*;
import java.util.*;

/**
 * It menages interaction with user throw command line
 */
public class CLI extends RemoteView {

    private CLIDisplay display;
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


    @Override
    public boolean isGameSet() {
        if( getDisplay().getMap()!=null){
            return true;
        }
        else{
            return false;
        }
    }


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


    @Override
    public void setGame(int mapNumber) {
        display.setMap(new CLIMap(mapNumber));
        CLIGameTrack gameTrack = new CLIGameTrack();
        gameTrack.createGameTrack();
        display.setGameTrack(gameTrack);
    }



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


    @Override
    public Event reloadChoice(ArrayList<String> reloadableWeapons) {
        int selected ;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape() +"Select one weapon to reload or type other to skip action.");

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


    @Override
    public Event weaponEffectSquareChoice(int[] possibleSquareX, int[] possibleSquareY) {
       PositionChoiceEvent message =(PositionChoiceEvent) positionMoveChoice(possibleSquareX,possibleSquareY);
       Event choice = new WeaponSquareTargetChoiceEvent(getUser(), message.getPositionX(),message.getPositionY());

        return choice;

    }


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


    @Override
    public Event weaponDiscardChoice(ArrayList<String> yourWeapons) {
        int weaponSelected ;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You choose to discard one weapon:");
        weaponSelected = CLIHandler.arraylistPrintRead(yourWeapons);
        return new WeaponDiscardChoiceEvent(getUser(), yourWeapons.get(weaponSelected));
    }


    @Override
    public Event weaponChoice(ArrayList<String> availableWeapons) {
        int weaponSelected;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You choose to fire ");
        weaponSelected = CLIHandler.arraylistPrintRead(availableWeapons);
        return new WeaponChoiceEvent(getUser(), availableWeapons.get(weaponSelected));
    }


    @Override
    public Event weaponGrabChoice(ArrayList<String> availableWeapon) {
        int weaponSelected;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You choose to garb ");
        weaponSelected = CLIHandler.arraylistPrintRead(availableWeapon);
        return new WeaponGrabChoiceEvent(getUser(), availableWeapon.get(weaponSelected));
    }


    @Override
    public Event winnerUpdate(EndGameUpdate endGameUpdate) {
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+endGameUpdate.getEndGameMessage());
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }


    @Override
    public Event weaponEffectChoice(boolean[] availableWeaponEffects) {
        int effectChoice = 40;
        int first= 404, second=404 , third = 404;
        Event message;

        if(availableWeaponEffects[0]){
             first = 0;
        }

        if(availableWeaponEffects[1]){
             second = 1;
        }

        if(availableWeaponEffects[2]){
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
            effectChoice++;
            message = new WeaponEffectChioceEvent(getUser(), effectChoice);

        return message;
    }


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
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape().concat(Color.ANSI_GREEN.escape().concat("Skip Choice OPTION "+powerUpNames.length)));
                System.out.println("Select one powerUp:[option number]");
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


    @Override
    public Event shotMoveChoiceEvent(int[] possibleSquareX, int[] possibleSquareY) {
        System.out.println("Select one square before shooting:");
        PositionChoiceEvent message =(PositionChoiceEvent) positionMoveChoice(possibleSquareX,possibleSquareY);
        Event choice = new ShotMoveChoiceEvent(getUser(), message.getPositionX(),message.getPositionY());
        return choice;
    }

    @Override
    public void printScreen() {
        display.createDisplay();
        display.printDisplay();
    }

    @Override
    public Event playerReconnectionNotify(String user, Character character, boolean disconnected) {
        String optional;
        String suffix;
        if(disconnected){
            optional = " disconnected: ";
            suffix = " isn't anymore in the arena!";
        }
        else{
            optional = " reconnected: ";
            suffix = " is back in the arena!";
        }
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+user+optional+Color.ANSI_BLACK_BACKGROUND.escape()+mapCharacterNameColors.get(character)+character.name().concat(suffix));
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }


    @Override
    public Event newPlayerJoinedUpdate(String newPlayer, Character characterChoice) {
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"New player joined the game:" + newPlayer+" with "+Color.ANSI_BLACK_BACKGROUND.escape()+mapCharacterNameColors.get(characterChoice)+characterChoice.name());
        CLIPlayerBoard player = new CLIPlayerBoard(newPlayer,characterChoice,mapCharacterNameColors);
        display.setPlayerBoard(player);
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }


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


    @Override
    public Event removeAmmoTileUpdate(int x, int y) {
        CLIPrintableElement currElement = new CLIPrintableElement(false);
        display.getMap().updateResource(currElement, x, y);
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }


    @Override
    public Event positionUpdate(Character currCharacter, int x, int y) {

        CLIPrintableElement currElement = new CLIPrintableElement(currCharacter, mapCharacterNameColors.get(currCharacter));
        if(x==404 && y== 404){
            display.getMap().removePlayer(currElement.getResource());
        } else{
            display.getMap().updateResource(currElement, x, y);
        }
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }


    @Override
    public Event playerBoardUpdate(Character character, int skullNumber, Character[] marks, Character[] damages) {
        display.getPlayerBoard(character).clean(2);//MARKS
        display.getPlayerBoard(character).clean(3);//DAMAGE
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


    @Override
    public Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, CubeColour[] colour) {

        for (int i = 0; i < powerUp.length; i++) {
            powerUp[i] = findColorEscape(colour[i].name()) + powerUp[i];
        }
        display.getPlayerBoard(currCharacter).gadgetsUpdate('P', powerUp);

        return new UpdateChoiceEvent(BROADCASTSTRING);
    }


    @Override
    public Event playerWeaponUpdate(Character currCharacter, String[] weapons,boolean[] load) {
        for (int i=0; i<load.length; i++){
            if (load[i]){
                weapons[i] = Color.ANSI_GREEN.escape()+weapons[i];
            }else
                weapons[i] = Color.ANSI_RED.escape()+weapons[i];
        }

        display.getPlayerBoard(currCharacter).gadgetsUpdate('W', weapons);

        return new UpdateChoiceEvent(BROADCASTSTRING);
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

        return new UpdateChoiceEvent(BROADCASTSTRING);
    }

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

    @Override
    public Event weaponReplaceUpdate(int x, int y, String[] weapon) {
        display.weaponsSpawnSquare(x, y, weapon);
        return new UpdateChoiceEvent(BROADCASTSTRING);
    }

    @Override
    public Event newtonTargetChoice(ArrayList<Character> availableTargets, int numTarget) {

       CharacterChoiceEvent message =(CharacterChoiceEvent)characterChoice(availableTargets);
       Event choice = new NewtonPlayerTargetChoiceEvent(getUser(),message.getChosenCharacter());
        return choice;
    }

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

    /*
     * int arrays are built like that:
     * [0] - # Red
     * [1] - # Yellow
     * [2] - # Blue
     */
    @Override
    public Event weaponEffectPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest){
        Event message;
        int[] index = payment(powerUpNames,powerUpColours,minimumPowerUpRequest,maximumPowerUpRequest);
        String[] nameSelected = new String[index.length];
        CubeColour[] colourSelected = new CubeColour[index.length];
        if(index ==null){
            message = new WeaponEffectPaymentChoiceEvent(getUser(),null,null);
        }else {
            for (int i = 0; i < index.length; i++) {
                nameSelected[i] = powerUpNames[index[i]];
                colourSelected[i] = powerUpColours[index[i]];
            }
            message = new WeaponEffectPaymentChoiceEvent(getUser(), nameSelected, colourSelected);
        }
        return message;
    }


    @Override
    public Event weaponGrabPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        Event message;
        int[] index = payment(powerUpNames,powerUpColours,minimumPowerUpRequest,maximumPowerUpRequest);
        String[] nameSelected = new String[index.length];
        CubeColour[] colourSelected = new CubeColour[index.length];
        if(index ==null){
            message = new WeaponGrabPaymentChoiceEvent(getUser(),null,null);
        }else {
            for (int i = 0; i < index.length; i++) {
                nameSelected[i] = powerUpNames[index[i]];
                colourSelected[i] = powerUpColours[index[i]];
            }

            message = new WeaponGrabPaymentChoiceEvent(getUser(), nameSelected, colourSelected);
        }
        return message;
    }


    @Override
    public Event weaponReloadPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest){
        Event message;
        int[] index = payment(powerUpNames,powerUpColours,minimumPowerUpRequest,maximumPowerUpRequest);
        String[] nameSelected = new String[index.length];
        CubeColour[] colourSelected = new CubeColour[index.length];

        if(index ==null){
            message = new WeaponReloadPaymentChoiceEvent(getUser(),null,null);
        }else {
            for (int i = 0; i < index.length; i++) {
                nameSelected[i] = powerUpNames[index[i]];
                colourSelected[i] = powerUpColours[index[i]];
            }
            message = new WeaponReloadPaymentChoiceEvent(getUser(), nameSelected, colourSelected);
        }
        return message;
    }


    @Override
    public Event genericPaymentChoice(boolean[] usableAmmo, String[] powerUpsType, CubeColour[] powerUpsColour) {
        int payChoice= 404 ;
        boolean[] ammoChoice = {false,false,false};
        String powerUpChoice = null;
        CubeColour colourChoice = null;

        System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"You have follow ammo: \n");
        if (usableAmmo[0]){
            //RED
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_RED.escape()+"RED option 0\t");
        }
        if (usableAmmo[1]){
            //YELLOW
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_YELLOW.escape()+"YELLOW option 1\t");
        }
        if (usableAmmo[2]){
            //BLUE
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_BLUE.escape()+"BLUE option 2\t");
        }
        if (usableAmmo[0] == false && usableAmmo[1] == false && usableAmmo[2] ==false){
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"no ammo in your bag");
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
                if (payChoice<0 || payChoice>(2+powerUpsType.length)){
                    System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "no item for this option");
                    payChoice = 404;
                }
                if (-1<payChoice && payChoice<3 ){
                    if (!usableAmmo[payChoice]){
                        System.out.print(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "no item for this option");
                        payChoice = 404;
                    }
                }

            } catch (IllegalArgumentException e) {
                payChoice = 404;
            }
        }
            if (-1<payChoice && payChoice<3 ){
                if (usableAmmo[payChoice]){
                 ammoChoice[payChoice] = true;
                 powerUpChoice = null;
                 colourChoice = null;
                }
            }
            else {
                powerUpChoice = powerUpsType[payChoice-3];
                colourChoice = powerUpsColour[payChoice-3];
            }


        return new GenericPayChoiceEvent(getUser(),ammoChoice,powerUpChoice,colourChoice);
    }


    @Override
    public Event targetingScopeTargetChoice(ArrayList<Character> possibleTargets) {
        CharacterChoiceEvent message =(CharacterChoiceEvent) characterChoice(possibleTargets);
        Event choice = new TargetingScopeTargetChoiceEvent(getUser(), message.getChosenCharacter());
        return choice;
    }


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

            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Select a correct option:");
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
        ArrayList<Integer> selected = new ArrayList<Integer>() ;
        int index ;
        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape() +"It's time to pay:");
        String choice = answerControl();

        if (choice.equalsIgnoreCase("Y")) {

            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+ "Minimum powerUP request:\n");
            colourPowerUpRequest(minimumPowerUpRequest);
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+ "Max powerUP request:\n ");
            colourPowerUpRequest(maximumPowerUpRequest);
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+"\n");

            for(int i=0;i<powerUpNames.length;i++){
                System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+findColorEscape(powerUpColours[i].toString())+powerUpNames[i]+" OPTION "+i);
            }

            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Select from your powerUp:[type" +
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
                            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Invalid choice \n");
                            i--;

                        }else {
                            selected.add(index);
                            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Select another power Up or type 404 to terminate");
                        }
                    }
                }
            }
        }
        else if(choice.equals("N"))
            selected = null;

        int[] indexChoose = new int[selected.size()];
        for (int i =0; i< selected.size();i++) {
            indexChoose[i] = selected.get(i);

        }
        return indexChoose;
    }

    /**
     * it finds color by name
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
     * it prints request of power up
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
     * It checks if input is equals to Y (yes) or N (no)
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