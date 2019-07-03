package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class PowerUpChoiceController extends AbstractController{

    @FXML
    private ImageView powerUp3Image;

    @FXML
    private Button powerUp2Button;

    @FXML
    private ImageView powerUp1Image;

    @FXML
    private Button skipChoiceButton;

    @FXML
    private Button powerUp3Button;

    @FXML
    private ImageView powerUp2Image;

    @FXML
    private Button powerUp1Button;

    private int toChose;

    public void setController(String[] powerUpNames, CubeColour[] powerUpColours, int toChose){
        this.toChose = toChose;
        DecodeMessage decoder = new DecodeMessage();
        for (int i = 0; i < powerUpNames.length; i++) {
            whichImage(i).setImage(decoder.powerUpImage(powerUpNames[i],powerUpColours[i]));
            whichButton(i).setDisable(false);
        }

    }

    private ImageView whichImage(int i){
        if(i==0){
            return powerUp1Image;
        }
        else if(i==1){
                return powerUp2Image;
        }
        else{
            return powerUp3Image;
        }
    }

    private Button whichButton(int i){
        if(i==0){
            return powerUp1Button;
        }
        else if(i==1){
            return powerUp2Button;
        }
        else{
            return powerUp3Button;
        }
    }
    @FXML
    void powerUp1Click(ActionEvent event) {

    }

    @FXML
    void powerUp2Click(ActionEvent event) {

    }

    @FXML
    void powerUp3Click(ActionEvent event) {

    }

    @FXML
    void skipChoiceClick(ActionEvent event) {

    }


}
