package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.viewcontrollerevent.ReconnectedEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

/**
 * It controls scene of reconnection
 *
 * @author Evandro Maddes
 */
public class ReconnectionController extends AbstractController {
    @FXML
    private ComboBox<String> disconnectedClient;

    /**
     * It set available username to reconnection
     *
     * @param available available username
     */
    void setController(ArrayList<String> available) {
        ObservableList<String> list = FXCollections.observableArrayList(available);
        disconnectedClient.setItems(list);

    }

    @FXML
    void disconnetionSelection(ActionEvent event) {
        String selected = disconnectedClient.getValue();
        setMessage(new ReconnectedEvent(selected));
        getWindow().close();
    }

}
