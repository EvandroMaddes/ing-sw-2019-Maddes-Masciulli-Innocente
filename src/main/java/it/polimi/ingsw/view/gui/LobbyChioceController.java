package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.event.viewserverevent.LobbyChoiceEvent;
import it.polimi.ingsw.event.viewserverevent.NewGameChoiceEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

/**
 * It menage lobby choice scene
 *
 * @author Evandro Maddes
 */
public class LobbyChioceController extends AbstractController {

    @FXML
    public Button newGameButton;

    @FXML
    private ComboBox<String> waitingLobbyComboBox;

    @FXML
    private ComboBox<String> startedLobbyComboBox;


    /**
     * It sets available lobby of started game or waiting one
     *
     * @param available      It use to set button on screen
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
            waitingLobbyComboBox.setOpacity(1.0);

        } else {
            waitingLobbyComboBox.setDisable(true);
        }
        //STARTED LOBBY
        if (available[2]) {
            ObservableList<String> list = FXCollections.observableArrayList(startedLobbies);
            startedLobbyComboBox.setItems(list);
            startedLobbyComboBox.setOpacity(1.0);

        } else {
            startedLobbyComboBox.setDisable(true);
        }
    }

    /**
     * It checks click on a new game button
     */
    public void newGameClick() {

        setMessage(new NewGameChoiceEvent(getGui().getUser()));
        getWindow().close();
    }

    /**
     * It checks selection of a waiting lobby
     */
    @FXML
    public void waitLobbyClick() {
        setMessage(new LobbyChoiceEvent(getGui().getUser(), waitingLobbyComboBox.getValue()));
        getWindow().close();
    }

    /**
     * * It checks selection of a starting lobby
     */
    @FXML
    public void startedLobbyClick() {
        setMessage(new LobbyChoiceEvent(getGui().getUser(), startedLobbyComboBox.getValue()));
        getWindow().close();
    }

}

