package it.polimi.ingsw.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class GameBoardController extends AbstractController{

    @FXML
    private GridPane gridMap;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    void gridMapClick(ActionEvent event) {
        System.out.println(gridMap.getLayoutY());

    }
}
