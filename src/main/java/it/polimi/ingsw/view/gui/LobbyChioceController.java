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

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


public  class LobbyChioceController extends AbstractController{

     @FXML
        private AnchorPane gameChoicePanel;

        @FXML
        public Button newGameButton ;

        @FXML
        private ComboBox<String> waitingLobbyComboBox ;
        @FXML
        private ComboBox<String> startedLobbyComboBox ;

        private String choice;



    public String setLobby(boolean[] available, ArrayList<String> startedLobbies, ArrayList<String> waitingLobbies ){

        //NEW GAME
        if(available[0]){

        }else {
            newGameButton.setDisable(true);
        }

        //WAIT LOBBIES
        if (available[1]){
            ObservableList<String> list = FXCollections.observableArrayList(waitingLobbies);
           waitingLobbyComboBox.setItems(list);
        }
        else {
            waitingLobbyComboBox.setDisable(true);
        }
        //STARTED LOBBY
        if (available[2]){
            ObservableList<String> list = FXCollections.observableArrayList(startedLobbies);
            Platform.runLater(()->startedLobbyComboBox.setItems(list));

            }else {
                startedLobbyComboBox.setDisable(true);
            }

       return choice;

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


    public void newGameClick(){

        sendChoice(new NewGameChoiceEvent(getGui().getUser()));


    }


    @FXML
    public void waitLobbyClick() {

        choice=waitingLobbyComboBox.getValue();
        waitingLobbyComboBox.setDisable(true);
        sendChoice(new LobbyChoiceEvent(getGui().getUser(),choice));

    }


    @FXML
    public void startedLobbyClick() {


        choice=startedLobbyComboBox.getValue();
        startedLobbyComboBox.setDisable(true);
       sendChoice(new LobbyChoiceEvent(getGui().getUser(),choice));

    }



}

