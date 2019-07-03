package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.view_controller_event.ActionChoiceEvent;
import it.polimi.ingsw.event.view_controller_event.SkipActionChoiceEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ActionChoiceController extends AbstractController{

    @FXML
    private Button skipButton;

    @FXML
    private Button moveButton;

    @FXML
    private Button shotButton;

    @FXML
    private Button grabButton;

    public void setController(boolean isFireEnable){
        skipButton.setDisable(false);
        moveButton.setDisable(false);
        grabButton.setDisable(false);
        if(isFireEnable){
            shotButton.setDisable(false);
        }
        else{
            shotButton.setDisable(true);
        }
    }

    @FXML
    void moveClick(ActionEvent event) {
        setMessage(new ActionChoiceEvent(getGui().getUser(), 1));
        getWindow().close();
    }

    @FXML
    void grabClick(ActionEvent event) {
        setMessage(new ActionChoiceEvent(getGui().getUser(), 2));
        getWindow().close();
    }

    @FXML
    void shotClick(ActionEvent event) {
        setMessage(new ActionChoiceEvent(getGui().getUser(), 3));
        getWindow().close();
    }

    @FXML
    void skipClick(ActionEvent event) {
        setMessage(new SkipActionChoiceEvent(getGui().getUser()));
        getWindow().close();
    }

}


