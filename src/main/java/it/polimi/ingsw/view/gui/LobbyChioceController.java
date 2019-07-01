package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.event.view_server_event.LobbyChoiceEvent;
import it.polimi.ingsw.event.view_server_event.NewGameChoiceEvent;
import it.polimi.ingsw.model.player.Character;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


public class LobbyChioceController extends AbstractController {

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

/*
        public  String listenNewGame() {

             newGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                System.out.println("new game pressed");
                event.consume();
            });

            waitingLobbyComboBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                System.out.println("wait pressed");
                event.consume();
            });

            startedLobbyComboBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                System.out.println("staretd pressed");
                event.consume();
            });
           return choice;
        }
*/


    public void newGameClick() {

       // sendChoice(new NewGameChoiceEvent(getGui().getUser()));
        getGui().setChoice("newgame");
        window.close();
    }


    @FXML
    public void waitLobbyClick() {
        getGui().setChoice(waitingLobbyComboBox.getValue());
        waitingLobbyComboBox.setDisable(true);
        window.close();
    }


    @FXML
    public void startedLobbyClick() {


        getGui().setChoice(startedLobbyComboBox.getValue());
        startedLobbyComboBox.setDisable(true);
        window.close();


    }
    public void setStage(Stage window){
        this.window = window;
    }

public Event ask(){
        Platform.runLater(()->window.showAndWait());
        return new NewGameChoiceEvent(getGui().getUser());
}

    public AnchorPane getGameChoicePanel() {
        return gameChoicePanel;
    }
}

