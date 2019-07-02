package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_server_event.LobbyChoiceEvent;
import it.polimi.ingsw.event.view_server_event.NewGameChoiceEvent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * It menage lobby choice scene
 */
public class LobbyChioceController extends AbstractController {

    private Event message;
    @FXML
    private AnchorPane gameChoicePanel;

    @FXML
    public Button newGameButton;

    @FXML
    private ComboBox<String> waitingLobbyComboBox;
    @FXML
    private ComboBox<String> startedLobbyComboBox;
        private Stage window;
    private String choice;

    /**
     * It sets available lobby of started game or waiting one
     * @param available
     * @param startedLobbies started game
     * @param waitingLobbies waiting game
     */
    public void setLobby(boolean[] available, ArrayList<String> startedLobbies, ArrayList<String> waitingLobbies) {

        //NEW GAME
        if (available[0]) {

        } else {
            newGameButton.setDisable(true);
        }

        //WAIT LOBBIES
        if (available[1]) {
            ObservableList<String> list = FXCollections.observableArrayList(waitingLobbies);
            waitingLobbyComboBox.setItems(list);
        } else {
            waitingLobbyComboBox.setDisable(true);
        }
        //STARTED LOBBY
        if (available[2]) {
            ObservableList<String> list = FXCollections.observableArrayList(startedLobbies);
            startedLobbyComboBox.setItems(list);
        } else {
            startedLobbyComboBox.setDisable(true);
        }
    }

    /**
     * It checks click on a new game button
     */
    public void newGameClick() {

       // sendChoice(new NewGameChoiceEvent(getGui().getUser()));
        getGui().setChoice("newgame");
        message = new NewGameChoiceEvent(getGui().getUser()) ;
        window.close();    }

    /**
     * It checks selection of a waiting lobby
     */
    @FXML
    public void waitLobbyClick() {
        message = new LobbyChoiceEvent(getGui().getUser(),waitingLobbyComboBox.getValue());
        Platform.runLater(()->window.close());    }

    /**
     * * It checks selection of a starting lobby
     */
    @FXML
    public void startedLobbyClick() {
        message = new LobbyChoiceEvent(getGui().getUser(),startedLobbyComboBox.getValue());
        Platform.runLater(()->window.close());
    }

    /**
     *setter
     * @param window new stage
     */
    public void setStage(Stage window){
        this.window = window;
    }

    /**
     *
     * @return
     */
    public Event ask(Scene scene){
        Platform.runLater(()->{
            window.setScene(scene);
            window.showAndWait();
        });
        return message;
}

    public AnchorPane getGameChoicePanel() {
        return gameChoicePanel;
    }
}

