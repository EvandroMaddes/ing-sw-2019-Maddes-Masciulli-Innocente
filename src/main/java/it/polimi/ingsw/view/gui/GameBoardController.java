package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.ActionChoiceEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class GameBoardController extends AbstractController{


    @FXML
    private GridPane gridMap;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button moveButton;

    @FXML
    private Button grabButton;

    @FXML
    private Button fireButton;


    @FXML
    void gridMapClick() {

    }

    public Button getFireButton() {
        return fireButton;
    }

    @FXML
    void moveButtonPress(ActionEvent event) {

        sendChoice(new ActionChoiceEvent(getGui().getUser(),1));
    }

    @FXML
    void grabButtonPress(ActionEvent event) {

        sendChoice(new ActionChoiceEvent(getGui().getUser(),2));
    }



    @FXML
    void fireButtonPress(ActionEvent event) {

        sendChoice(new ActionChoiceEvent(getGui().getUser(),3));

    }


}
