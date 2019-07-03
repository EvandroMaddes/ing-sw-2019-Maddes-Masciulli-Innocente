package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.event.viewcontrollerevent.GameChoiceEvent;
import it.polimi.ingsw.model.player.Character;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

public class MapCharacterController extends AbstractController {

    @FXML
    private ComboBox<Integer> mapComboBox;

    private int mapChoice;
    private Character characterChoice;

    /**
     * it set available map to choose
     *
     * @param mapChoice
     */
    public void setMapComboBox(ArrayList<Integer> mapChoice) {
        ObservableList<Integer> mapList = FXCollections.observableArrayList(mapChoice);
        mapComboBox.setItems(mapList);

    }

    /**
     * it sets map choice and creates answer message
     *
     * @param event user click on map choice
     */
    @FXML
    void mapComboBoxChoice(ActionEvent event) {
        mapChoice = mapComboBox.getValue();
        System.out.println("map choose");
        setMessage(new GameChoiceEvent(getGui().getUser(), mapChoice));
        getWindow().close();
    }


}
