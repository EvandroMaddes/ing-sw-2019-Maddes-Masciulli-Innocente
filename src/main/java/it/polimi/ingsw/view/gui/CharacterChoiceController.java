package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CharacterChoiceController extends AbstractController {

    @FXML
    private Button dozerButton;

    @FXML
    private Button violetButton;

    @FXML
    private Button sprogButton;

    @FXML
    private Button bansheeButton;

    @FXML
    private Button dstructorButton;

    public Button getDozerButton() {
        return dozerButton;
    }

    public Button getVioletButton() {
        return violetButton;
    }

    public Button getSprogButton() {
        return sprogButton;
    }

    public Button getBansheeButton() {
        return bansheeButton;
    }

    public Button getDstructorButton() {
        return dstructorButton;
    }
}
