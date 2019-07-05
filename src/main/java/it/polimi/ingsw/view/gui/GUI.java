package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.modelviewevent.EndGameUpdate;
import it.polimi.ingsw.event.serverviewevent.UsernameModificationEvent;
import it.polimi.ingsw.event.viewcontrollerevent.*;
import it.polimi.ingsw.model.gamecomponents.ammo.AmmoCube;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import it.polimi.ingsw.model.gamecomponents.cards.PowerUp;
import it.polimi.ingsw.model.gamecomponents.cards.powerups.TagbackGrenade;
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
import jdk.nashorn.internal.ir.CallNode;

import java.io.IOException;
import java.util.ArrayList;

// TODO: 02/07/2019 chiudere il primary stage prima di mostare la welcomeChoice

public class GUI extends RemoteView {
    private DecodeMessage decodeMessage = new DecodeMessage();

    private LobbyChioceController lobbyController;
    private GameBoardController gameBoardController;
    private MapController mapController;
    private CharacterChoiceController characterController;
    private PowerUpChoiceController powerUpController;
    private ActionChoiceController actionChoiceController;
    private WeaponChoiceController weaponChoiceController;
    private GenericPaymentController genericPaymentController;
    private EffectChoiceController effectChoiceController;
    private PositionChoiceController positionChoiceController;

    private Stage primaryStage;
    private Stage lobbyStage;
    private Stage gameBoardStage;
    private Stage mapStage;
    private Stage characterStage;
    private Stage powerUpStage;
    private Stage actionChoiceStage;
    private Stage weaponChoiceStage;
    private Stage genericPaymentStage;
    private Stage effectChoiceStage;
    private Stage positionChoiceStage;

    private Scene gameboardScene;
    private Scene lobbyScene;
    private Scene mapChoiceScene;
    private Scene characterScene;
    private Scene powerUpScene;
    private Scene actionChoiceScene;
    private Scene weaponChoiceScene;
    private Scene genericPaymentScene;
    private Scene effectChoiceScene;
    private Scene positionChoiceScene;

    private String[] clientChoices = new String[3];
    private Character characterChoose;


    /**
     * RemoteViewInterface method implementation: it catches user choices for username,connection(RMI/SOCKET) and IP address
     *
     * @return user choices
     */
    @Override
    public String[] gameInit() {
        return clientChoices;
    }

    /**
     * Constructor: it sets primary stage
     *
     * @param primaryStage first stage shows
     */
    GUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * It initializes all controller and stage of graphic interface
     */
    void initialize() {

        Parent lobby = null;
        Parent gameboard = null;
        Parent map = null;
        Parent character = null;
        Parent powerUp = null;
        Parent action = null;
        Parent weapon = null;
        Parent genericPayment = null;
        Parent effectChoice = null;
        Parent positionChoice = null;

        FXMLLoader lobbyFxml = new FXMLLoader(getClass().getResource("/fxml/lobbyScene.fxml"));
        FXMLLoader gameBoardFxml = new FXMLLoader(getClass().getResource("/fxml/gameboardScene.fxml"));
        FXMLLoader mapCharacterFxml = new FXMLLoader(getClass().getResource("/fxml/mapChoice.fxml"));
        FXMLLoader characterFxml = new FXMLLoader(getClass().getResource("/fxml/characterChoicePopUp.fxml"));
        FXMLLoader powerUpFxml = new FXMLLoader(getClass().getResource("/fxml/powerUpChoice.fxml"));
        FXMLLoader actionFxml = new FXMLLoader(getClass().getResource("/fxml/actionChoicePopUp.fxml"));
        FXMLLoader weaponFxml = new FXMLLoader(getClass().getResource("/fxml/weaponChoice.fxml"));
        FXMLLoader genericPaymentFxml = new FXMLLoader(getClass().getResource("/fxml/genericPaymentPopUp.fxml"));
        FXMLLoader effectChoiceFxml = new FXMLLoader(getClass().getResource("/fxml/effectChoice.fxml"));
        FXMLLoader positionChoiceFxml = new FXMLLoader(getClass().getResource("/fxml/positionChoice.fxml"));

        try {
            lobby = lobbyFxml.load();
            gameboard = gameBoardFxml.load();
            map = mapCharacterFxml.load();
            character = characterFxml.load();
            powerUp = powerUpFxml.load();
            action = actionFxml.load();
            weapon = weaponFxml.load();
            genericPayment = genericPaymentFxml.load();
            effectChoice = effectChoiceFxml.load();
            positionChoice = positionChoiceFxml.load();

        } catch (IOException e) {
            CustomLogger.logException(e);
        }
        lobbyController = lobbyFxml.getController();
        gameBoardController = gameBoardFxml.getController();
        mapController = mapCharacterFxml.getController();
        characterController = characterFxml.getController();
        powerUpController = powerUpFxml.getController();
        actionChoiceController = actionFxml.getController();
        weaponChoiceController = weaponFxml.getController();
        genericPaymentController = genericPaymentFxml.getController();
        effectChoiceController = effectChoiceFxml.getController();
        positionChoiceController = positionChoiceFxml.getController();

        lobbyStage = new Stage();
        mapStage = new Stage();
        gameBoardStage = new Stage();
        characterStage = new Stage();
        powerUpStage = new Stage();
        actionChoiceStage = new Stage();
        weaponChoiceStage = new Stage();
        genericPaymentStage = new Stage();
        effectChoiceStage = new Stage();
        positionChoiceStage = new Stage();

        lobbyStage.setTitle("Lobby-ADRENALINE");
        mapStage.setTitle("MapChoice-ADRENALINE");
        gameBoardStage.setTitle("GameBoard-ADRENALINE");
        actionChoiceStage.setTitle("ActionChoice-ADRENALINE");
        weaponChoiceStage.setTitle("WeaponChoice-ADRENALINE");
        characterStage.setTitle("CharacterChoice-ADRENALINE");
        powerUpStage.setTitle("PowerUpChoice-ADRENALINE");
        genericPaymentStage.setTitle("PaymentChoice-ADRENALINE");
        effectChoiceStage.setTitle("EffectChoice-ADRENALINE");
        positionChoiceStage.setTitle("PositionChoice-ADRENALINE");

        gameBoardController.setGui(this);
        lobbyController.setGui(this);
        mapController.setGui(this);
        characterController.setGui(this);
        powerUpController.setGui(this);
        actionChoiceController.setGui(this);
        weaponChoiceController.setGui(this);
        genericPaymentController.setGui(this);
        effectChoiceController.setGui(this);
        positionChoiceController.setGui(this);

        lobbyScene = new Scene(lobby, 880, 620);
        gameboardScene = new Scene(gameboard, 800, 560);
        mapChoiceScene = new Scene(map, 400, 120);
        characterScene = new Scene(character, 500, 369);
        powerUpScene = new Scene(powerUp, 500, 369);
        actionChoiceScene = new Scene(action, 500, 250);
        weaponChoiceScene = new Scene(weapon, 500, 400);
        genericPaymentScene = new Scene(genericPayment, 505, 456);
        effectChoiceScene = new Scene(effectChoice, 500, 450);
        positionChoiceScene = new Scene(positionChoice,625,500);

        lobbyStage.setScene(lobbyScene);
        gameBoardStage.setScene(gameboardScene);
        characterStage.setScene(characterScene);
        powerUpStage.setScene(powerUpScene);
        actionChoiceStage.setScene(actionChoiceScene);
        weaponChoiceStage.setScene(weaponChoiceScene);
        genericPaymentStage.setScene(genericPaymentScene);
        effectChoiceStage.setScene(effectChoiceScene);
        positionChoiceStage.setScene(positionChoiceScene);



        gameBoardController.init();
        weaponChoiceController.init();
        positionChoiceController.init();
        genericPaymentController.init();
        metodoPROVA();
    }

    // TODO: 03/07/2019 da eliminare!!
    public void metodoPROVA() {
         Platform.runLater(()->
         {
             powerUpController.setInfoLabel("SELECT YOUR POWER UP TO RESPAWN");
             PowerUp power1 = new TagbackGrenade(CubeColour.Red);
             PowerUp power2 = new TagbackGrenade(CubeColour.Red);
             powerUpController.setController(new String[]{power1.getName(),power2.getName()}, new CubeColour[]{power1.getColour(),power2.getColour()},1);
             powerUpStage.show();

              });

    }

    /**
     * setter:
     *
     * @param choices user choices for username, connection and ip address
     */
    public void setClientChoices(String[] choices) {
        clientChoices = choices;

    }

    /**
     * RemoteViewInterface method implementation:User chooses one destination square for action move
     *
     * @param possibleSquareX row of possible destination square
     * @param possibleSquareY column of possible destination square
     * @return
     */
    @Override
    public Event shotMoveChoiceEvent(int[] possibleSquareX, int[] possibleSquareY) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                positionChoiceController.setController(possibleSquareX,possibleSquareY);
                positionChoiceController.setWindow(positionChoiceStage);
                MoveChoiceEvent message = (MoveChoiceEvent)positionChoiceController.ask(positionChoiceScene);
                return new ShotMoveChoiceEvent(getUser(),message.getPositionX(),message.getPositionY());
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation:it shows game board updated: map, player boards and game track
     */
    @Override
    public void printScreen() {
        Platform.runLater(() -> {
            primaryStage.setResizable(true);
            primaryStage.setScene(gameboardScene);
            primaryStage.setFullScreen(true);
            primaryStage.show();
        });

    }

    /**
     * RemoteViewInterface method implementation:setter
     *
     * @param mapNumber is the map chosen
     */
    @Override
    public void setGame(int mapNumber) {
        Image[] maps = decodeMessage.mapImage(mapNumber);
        gameBoardController.setMap(maps[0], maps[1]);

    }

    /**
     * RemoteViewInterface method implementation: It checks if game is ready to start(display ready)
     *
     * @return game set or not
     */
    @Override
    public boolean isGameSet() {
        // TODO: 03/07/2019 fra chiedi a evandro
        return gameBoardController.getLeftMap().getImage()!= null;
    }

    /**
     * RemoteViewInterface method implementation:
     *
     * @param endGameUpdate Update that notify the finish of the game
     * @return message notify the success of updating
     */
    @Override
    public Event winnerUpdate(EndGameUpdate endGameUpdate) {
        gameBoardController.setInfo(endGameUpdate.getEndGameMessage());
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface method implementation: User chooses to use one of his power up during an action
     *
     * @param powerUpNames   available power up
     * @param powerUpColours colour of power up
     * @return event that contains player's choice
     */
    @Override
    public Event whileActionPowerUpRequestEvent(String[] powerUpNames, CubeColour[] powerUpColours) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                try {
                    powerUpController.setInfoLabel("Select one power up to use:");
                    powerUpController.setController(powerUpNames, powerUpColours, 1);
                    powerUpController.setWindow(powerUpStage);
                } catch (Exception e) {
                }
                PowerUpChoiceEvent event = (PowerUpChoiceEvent) powerUpController.ask(powerUpScene);

                return new WhileActionPowerUpChoiceEvent(getUser(), powerUpController.isWantToUse(), event.getCard(), event.getPowerUpColour());
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: It updates number of skull on the game track
     *
     * @param killerCharacter player who takes the skull
     * @param skullNumber     number of skull left:
     *                        0-one skull;
     *                        1-one damage;
     *                        2-two damage;
     * @return message notify the success of updating
     */
    @Override
    public Event gameTrackSkullUpdate(Character[] killerCharacter, int[] skullNumber) {
        gameBoardController.gameTrackClean();
        for (int i = 0; skullNumber[i] != 0; i++) {
            gameBoardController.removeSkull(skullNumber[i], decodeMessage.playerTokenImage(killerCharacter[i]));
        }
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface method implementation: it shows how to pay when player can choose one item from ammo or powerUp
     *
     * @param usableAmmo     available ammo
     * @param powerUpsType   list of power up's name available to use
     * @param powerUpsColour list of power up's colour available to use
     * @return event that contains player's choice
     */
    @Override
    public Event genericPaymentChoice(boolean[] usableAmmo, String[] powerUpsType, CubeColour[] powerUpsColour) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                try {
                    genericPaymentController.genericPayment(usableAmmo,powerUpsType,powerUpsColour);
                    genericPaymentController.setWindow(genericPaymentStage);
                } catch (Exception e) {
                }
             return genericPaymentController.ask(genericPaymentScene);
    }
        };
        return userChoice(query);
    }
    /**
     * RemoteViewInterface method implementation: It shows the possibility to use one power up at the end of turn(no your turn)
     *
     * @param powerUpNames      list of  power up's name available to use
     * @param powerUpColours    list of power up's colour available to use
     * @param maxUsablePowerUps max powerUp that you can use
     * @return event that contains player's choice
     */
    @Override
    public Event endRoundPowerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours, int maxUsablePowerUps) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                try {
                    powerUpController.setTrueEndOfRoundPowerUp();
                    powerUpController.setInfoLabel("Select max " + maxUsablePowerUps + " powerUp to use:");
                    powerUpController.setController(powerUpNames, powerUpColours, maxUsablePowerUps);
                    powerUpController.setWindow(powerUpStage);
                } catch (Exception e) {
                }
                return powerUpController.ask(powerUpScene);

            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation:  User selects how to reload his weapon (power Up, ammo or both)
     *
     * @param powerUpNames          list of power up's name available to use
     * @param powerUpColours        list of power up's colour available to use
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return event that contains player's choice
     */
    @Override
    public Event weaponReloadPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                try {String toShow = "SELECT HOW TO PAY RELOAD COST";
                        toShow =toShow+ "Min " + powerUpRequestString(minimumPowerUpRequest);
                        toShow = toShow+"Max "+ powerUpRequestString(maximumPowerUpRequest);
                        int min = minimumPowerUpRequest[0]+minimumPowerUpRequest[1]+minimumPowerUpRequest[2];
                    int max = maximumPowerUpRequest[0]+maximumPowerUpRequest[1]+maximumPowerUpRequest[2];
                        genericPaymentController.setUpController(toShow,powerUpNames,powerUpColours,min,max);
                    genericPaymentController.setWindow(genericPaymentStage);
                } catch (Exception e) {
                }
                return genericPaymentController.ask(genericPaymentScene);
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: It returns how a player pay the weapon GRAB cost
     *
     * @param powerUpNames          list of power up's name available to use
     * @param powerUpColours        list of power up's colour available to use
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return event that contains player's choice
     */
    @Override
    public Event weaponGrabPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                try {String toShow = "SELECT HOW TO PAY GRAB COST";
                    toShow =toShow+ "Min " + powerUpRequestString(minimumPowerUpRequest);
                    toShow = toShow+"Max "+ powerUpRequestString(maximumPowerUpRequest);
                    int min = minimumPowerUpRequest[0]+minimumPowerUpRequest[1]+minimumPowerUpRequest[2];
                    int max = maximumPowerUpRequest[0]+maximumPowerUpRequest[1]+maximumPowerUpRequest[2];
                    genericPaymentController.setUpController(toShow,powerUpNames,powerUpColours,min,max);
                    genericPaymentController.setWindow(genericPaymentStage);
                } catch (Exception e) {
                }
                WeaponReloadPaymentChoiceEvent message =((WeaponReloadPaymentChoiceEvent)genericPaymentController.ask(genericPaymentScene));
                return new WeaponGrabPaymentChoiceEvent(getUser(),message.getPowerUpType(),message.getPowerUpColour());
            }
        };
        return userChoice(query);
    }
    /**
     * RemoteViewInterface method implementation: it return how a player pay the cost of effect selected(weapon effect)
     *
     * @param powerUpNames          list of power up's name available to use
     * @param powerUpColours        list of power up's colour available to use
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
     * @param maximumPowerUpRequest max number of power up you can use
     * @return event that contains player's choice
     */
    @Override
    public Event weaponEffectPaymentChoice(String[] powerUpNames, CubeColour[] powerUpColours, int[] minimumPowerUpRequest, int[] maximumPowerUpRequest) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                try {String toShow = "SELECT HOW TO PAY EFFECT COST";
                    toShow =toShow+ "Min " + powerUpRequestString(minimumPowerUpRequest);
                    toShow = toShow+"Max "+ powerUpRequestString(maximumPowerUpRequest);
                    int min = minimumPowerUpRequest[0]+minimumPowerUpRequest[1]+minimumPowerUpRequest[2];
                    int max = maximumPowerUpRequest[0]+maximumPowerUpRequest[1]+maximumPowerUpRequest[2];
                    genericPaymentController.setUpController(toShow,powerUpNames,powerUpColours,min,max);
                    genericPaymentController.setWindow(genericPaymentStage);
                } catch (Exception e) {
                }
                WeaponReloadPaymentChoiceEvent message =((WeaponReloadPaymentChoiceEvent)genericPaymentController.ask(genericPaymentScene));
                return new WeaponEffectPaymentChoiceEvent(getUser(),message.getPowerUpType(),message.getPowerUpColour());
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: it selects the destination square of target of newton powerUp
     *
     * @param possibleSquareX column of possible square
     * @param possibleSquareY row of possible square
     * @return event that contains player's choice
     */
    @Override
    public Event newtonTeleporterTargetSquareChoice(int[] possibleSquareX, int[] possibleSquareY) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                positionChoiceController.setController(possibleSquareX,possibleSquareY);
                positionChoiceController.setWindow(positionChoiceStage);
                MoveChoiceEvent message = (MoveChoiceEvent)positionChoiceController.ask(positionChoiceScene);
                return new PowerUpSquareTargetChoiceEvent(getUser(),message.getPositionX(),message.getPositionY());
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: it shows the UpdateNotification message, requesting a choice if necessary;
     *
     * @param usernameEvent notify modification of a user name
     * @return the answer that will be sent to the server.
     */
    @Override
    public Event printUserNotification(UsernameModificationEvent usernameEvent) {
        // TODO: 04/07/2019 fraa dimmi cosa fare
        return null;
    }

    /**
     * RemoteViewInterface method implementation: it sets character on a new position on map
     *
     * @param currCharacter modified player position
     * @param x             row: if sets at 404, player must be removed
     * @param y             column: if sets at 404, players must be removed
     * @return message notify the success of updating
     */
    // TODO: 03/07/2019 RIMOZIONE CON 404
    @Override
    public Event positionUpdate(Character currCharacter, int x, int y) {
        gameBoardController.setPosition(x, y, decodeMessage.characterImage(currCharacter),currCharacter);
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface method implementation: user chooses target of his power up
     *
     * @param availableTargets character available to hit
     * @param maxTarget        max number of target
     * @return event that contains player's choice
     */
    @Override
    public Event newtonTargetChoice(ArrayList<Character> availableTargets, int maxTarget) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                characterController.setInfoText("Choose your target:");
                characterController.setCharacterChoice(availableTargets);
                characterController.setWindow(characterStage);
                NewtonPlayerTargetChoiceEvent message = (NewtonPlayerTargetChoiceEvent) characterController.ask(characterScene);
                return new NewtonPlayerTargetChoiceEvent(getUser(), message.getChosenTarget());
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user choice for character
     *
     * @param availableCharacters character available
     * @return event that contains player's choice
     */
    @Override
    public Event characterChoice(ArrayList<Character> availableCharacters) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                characterController.setCharacterChoice(availableCharacters);
                characterController.setWindow(characterStage);
                Event message = characterController.ask(characterScene);
                characterChoose = ((CharacterChoiceEvent) message).getChosenCharacter();
                gameBoardController.setPrincipalPlayerboard(decodeMessage.playerBoardImage(characterChoose), characterChoose);
                return message;
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user select target to hit(characters)
     *
     * @param availableTargets characters that can be hit
     * @param numTarget        number of target
     * @return event that contains player's choice
     */
    @Override
    public Event weaponTargetChoice(ArrayList<Character> availableTargets, int numTarget) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                characterController.trueWeaponTarget();
                characterController.setInfoText("You can choose max " + numTarget + " target:");
                characterController.setCharacterChoice(availableTargets);
                characterController.setWindow(characterStage);
                return characterController.ask(characterScene);

            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user choose square target for his selected effect
     *
     * @param possibleSquareX column of available square
     * @param possibleSquareY row of available square
     * @return event that contains player's choice
     */
    @Override
    public Event weaponEffectSquareChoice(int[] possibleSquareX, int[] possibleSquareY) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                positionChoiceController.setController(possibleSquareX,possibleSquareY);
                positionChoiceController.setWindow(positionChoiceStage);
                MoveChoiceEvent message = (MoveChoiceEvent)positionChoiceController.ask(positionChoiceScene);
                return new WeaponSquareTargetChoiceEvent(getUser(),message.getPositionX(),message.getPositionY());
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user select one weapon to grab
     *
     * @param availableWeapon weapon on spawn square
     * @return event that contains player's choice
     */
    @Override
    public Event weaponGrabChoice(ArrayList<String> availableWeapon) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                weaponChoiceController.setText("Select weapon to grab:");
                weaponChoiceController.setController(availableWeapon);
                weaponChoiceController.setWindow(weaponChoiceStage);
                WeaponChoiceEvent message = (WeaponChoiceEvent) weaponChoiceController.ask(weaponChoiceScene);
                return new WeaponGrabChoiceEvent(getUser(), message.getCard());
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: it updates map by deleting an ammo tile
     *
     * @param x row
     * @param y column
     * @return message notify the success of updating
     */
    @Override
    public Event removeAmmoTileUpdate(int x, int y) {
        gameBoardController.removeAmmoTileOnMap(x, y);
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface method implementation: it updates map by adding an ammo tile
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
        gameBoardController.addAmmoTileOnMap(x, y, decodeMessage.ammoTileImage(fistColour, secondColour, thirdColour));
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface method implementation: user chooses one weapon to discard
     *
     * @param yourWeapon player's weapon
     * @return event that contains player's choice
     */
    @Override
    public Event weaponDiscardChoice(ArrayList<String> yourWeapon) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                weaponChoiceController.setText("Select weapon to discard:");
                weaponChoiceController.setController(yourWeapon);
                weaponChoiceController.setWindow(weaponChoiceStage);
                WeaponChoiceEvent message = (WeaponChoiceEvent) weaponChoiceController.ask(weaponChoiceScene);
                return new WeaponDiscardChoiceEvent(getUser(), message.getCard());
            }
        };
        return userChoice(query);
    }


    /**
     * RemoteViewInterface method implementation: user choice for map
     *
     * @return event that contains player's choice
     */
    @Override
    public Event gameChoice() {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                try {
                    mapController.setWindow(mapStage);
                    ArrayList<Integer> mapChoice = new ArrayList<>();
                    mapChoice.add(0);
                    mapChoice.add(1);
                    mapChoice.add(2);
                    mapChoice.add(3);
                    mapController.setMapComboBox(mapChoice);
                } catch (Exception e) {
                }
                Event event = mapController.ask(mapChoiceScene);
                Image[] mapChoice = decodeMessage.mapImage(((GameChoiceEvent) event).getMap());
                gameBoardController.setMap(mapChoice[0], mapChoice[1]);
                positionChoiceController.setMapImage(((GameChoiceEvent) event).getMap());
                printScreen();
                return event;
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user chooses an action at the beginning of his own turn
     *
     * @param fireEnable true if player can use action fire
     * @return event that contains player's choice
     */
    @Override
    public Event actionChoice(boolean fireEnable) {
        System.out.println("action choice");


        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                actionChoiceController.setWindow(actionChoiceStage);
                actionChoiceController.setController(fireEnable);
                return actionChoiceController.ask(actionChoiceScene);
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user chooses weapon to reload
     *
     * @param reloadableWeapons list of weapon that can be reloaded
     * @return event that contains player's choice
     */
    @Override
    public Event reloadChoice(ArrayList<String> reloadableWeapons) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                weaponChoiceController.setText("Select weapon to reload:");
                weaponChoiceController.setController(reloadableWeapons);
                weaponChoiceController.setWindow(weaponChoiceStage);
                WeaponChoiceEvent message = (WeaponChoiceEvent) weaponChoiceController.ask(weaponChoiceScene);
                return new WeaponReloadChoiceEvent(getUser(), message.getCard());
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user chooses which powerUp discard
     *
     * @param powerUpNames   list of powerUp
     * @param powerUpColours color of powerUp
     * @return event that contains player's choice
     */
    @Override
    public Event respawnChoice(String[] powerUpNames, CubeColour[] powerUpColours) {

        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                try {
                    powerUpController.setInfoLabel("Select your power up to respawn:");
                    powerUpController.setSkipChoiceButtonDisable(true);
                    powerUpController.setWindow(powerUpStage);
                    powerUpController.setController(powerUpNames, powerUpColours, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Ritorno messaggio di spawn");
                PowerUpChoiceEvent event = (PowerUpChoiceEvent)powerUpController.ask(powerUpScene);
                System.out.println("Ritorno messaggio di spawn");
                Event message = new SpawnChoiceEvent(getUser(), event.getCard(), event.getPowerUpColour());
                System.out.println("Ritorno messaggio di spawn");
                return message;
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user chooses where move his character
     *
     * @param possibleSquareX column
     * @param possibleSquareY row
     * @return event that contains player's choice
     */
    @Override
    public Event positionMoveChoice(int[] possibleSquareX, int[] possibleSquareY) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                positionChoiceController.setController(possibleSquareX,possibleSquareY);
                positionChoiceController.setWindow(positionChoiceStage);
                return positionChoiceController.ask(positionChoiceScene);
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user chooses where move his character and grab item on new position
     *
     * @param possibleSquareX column
     * @param possibleSquareY row
     * @return event that contains player's choice
     */
    @Override
    public Event positionGrabChoice(int[] possibleSquareX, int[] possibleSquareY) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                positionChoiceController.setController(possibleSquareX,possibleSquareY);
                positionChoiceController.setWindow(positionChoiceStage);
                MoveChoiceEvent message = (MoveChoiceEvent)positionChoiceController.ask(positionChoiceScene);
                return new GrabChoiceEvent(getUser(),message.getPositionX(),message.getPositionY());
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user chooses weapon to fire
     *
     * @param availableWeapons weapon loaded and ready to fire
     * @return event that contains player's choice
     */
    @Override
    public Event weaponChoice(ArrayList<String> availableWeapons) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                weaponChoiceController.setText("Select weapon to fire:");
                weaponChoiceController.setController(availableWeapons);
                weaponChoiceController.setWindow(weaponChoiceStage);
                return weaponChoiceController.ask(weaponChoiceScene);
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user chooses at least one effect for his own weapon
     *
     * @param availableWeaponEffects effect available for selected weapon
     * @return event that contains player's choice
     */
    @Override
    public Event weaponEffectChoice(boolean[] availableWeaponEffects) {

        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                effectChoiceController.setController(availableWeaponEffects);
                effectChoiceController.setWindow(effectChoiceStage);
                return effectChoiceController.ask(effectChoiceScene);
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user chooses a target character
     *
     * @param possibleTargets character available to hit
     * @return event that contains player's choice
     */
    @Override
    public Event targetingScopeTargetChoice(ArrayList<Character> possibleTargets) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                characterController.setInfoText("Choose your target:");
                characterController.setCharacterChoice(possibleTargets);
                characterController.setWindow(characterStage);
                CharacterChoiceEvent message = (CharacterChoiceEvent) characterController.ask(characterScene);
                return new TargetingScopeTargetChoiceEvent(getUser(), message.getChosenCharacter());
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user chooses one powerUp to use
     *
     * @param powerUpNames   name of powerUp available
     * @param powerUpColours color of powerUp available
     * @return event that contains player's choice
     */
    @Override
    public Event powerUpChoice(String[] powerUpNames, CubeColour[] powerUpColours) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                try {
                    powerUpController.setSkipChoiceButtonDisable(true);
                    powerUpController.setInfoLabel("Select one power up:");
                    powerUpController.setController(powerUpNames, powerUpColours, 1);
                    powerUpController.setWindow(powerUpStage);
                } catch (Exception e) {
                }
                Event event = powerUpController.ask(powerUpScene);
                return event;
            }
        };
        return userChoice(query);
    }

    /**
     * RemoteViewInterface method implementation: user chooses which game would like to join
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
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                try {
                    lobbyController.setWindow(lobbyStage);
                    lobbyController.setLobby(available, startedLobbies, waitingLobbies);
                } catch (Exception e) {
                }
                return lobbyController.ask(lobbyScene);
            }
        };
        return userChoice(query);
    }


    /**
     * RemoteViewInterface method implementation: every time one player joins the game it's notified to other player
     *
     * @param newPlayer       new player username
     * @param characterChoice new player character choose
     * @return message notify the success of updating
     */
    @Override
    public Event newPlayerJoinedUpdate(String newPlayer, Character characterChoice) {
        if (characterChoose != characterChoice) {
            gameBoardController.setNewPlayer(decodeMessage.playerBoardImage(characterChoice), characterChoice);
        }
        gameBoardController.setInfo("User " + newPlayer + " join with " + characterChoice.name());
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface method implementation: modification of player Board(damage, mark, skull)
     *
     * @param character   modified player board
     * @param skullNumber number of skull
     * @param marks       number of marks and who did it
     * @param damages     number of damage and who did it
     * @return message notify the success of updating
     */
    @Override
    public Event playerBoardUpdate(Character character, int skullNumber, Character[] marks, Character[] damages) {
        Image[] damageToAdd = new Image[damages.length];
        Image[] marksToAdd = new Image[marks.length];

        for (int i = 0; i < damages.length; i++) {
            damageToAdd[i] = decodeMessage.playerTokenImage(damages[i]);
        }
        for (int i = 0; i < marks.length; i++) {
            marksToAdd[i] = decodeMessage.playerTokenImage(marks[i]);
        }
        gameBoardController.setDamage(character, damageToAdd);
        gameBoardController.setMark(character, marksToAdd);
        if (characterChoose == character) {
            gameBoardController.addPlayerSkull(skullNumber);
        }
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface method implementation: it shows ammo available
     *
     * @param currCharacter modified player board
     * @param ammo          ammo of player
     * @return message notify the success of updating
     */
    @Override
    public Event playerAmmoUpdate(Character currCharacter, ArrayList<AmmoCube> ammo) {

        Image[] ammoToAdd = new Image[ammo.size()];
        for (int i = 0; i < ammo.size(); i++) {
            ammoToAdd[i] = decodeMessage.ammoCubeImage(ammo.get(i));
        }
        gameBoardController.setAmmo(currCharacter, ammoToAdd);
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface method implementation: it shows weapon available
     *
     * @param currCharacter modified player board
     * @param weapons       weapons of player
     * @param load          if weapon is load
     * @return message notify the success of updating
     */
    @Override
    public Event playerWeaponUpdate(Character currCharacter, String[] weapons, boolean[] load) {
        if (characterChoose == currCharacter) {
            if (weapons.length < 4) {
                Image[] toAdd = new Image[weapons.length];
                for (int i = 0; i < weapons.length; i++) {
                    if (load[i]) {
                        toAdd[i] = decodeMessage.weaponImage(weapons[i]);
                    } else {
                        toAdd[i] = decodeMessage.weaponImage("unload");
                    }
                }
                gameBoardController.setPlayerWeapon(toAdd);
            }
        }
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface method implementation: it replaces weapons on spawn square
     *
     * @param x      coordinate x (row)
     * @param y      coordinate y (column)
     * @param weapon weapon to add
     * @return message notify the success of updating
     */
    @Override
    public Event weaponReplaceUpdate(int x, int y, String[] weapon) {
        //xy:01 red
        //   20 blue
        //   32 yellow
        Image[] toAdd = new Image[weapon.length];
        for (int i = 0; i < weapon.length; i++) {
            toAdd[i] = decodeMessage.weaponImage(weapon[i]);

        }
        gameBoardController.setSpawnWeapon(x, toAdd);
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface method implementation: it shows power up available
     *
     * @param currCharacter modified player board
     * @param powerUp       power up of player
     * @param color         colour of power up
     * @return message notify the success of updating
     */
    @Override
    public Event playerPowerUpUpdate(Character currCharacter, String[] powerUp, CubeColour[] color) {
        Image[] toAdd = new Image[powerUp.length];
        if (characterChoose == currCharacter) {
            System.out.println("setto power up");

            for (int i = 0; i < powerUp.length; i++) {
                toAdd[i] = decodeMessage.powerUpImage(powerUp[i], color[i]);
            }
        }
        gameBoardController.setPlayerPowerUp(toAdd);
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * RemoteViewInterface method implementation: it shows reconnection or disconnection (depending on disconnected value) update notifying the user and character disconnected
     *
     * @param user         is the involved user
     * @param character    is the involved user's character
     * @param disconnected is true if the user was disconnected, false if was reconnected
     * @return message notify the success of updating
     */
    @Override
    public Event playerReconnectionNotify(String user, Character character, boolean disconnected) {
        String toShow = user + " is " ;
       if (disconnected){
           toShow = toShow +" disconnected ";
       }else {
           toShow = toShow + " reconnected ";
       }
       toShow = toShow + " with " +character.name();
       gameBoardController.setInfo(toShow);
        return new UpdateChoiceEvent(BROADCAST_STRING);
    }

    /**
     * It "takes" choice from controller
     *
     * @param query
     * @return event that contains player's choice
     */
    // TODO: 03/07/2019 fra completa javadoc grazie
    private Event userChoice(Task<Event> query) {
        Thread th = new Thread(query);
        th.start();
        try {
            Event event = query.get();
            System.out.println("Ritorno messaggio da user choice");

            return event;
        } catch (Exception interrupted) {
            CustomLogger.logException(interrupted);
            return null;
        }
    }

    /**
     * It create a string with information about power up payment request
     * @param powerUpRequest
     * @return
     */
    private String powerUpRequestString(int[] powerUpRequest) {
        String toShow= "";
        if (powerUpRequest[0] != 0) {
            toShow = powerUpRequest[0] + " RED -";
        }
        if (powerUpRequest[1] != 0) {
            toShow = toShow+powerUpRequest[1] + " YELLOW -";

        }
        if (powerUpRequest[2] != 0) {
            toShow = toShow+ powerUpRequest[2] + " BLUE -";
        }
        return toShow;
    }
}
