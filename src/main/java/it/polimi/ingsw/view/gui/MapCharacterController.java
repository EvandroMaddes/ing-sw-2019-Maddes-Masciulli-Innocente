package it.polimi.ingsw.view.gui;

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

    @FXML
    void enterButtonPress(ActionEvent event) {
        int mapChoice = mapComboBox.getValue();
        sendChoice(new GameChoiceEvent(getGui().getUser(),mapChoice,0));
    }

    //todo Setta i character disponibili nell' combo box
    public void setMapComboBox(ArrayList<Integer> mapChoice){
        ObservableList<Integer> mapList = FXCollections.observableArrayList(mapChoice);
        mapComboBox.setItems(mapList);

    }


    public ComboBox<String> getCharacterComboBox() {
        return characterComboBox;
    }

    public void setCharacterComboBox(ArrayList<String> availableCharacters) {

        ObservableList<String> charactersList = FXCollections.observableArrayList(availableCharacters) ;
        characterComboBox.setItems(charactersList);
    }

    @FXML
    void mapComboBoxChoice(ActionEvent event) {

        System.out.println("map choose");
    }

    public ComboBox<Integer> getMapComboBox() {
        return mapComboBox;
    }

    @FXML
    void characterComboBoxChoice(ActionEvent event) {

        System.out.println("charcter choose");
    }


}
