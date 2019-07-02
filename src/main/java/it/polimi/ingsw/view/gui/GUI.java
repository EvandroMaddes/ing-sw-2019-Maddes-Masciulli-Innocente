package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.model_view_event.EndGameUpdate;
import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.event.view_server_event.NewGameChoiceEvent;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

// TODO: 02/07/2019 chiudere il primary stage prima di mostare la welcomeChoice  modificare mapCharacterChoiceFxml in mapChoice

public class GUI extends RemoteView {
    private DecodeMessage decodeMessage = new DecodeMessage();

    private LobbyChioceController lobbyController;
    private GameBoardController gameBoardController;
    private MapCharacterController mapCharacterController;
    private LoginController loginController;

    private Stage primaryStage;
    private Stage prevStage;
    private Stage lobbyStage;
    private Stage gameBoardStage;
    private Stage mapCharacterStage;

    private Scene gameboardScene;
    private Scene lobbyScene;
    private Scene mapharacterChoiceScene;

    private String[] clientChoices = new String[3];

    private String choice;
    private String prova;

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
        FXMLLoader gameBoardFxml = new FXMLLoader(getClass().getResource("/fxml/gameBoard.fxml"));
        FXMLLoader mapCharacterFxml = new FXMLLoader(getClass().getResource("/fxml/mapCharacterChoice.fxml"));
        try {
            lobby = lobbyFxml.load();
            gameboard = gameBoardFxml.load();
            mapCharacter = mapCharacterFxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lobbyController = lobbyFxml.getController();
        gameBoardController = gameBoardFxml.getController();
        mapCharacterController = mapCharacterFxml.getController();

        gameBoardController.setGui(this);
        lobbyController.setGui(this);
        lobbyStage = new Stage();
        mapCharacterStage = new Stage();
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
        mapCharacterController.setGui(this);
        lobbyScene = new Scene(lobby, 800, 560);
        gameboardScene = new Scene(gameboard, 800, 560);
        mapharacterChoiceScene = new Scene(mapCharacter, 400, 280);

        lobbyStage.setScene(lobbyScene);

        System.out.println("fine configurazione GUI");

        /***********FUNZIONA**************
         Image weapon = decodeMessage.loadImage(decodeMessage.findWeaponImage("FURNACE"));
         Platform.runLater(()->{
         primaryStage.setScene(gameboardScene);
         gameBoardController.setFirstWeaponSpawnBlueImage(weapon);
         primaryStage.show();
         });
         */
    }

    public void metodoprova() {
        boolean[] available = new boolean[3];
        available[0] = true;
        available[1] = true;
        available[2] = true;

        ArrayList<String> wait = new ArrayList<String>();
        wait.add("wait1");
        wait.add("wait2");
        wait.add("wait3");

        ArrayList<String> started = new ArrayList<String>();
        started.add("started1");
        started.add("started2");
        started.add("started3");

        ArrayList<String> name = new ArrayList<String>();
        name.add("name1");
        name.add("name2");
        name.add("name3");

        welcomeChoice(available, started, wait);
    }

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

    /**
     * chiamato dai controller delle scene gira il messaggio al server
     * //TODO frti tornarre nel metodo chiamante il controller delle sceene il messaggio
     *
     * @param choice
     */
    public void sendMessage(Event choice) {
        System.out.println(choice.getUser());

    }

    //todo aggiunto per essere chiamato da client
    @Override
    public void printScreen() {

    }

    public void setChoice(String choice) {
        this.choice = choice;
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
        ArrayList<String> charctersName = new ArrayList<>();
        charctersName.add(availableCharacters.get(0).name());
        charctersName.add(availableCharacters.get(1).name());
        charctersName.add(availableCharacters.get(2).name());
        charctersName.add(availableCharacters.get(3).name());
        Platform.runLater(() -> mapCharacterController.setCharacterComboBox(charctersName));

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
        final Task<Event> query = new Task<Event>(){
            @Override
            public Event call() throws Exception {
                try {
                    mapCharacterController.setWindow(mapCharacterStage);
                    ArrayList<Integer> mapChoice = new ArrayList<Integer>();
                    mapChoice.add(0);
                    mapChoice.add(1);
                    mapChoice.add(2);
                    mapChoice.add(3);
                    mapCharacterController.setMapComboBox(mapChoice);
                } catch (Exception e) {
                }
                Event event = mapCharacterController.ask(mapharacterChoiceScene);
                return event;
            }
        };
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
    public Event actionChoice(boolean fireEnable) {
        System.out.println("action choice");
        Platform.runLater(() -> {
                    primaryStage.setScene(gameboardScene);
                    primaryStage.show();
                    System.out.println("run later");
                    gameBoardController.getFireButton().setDisable(!fireEnable);
                }
        );
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
        // TODO: 02/07/2019 renderlo un metodo 
        Thread th = new Thread(query);
        th.start();
        try{
            Event event = query.get();
            return event;
        }catch(Exception interrupted){
            CustomLogger.logException(interrupted);
            return null;
        }


        /*Platform.runLater(
                () -> {
                    lobbyController.setLobby(available, startedLobbies, waitingLobbies);
                    lobbyStage.setScene(lobbyScene);
                });
         return lobbyController.ask(lobbyScene);

         */
    }

    @Override
    public Event newPlayerJoinedUpdate(String newPlayer, Character characterChoice) {
        return null;
    }

    @Override
    public Event playerBoardUpdate(Character character, int skullNumber, Character[] marks, Character[] damages) {
        return null;
    }

    @Override
    public Event playerAmmoUpdate(Character currCharacter, ArrayList<AmmoCube> ammo) {
        return null;
    }

    @Override
    public Event playerWeaponUpdate(Character currCharacter, String[] weapons, boolean[] load) {
        return null;
    }

    @Override
    public Event weaponReplaceUpdate(int x, int y, String[] weapon) {
        return null;
    }

    @Override
    public Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, CubeColour[] color) {
        return null;
    }

    @Override
    public Event playerReconnectionNotify(String user, Character character, boolean disconnected) {
        return null;
    }
}
