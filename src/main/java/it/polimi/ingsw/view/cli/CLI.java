package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.*;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.graph.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

//todo deve resettare i colori del terminale!
public class CLI extends RemoteView {


    private CLIMap map;
    private ArrayList<CLIPlayerBoard> playerBoards;//una per ogni player
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


    /**
     *
     * @return
     */
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

    /**
     *
     * @param availableCharacters
     * @return
     */
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
        int map = 404;
        while (map == 404) {
            try {

                while(map<0||map>3) {

                    System.out.println("option 0 for twelve squares" +
                            "\noption 1 for eleven squares" +
                            "\noption 2 for eleven squares" +
                            "\noption 3 for ten squares(recommended for three players)");
                    System.out.println("Choose a map from the following(select number):");
                    System.out.flush();

                    map = CLIHandler.intRead();
                }
                this.map = new CLIMap(map);
            } catch (IllegalArgumentException e) {

                map = 404;
            }
        }


        //todo modalità selezionata
        GameChoiceEvent message = new GameChoiceEvent(getUser(), map,0);
        return message;
    }

    public CLIMap getMap() {
        return map;
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

    /**
     *
     * @param reloadableWeapons
     * @return
     */
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
    public Event respawnChoice(String[] powerUpNames, CubeColour[] powerUpColours) {
        //todo assocciare colore alle carte
        ArrayList<String> powerUpList = new ArrayList<>(Arrays.asList(powerUpNames));
        ArrayList<CubeColour> coloursList = new ArrayList<>(Arrays.asList(powerUpColours));
        String chosenPowerUp = null;
        while (chosenPowerUp == null) {
            try {
                System.out.println("You should respwan");
                CLIHandler.arrayPrint(powerUpList);
                chosenPowerUp =CLIHandler.stringRead().toUpperCase();
            } catch (IllegalArgumentException e) {
                chosenPowerUp = null;
            }

        }
        return new SpownChoiceEvent(getUser(), chosenPowerUp, coloursList.get(powerUpList.indexOf(chosenPowerUp)));
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

    /**
     *
     * @param yourWeapons
     * @return
     */
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
        return new WeaponChoiceEvent(getUser(),weaponSelected);
    }

    /**
     *
     * @param availableWeapon
     * @return
     */
    @Override
    public Event weaponGrabChoice(ArrayList<String> availableWeapon) {
        String weaponSelected = null;
        while (weaponSelected==null) {
            try {
                System.out.println("You choose to grab");
                CLIHandler.arrayPrint(availableWeapon);
                weaponSelected = CLIHandler.stringRead();
            } catch (IllegalArgumentException e) {
                weaponSelected = null;

            }
        }
        return new WeaponGrabChoiceEvent(getUser(),weaponSelected);
    }

    /**
     *
     * @param availableWeaponEffects
     * @return
     */
    @Override
    public Event weaponEffectChoice(boolean[] availableWeaponEffects) {
        int effectChoice = 404;

        for (int i =0; i<= availableWeaponEffects.length;i++)
        {
            System.out.println("effect "+i);
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

    /**
     * Every tima one player joins the game it's notified to other player
     * @param newPlayer
     * @return
     */
    @Override
    public Event newPlayerJoinedUpdate(String newPlayer) {


        System.out.println("New player joined the game:"+newPlayer);
             //TODO   "character choice:"+mapCharacterNameColors.get(newPlayer));

        return new UpdateChoiceEvent(getUser());
    }

    /**
     *
     * @param x
     * @param y
     * @param fistColour
     * @param secondColour
     * @param thirdColour
     * @return
     */
    @Override
    public Event addAmmoTileUpdate(int x, int y, String fistColour, String secondColour, String thirdColour) {
        String[] color = {fistColour, secondColour};
        CLIPrintableElement currElement;
        if(thirdColour.equals("POWERUP")) {

             currElement = new CLIPrintableElement(true, color);
        }else {
            color[2] = thirdColour;
            currElement = new CLIPrintableElement(false,color);
        }
        map.updateResource(currElement ,x ,y);
        return new UpdateChoiceEvent(getUser());
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public Event removeAmmoTileUpdate(int x, int y) {
        CLIPrintableElement currElement= new CLIPrintableElement(false);
        map.updateResource(currElement,x,y);
        return new UpdateChoiceEvent(getUser());
    }

    /**
     *
     * @param currCharacter
     * @param x
     * @param y
     * @return
     */
    @Override
    public Event positionUpdate(Character currCharacter, int x, int y) {
        CLIPrintableElement currElement = new CLIPrintableElement(currCharacter,mapCharacterNameColors.get(currCharacter));
        map.updateResource(currElement,x,y);
        return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event PlayerBoardUpdate(Character currCharacter, int damageToken, int markNumber) {
        for (CLIPlayerBoard currentPlayerBoard:playerBoards
        ) {
            if(currentPlayerBoard.getCharacter() == currCharacter) {
                currentPlayerBoard.markDamageUpdate(damageToken,markNumber);//deve essere quella del payer corretto
                return new UpdateChoiceEvent(getUser());
            }
        }
        return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event playerPowerUpUpdate(Character currCharacter, Map<String, CubeColour> powerUps) {
        for (CLIPlayerBoard currentPlayerBoard:playerBoards
        ) {
            if(currentPlayerBoard.getCharacter() == currCharacter) {

                //playerBoard.yourPowerUpAdd('P',powerUps); todo come gestire la map tra colore e power up
                return new UpdateChoiceEvent(getUser());
            }
        }

        return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event playerWeaponUpdate(Character currCharacter, String[] weapons) {
        for (CLIPlayerBoard currentPlayerBoard:playerBoards
             ) {
           if(currentPlayerBoard.getCharacter() == currCharacter) {

               currentPlayerBoard.gadgetsUpdate('W', weapons);
               return new UpdateChoiceEvent(getUser());
           }
        }
        return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event playerAmmoUpdate(Character currCharacter, ArrayList<AmmoCube> ammo) {
        int size = ammo.size();
        String[] ammoString = new String[size];
        int i=0;
        for (AmmoCube ammoCube:ammo
             ) {

            ammoString[i] = ammoCube.getColour().name();
            i++;
        }
        for (CLIPlayerBoard currentPlayerBoard:playerBoards
        ) {
            if(currentPlayerBoard.getCharacter() == currCharacter) {

                currentPlayerBoard.gadgetsUpdate('A',ammoString );
                return new UpdateChoiceEvent(getUser());
            }
        }
        return null;
    }

    @Override
    public Event gameTrackSkullUpdate(Character currCharacter, int skullNumber) {
        String[] skull = new String[skullNumber];
        skull[0] = "☠";

        if(skullNumber==2){
            skull[1]= "☠";
        }



        for (CLIPlayerBoard currentPlayerBoard:playerBoards
        ) {
            if(currentPlayerBoard.getCharacter() == currCharacter) {

                currentPlayerBoard.gadgetsUpdate('S', skull);
                return new UpdateChoiceEvent(getUser());
            }
        }

        //todo chiamata alla gameTrack e aggiunta segnalini del colore character
        return new UpdateChoiceEvent(getUser());

    }
}
