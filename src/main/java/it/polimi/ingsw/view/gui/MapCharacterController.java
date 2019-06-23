package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.view_controller_event.CharacterChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.GameChoiceEvent;
import it.polimi.ingsw.model.player.Character;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

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
    void enterButtonPress(ActionEvent event) {
        sendChoice(new GameChoiceEvent(getGui().getUser(),mapChoice,0));
        sendChoice(new CharacterChoiceEvent(getGui().getUser(),characterChoice));
    }

    public void setMapComboBox(ArrayList<Integer> mapChoice){
        ObservableList<Integer> mapList = FXCollections.observableArrayList(mapChoice);
        mapComboBox.setItems(mapList);

    }

    public Button getEnterButton() {
        return enterButton;
    }

    public void setCharacterComboBox(ArrayList<String> availableCharacters) {

        ObservableList<String> charactersList = FXCollections.observableArrayList(availableCharacters) ;
        characterComboBox.setItems(charactersList);
    }

    @FXML
    void mapComboBoxChoice(ActionEvent event) {
        mapChoice = mapComboBox.getValue();
        System.out.println("map choose");
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
