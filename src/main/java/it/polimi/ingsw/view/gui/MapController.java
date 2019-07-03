package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.event.viewcontrollerevent.GameChoiceEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

/**
 * It controls scene in which user choose map if the game
 *
 * @author Francesco Masciulli
 * @author Evandro Maddes
 */
public class MapController extends AbstractController {
    /**
     * it contains all available map choices
     */
    @FXML
    private ComboBox<Integer> mapComboBox;


    /**
     * it set available map to choose
     *
     * @param mapChoice set available map number
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
        int mapChoice;
        mapChoice = mapComboBox.getValue();
        setMessage(new GameChoiceEvent(getGui().getUser(), mapChoice));
        getWindow().close();
    }


}
