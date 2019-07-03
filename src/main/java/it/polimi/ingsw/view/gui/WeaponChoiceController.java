package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WeaponChoiceController extends AbstractController {

    @FXML
    private Button weapon3Button;
    @FXML
    private Button skipChoiceButton;

    @FXML
    private Button weapon1Button;

    @FXML
    private Button weapon4Button;

    @FXML
    private Button weapon2Button;

    public Button getWeapon3Button() {
        return weapon3Button;
    }

    public Button getSkipChoiceButton() {
        return skipChoiceButton;
    }

    public Button getWeapon1Button() {
        return weapon1Button;
    }

    public Button getWeapon4Button() {
        return weapon4Button;
    }

    public Button getWeapon2Button() {
        return weapon2Button;
    }
}
