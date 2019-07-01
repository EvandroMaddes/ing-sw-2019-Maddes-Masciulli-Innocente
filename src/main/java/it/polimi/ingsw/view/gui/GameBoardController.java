package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.view_controller_event.ActionChoiceEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private GridPane dstructorDamege;

    @FXML
    private GridPane dstructorAmmoCube;

    @FXML
    private GridPane bansheeDamege;

    @FXML
    private GridPane bansheeAmmoCube;

    @FXML
    private GridPane violetDamege;

    @FXML
    private GridPane violetAmmoCube;

    @FXML
    private GridPane dozerDamege;

    @FXML
    private GridPane dozerAmmoCube;

    @FXML
    private GridPane sprogAmmoCube;

    @FXML
    private GridPane sprogDamege;

    //SPAWNSQUARE
    @FXML
    private ImageView firstWeaponSpawnRed;

    @FXML
    private ImageView secondWeaponSpawnRed;

    @FXML
    private ImageView thirdWeaponSpawnRed;

    @FXML
    private ImageView firstWeaponSpawnBlue;


    @FXML
    private ImageView secondWeaponSpawnBlue;

    @FXML
    private ImageView thridWeaponSpawnBlue;

    @FXML
    private ImageView firstWeaponSpawnYellow;

    @FXML
    private ImageView secondWeaponSpawnYellow;

    @FXML
    private ImageView thridWeaponSpawnYellow;




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

    public void setFirstWeaponSpawnBlueImage(Image firstWeaponSpawnBlue) {
        this.firstWeaponSpawnBlue.setImage(firstWeaponSpawnBlue);
    }
}
