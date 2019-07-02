package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.CharacterChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.utils.CustomLogger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MapCharacterController extends AbstractController {
    @FXML
    private Button enterButton;

    @FXML
    private ComboBox<Integer> mapComboBox;

    @FXML
    private ComboBox<String> characterComboBox;

    private int mapChoice;
    private Character characterChoice;

    @FXML
    void enterButtonPress() {
        //sendChoice(new CharacterChoiceEvent(getGui().getUser(),characterChoice));

    }

    public void setMapComboBox(ArrayList<Integer> mapChoice){
        ObservableList<Integer> mapList = FXCollections.observableArrayList(mapChoice);
        mapComboBox.setItems(mapList);

    }


    public void setCharacterComboBox(ArrayList<String> availableCharacters) {

        ObservableList<String> charactersList = FXCollections.observableArrayList(availableCharacters) ;
        characterComboBox.setItems(charactersList);
    }

    @FXML
    void mapComboBoxChoice(ActionEvent event) {
        mapChoice = mapComboBox.getValue();
        System.out.println("map choose");
        setMessage(new GameChoiceEvent(getGui().getUser(),mapChoice,0));
        getWindow().close();
    }

    public ComboBox<Integer> getMapComboBox() {
        return mapComboBox;
    }


    @FXML
    void characterComboBoxChoice(ActionEvent event) {
        characterChoice = Character.valueOf(characterComboBox.getValue());
        enterButton.setDisable(false);
        System.out.println(characterChoice.name());
    }



}
