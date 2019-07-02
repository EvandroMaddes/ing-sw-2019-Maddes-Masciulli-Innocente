package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.model_view_event.EndGameUpdate;
import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.UpdateChoiceEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.utils.CustomLogger;
import it.polimi.ingsw.view.RemoteView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

// TODO: 02/07/2019 chiudere il primary stage prima di mostare la welcomeChoice  modificare mapCharacterChoiceFxml in mapChoice

public class GUI extends RemoteView {
    private DecodeMessage decodeMessage = new DecodeMessage();

    private LobbyChioceController lobbyController;
    private GameBoardController gameBoardController;
    private MapCharacterController mapController;

    private Stage primaryStage;
    private Stage lobbyStage;
    private Stage gameBoardStage;
    private Stage mapStage;

    private Scene gameboardScene;
    private Scene lobbyScene;
    private Scene mapChoiceScene;

    private String[] clientChoices = new String[3];
    private Character characterChoose ;
    

    /**
     * inizializzazione e caricamento stage
     * todo ogni metodo chiamerà set stage
     *
     * @return
     */
    @Override
    public String[] gameInit() {
        return clientChoices;
    }

    public GUI(Stage primaryStage){
        this.primaryStage = primaryStage;
    }


    public void initialize() {

        Parent lobby = null;
        Parent gameboard = null;
        Parent mapCharacter = null;

        FXMLLoader lobbyFxml = new FXMLLoader(getClass().getResource("/fxml/lobbyScene.fxml"));
        FXMLLoader gameBoardFxml = new FXMLLoader(getClass().getResource("/fxml/gameboardScene.fxml"));
        FXMLLoader mapCharacterFxml = new FXMLLoader(getClass().getResource("/fxml/mapChoice.fxml"));
        try {
            lobby = lobbyFxml.load();
            gameboard = gameBoardFxml.load();
            mapCharacter = mapCharacterFxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lobbyController = lobbyFxml.getController();
        gameBoardController = gameBoardFxml.getController();
        mapController = mapCharacterFxml.getController();

        gameBoardController.setGui(this);
        lobbyController.setGui(this);
        lobbyStage = new Stage();
        mapStage = new Stage();
        gameBoardStage = new Stage();
/*

        lobbyStage = ((Stage)lobbyController.getScene().getWindow());
        setPrimaryStage((Stage)enterButton.getScene().getWindow());
        gameBoardStage = new Stage();
        mapCharacterStage = new Stage();
        lobbyStage.setScene(new Scene(lobby, 800, 560));
        gameBoardStage.setScene(new Scene(gameboard,800,560));
        mapCharacterStage.setScene(new Scene(mapCharacter,400,280));
        lobbyStage.setTitle("Lobby-ADRENALINE");
        mapCharacterStage.setTitle("mapCharacter-ADRENALINE");
        gameBoardStage.setTitle("GameBoard-ADRENALINE");
 */
        mapController.setGui(this);
        lobbyScene = new Scene(lobby, 800, 560);
        gameboardScene = new Scene(gameboard, 800, 560);
        mapChoiceScene = new Scene(mapCharacter, 400, 120);

        lobbyStage.setScene(lobbyScene);
        gameBoardStage.setScene(gameboardScene);
        gameBoardController.init();
        //primaryStage.close(); non mostra il secondo stage prova con la reduce
        System.out.println("fine configurazione GUI");
        metodoPROVA();

        /***********FUNZIONA**************
         Image weapon = decodeMessage.loadImage(decodeMessage.findWeaponImage("FURNACE"));
         Platform.runLater(()->{
         primaryStage.setScene(gameboardScene);
         gameBoardController.setFirstWeaponSpawnBlueImage(weapon);
         primaryStage.show();
         });
         */
    }

    public void metodoPROVA(){

        Platform.runLater(()->gameBoardStage.show());
    }

    /**
     *
     * @param choices
     */
    public void setClientChoices(String[] choices) {
        clientChoices = choices;
        System.out.println("\nUser: " + clientChoices[0] +
                "\nConnection: " + clientChoices[1] +
                "\nIp: " + clientChoices[2]);
    }

    @Override
    public Event shotMoveChoiceEvent(int[] possibleSquareX, int[] possibleSquareY) {
        return null;
    }
    

    //todo aggiunto per essere chiamato da client
    @Override
    public void printScreen() {

    }

    
    @Override
    public void setGame(int mapNumber) {
    }

    @Override
    public boolean isGameSet() {
        return false;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public Event winnerUpdate(EndGameUpdate endGameUpdate) {
        return null;
    }

    @Override
    public Event whileActionPowerUpRequestEvent(String[] powerUpNames, CubeColour[] powerUpColours) {
        return null;
    }

    @Override
    public Event gameTrackSkullUpdate(Character[] killerCharacter, int[] skullNumber) {
        // TODO: 02/07/2019
        return null;
    }

    @Override
    public Event genericPaymentChoice(boolean[] usableAmmo, String[] powerUpsType, CubeColour[] powerUpsColour) {
        return null;
    }

    @Override
    public Event endRoundPowerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours, int maxUsablePowerUps) {
        return null;
    }

    @Override
    public Event weaponReloadPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        return null;
    }

    @Override
    public Event weaponGrabPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        return null;
    }

    @Override
    public Event weaponEffectPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        return null;
    }

    @Override
    public Event newtonTeleporterTargetSquareChoice(int[] possibleSquareX, int[] possibleSquareY) {
        return null;
    }

    @Override
    public Event printUserNotification(UsernameModificationEvent usernameEvent) {
        return null;
    }

    @Override
    public Event positionUpdate(Character currCharacter, int x, int y) {
        return null;
    }


    @Override
    public Event newtonTargetChoice(ArrayList<Character> availableTargets, int maxTarget) {
        return null;
    }


    @Override
    public Event characterChoice(ArrayList<Character> availableCharacters) {

        return null;
    }

    public void setCharacterChoose(Character characterChoose) {
        this.characterChoose = characterChoose;
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
        final Task<Event> query = new Task<Event>(){
            @Override
            public Event call() throws Exception {
                try {
                    mapController.setWindow(mapStage);
                    ArrayList<Integer> mapChoice = new ArrayList<Integer>();
                    mapChoice.add(0);
                    mapChoice.add(1);
                    mapChoice.add(2);
                    mapChoice.add(3);
                    mapController.setMapComboBox(mapChoice);
                } catch (Exception e) {
                }
                Event event = mapController.ask(mapChoiceScene);
                Image[] mapChoice = decodeMessage.mapImage(((GameChoiceEvent)event).getMap());
                gameBoardController.setMap(mapChoice[0],mapChoice[1]);
                return event;
            }
        };
        return userChoice(query);
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
    public Event targetingScopeTargetChoice(ArrayList<Character> possibleTargets) {
        return null;
    }

    @Override
    public Event powerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours) {
        return null;
    }


    @Override
    public Event welcomeChoice(boolean[] available, ArrayList<String> startedLobbies, ArrayList<String> waitingLobbies) {
        final Task<Event> query = new Task<Event>(){
            @Override
            public Event call() throws Exception {
                try {
                    lobbyController.setWindow(lobbyStage);
                    lobbyController.setLobby(available, startedLobbies, waitingLobbies);
                } catch (Exception e) {
                }
                Event event = lobbyController.ask(lobbyScene);
                return event;
            }
        };
    return userChoice(query);
    }

    /**
     * It "takes" choice from controller
     * @param query
     * @return
     */
    private Event userChoice(Task<Event> query){
        Thread th = new Thread(query);
        th.start();
        try{
            Event event = query.get();
            return event;
        }catch(Exception interrupted){
            CustomLogger.logException(interrupted);
            return null;
        }
        }

    @Override
    public Event newPlayerJoinedUpdate(String newPlayer, Character characterChoice) {
        return null;
    }

    @Override
    public Event playerBoardUpdate(Character character, int skullNumber, Character[] marks, Character[] damages) {
        Image[] toAdd = new Image[damages.length];
        for (int i=0; i<damages.length;i++){
             toAdd[i] = decodeMessage.playerTokenImage(damages[i]);
        }
        gameBoardController.setDemage(character,toAdd);
        return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event playerAmmoUpdate(Character currCharacter, ArrayList<AmmoCube> ammo) {

        Image[] ammoToAdd = new Image[ammo.size()];
        for (int i=0; i<ammo.size(); i++) {
            ammoToAdd[i] = decodeMessage.ammoCubeImage(ammo.get(i));
        }
        gameBoardController.setAmmo(currCharacter, ammoToAdd);
        return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event playerWeaponUpdate(Character currCharacter, String[] weapons, boolean[] load) {
         if(characterChoose == currCharacter){
            if(weapons.length<4){
            Image[] toAdd = new Image[weapons.length];
                for (int i=0; i<weapons.length;i++){
                    if (load[i]){
                        toAdd[i] = decodeMessage.weaponImage(weapons[i]);
                    }else{
                        toAdd[i] = decodeMessage.weaponImage("unload");
                    }
                }
                gameBoardController.setPlayerWeapon(toAdd);
         }
        }
            return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event weaponReplaceUpdate(int x, int y, String[] weapon) {
        //xy:01 red
        //   20 blue
        //   32 yellow
        Image[] toAdd = new Image[weapon.length];
        for (int i=0; i<weapon.length;i++){
                toAdd[i] = decodeMessage.weaponImage(weapon[i]);

        }
        gameBoardController.setSpawnWeapon(x,toAdd);
        return new UpdateChoiceEvent(getUser());
    }

    @Override
    public Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, CubeColour[] color) {
        Image[] toAdd = new Image[powerUp.length];
        if(characterChoose == currCharacter){
            for (int i=0; i<powerUp.length;i++){
                    toAdd[i] = decodeMessage.powerUpImage(powerUp[i],color[i]);
                }
            }
            gameBoardController.setPlayerPowerUp(toAdd);
            return new UpdateChoiceEvent(getUser());
        }



    @Override
    public Event playerReconnectionNotify(String user, Character character, boolean disconnected) {
        // TODO: 02/07/2019 chiedi a fra come funziona
        //si chiama gameboard.setInfo() e si passa la stringa da mostrare
        return null;
    }
}
