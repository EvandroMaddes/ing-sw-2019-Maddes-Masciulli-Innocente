package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.server_view_event.LobbySettingsEvent;
import it.polimi.ingsw.event.server_view_event.UsernameModificationEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.view.RemoteView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class GUI extends RemoteView  {

    private LobbyChioceController lobbyController;
    private UsernameController usernameController;
    private GameBoardController gameBoardController;
    private MapCharacterController mapCharacterController;

    private Stage primaryStage;
    private Stage prevStage;
    private Stage lobbyStage;
    private Stage gameBoardStage;
    private Stage mapCharacterStage;

    private String prova;

    //todo aggiunto per essere chiamato da client
    @Override
    public void printScreen() {

    }

    @Override
    public void setGame(LobbySettingsEvent lobbySettings) {
    }

    @Override
    public boolean isGameSet() {
        return false;
    }

    //TODO gestire l'inizializzazione di tutte le scene
    public void setStage(Stage currentStage) {
        Platform.runLater(()->primaryStage.close());
        Platform.runLater(()->this.primaryStage=currentStage);
        Platform.runLater(()->primaryStage.show());

    }



    /**
     * inizializzazione e caricamento stage
     * todo ogni metodo chiamerà set stage
     * @return
     */
    @Override
    public String[] gameInit() {
        return new String[0];
    }

    public String[] gameInit(String[] configuration){

        Parent lobby = null;
        Parent username = null;
        Parent gameboard = null;
        Parent mapCharacter = null;

        FXMLLoader lobbyFxml = new FXMLLoader(getClass().getResource("/lobbyScene.fxml"));
        FXMLLoader gameBoardFxml = new FXMLLoader(getClass().getResource("/gameBoard.fxml"));
        FXMLLoader mapCharacterFxml = new FXMLLoader(getClass().getResource("/mapCharacterChoice.fxml"));


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
        mapCharacterController.setGui(this);

        lobbyStage = new Stage();
        gameBoardStage = new Stage();
        mapCharacterStage = new Stage();


        lobbyStage.setScene(new Scene(lobby, 800, 560));
        gameBoardStage.setScene(new Scene(gameboard,800,560));
        mapCharacterStage.setScene(new Scene(mapCharacter,400,280));

        lobbyStage.setTitle("Lobby-ADRENALINE");
        mapCharacterStage.setTitle("mapCharacter-ADRENALINE");
        gameBoardStage.setTitle("GameBoard-ADRENALINE");

        ArrayList<Character> availableTestedCharacter = new ArrayList<>();
        availableTestedCharacter.add(Character.SPROG);
        availableTestedCharacter.add(Character.BANSHEE);
        availableTestedCharacter.add(Character.D_STRUCT_OR);
        availableTestedCharacter.add(Character.DOZER);
        availableTestedCharacter.add(Character.VIOLET);
        //Platform.runLater(()->metodoprova());
        //gameChoice();
        //characterChoice(availableTestedCharacter);
        System.out.println(gameBoardStage.isResizable());
        Platform.runLater(()->actionChoice(true));


        return configuration;
    }

    public void metodoprova(){
        boolean[] available=new boolean[3];
        available[0]=true;
        available[1]=true;
        available[2]=true;

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

        welcomeChoice(available,started,wait);
    }


    /**
     * chiamato dai controller delle scene gira il messaggio al server
     * //TODO frti tornarre nel metodo chiamante il controller delle sceene il messaggio
     * @param choice
     */
    public void sendMessage(Event choice){
        System.out.println(choice.getUser());
        setToVirtualView(choice);

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public Event winnerUpdate(String user, int point) {
        return null;
    }

    @Override
    public Event whileActionPowerUpRequestEvent(String[] powerUpNames, CubeColour[] powerUpColours) {
        return null;
    }

    @Override
    public Event gameTrackSkullUpdate( Character[] killerCharacter, int[] skullNumber) {
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
        Platform.runLater(()->mapCharacterController.setCharacterComboBox(charctersName));
        setStage(mapCharacterStage);

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
        Platform.runLater(()->mapCharacterController.getMapComboBox().setDisable(false));
        ArrayList<Integer> mapChoice = new ArrayList<Integer>();
        mapChoice.add(0);
        mapChoice.add(1);
        mapChoice.add(2);
        mapChoice.add(3);
        Platform.runLater(()->mapCharacterController.getEnterButton().setDisable(true));
        Platform.runLater(()->mapCharacterController.setMapComboBox(mapChoice));
        setStage(mapCharacterStage);

        return null;
    }

    @Override
    public Event actionChoice(boolean fireEnable) {
        Platform.runLater(()->gameBoardController.getFireButton().setDisable(!fireEnable));
        setStage(gameBoardStage);

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
        Platform.runLater(()->lobbyController.setLobby(available,startedLobbies,waitingLobbies));
        setStage(lobbyStage);
        return null;
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
    public Event playerWeaponUpdate(Character currCharacter, String[] weapons) {
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
}
