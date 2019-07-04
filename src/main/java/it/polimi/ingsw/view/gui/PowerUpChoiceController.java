package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.viewcontrollerevent.EndRoundPowerUpChoiceEvent;
import it.polimi.ingsw.event.viewcontrollerevent.PowerUpChoiceEvent;
import it.polimi.ingsw.event.viewcontrollerevent.SkipActionChoiceEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class PowerUpChoiceController extends AbstractController {

    @FXML
    private ImageView powerUp3Image;

    @FXML
    private Button powerUp2Button;

    @FXML
    private ImageView powerUp1Image;

    @FXML
    private Button finishButtom;

    @FXML
    private Button skipChoiceButton;

    @FXML
    private Button powerUp3Button;

    @FXML
    private ImageView powerUp2Image;

    @FXML
    private Button powerUp1Button;

    @FXML
    private Label infoLabel;

    private String powerUp1;
    private CubeColour color1;

    private String powerUp2;
    private CubeColour color2;

    private String powerUp3;
    private CubeColour color3;

    private ArrayList<String> powerUpChoice = new ArrayList<>();
    private ArrayList<CubeColour> colorChoice = new ArrayList<>();

    private boolean endOfRoundPowerUp= false;
    private boolean wantToUse = false;

    private int toChose = 1;


    public void setController(String[] powerUpNames, CubeColour[] powerUpColours, int toChose) {
        this.toChose = toChose;
        DecodeMessage decoder = new DecodeMessage();
        for (int i = 0; i < powerUpNames.length; i++) {
            whichImage(i).setImage(decoder.powerUpImage(powerUpNames[i], powerUpColours[i]));
            whichButton(i).setDisable(false);
        }
        finishButtom.setDisable(true);
    }

    private ImageView whichImage(int i) {
        if (i == 0) {
            return powerUp1Image;
        } else if (i == 1) {
            return powerUp2Image;
        } else {
            return powerUp3Image;
        }
    }

    private Button whichButton(int i) {
        if (i == 0) {
            return powerUp1Button;
        } else if (i == 1) {
            return powerUp2Button;
        } else {
            return powerUp3Button;
        }
    }

    @FXML
    void powerUp1Click(ActionEvent event) {
        powerUp1Button.setDisable(true);
        wantToUse=true;
        checkChoice(powerUp1, color1);
    }

    @FXML
    void powerUp2Click(ActionEvent event) {
        wantToUse=true;
        powerUp2Button.setDisable(true);
        checkChoice(powerUp2, color2);
    }

    @FXML
    void powerUp3Click(ActionEvent event) {
        wantToUse=true;
        powerUp3Button.setDisable(true);
        checkChoice(powerUp3, color3);
    }

    @FXML
    void skipChoiceClick(ActionEvent event) {
    if (endOfRoundPowerUp){
        setMessage(new EndRoundPowerUpChoiceEvent(getGui().getUser(),null,null));
        getWindow().close();
    }else {
        wantToUse=false;
        setMessage(new SkipActionChoiceEvent(getGui().getUser()));
        getWindow().close();
        }
        setSkipChoiceButtonDisable(false);
    }

    @FXML
    void finishClick(ActionEvent event) {

        if (endOfRoundPowerUp) {
            setMessage(new EndRoundPowerUpChoiceEvent(getGui().getUser(), (String[]) powerUpChoice.toArray(), (CubeColour[]) colorChoice.toArray()));
            endOfRoundPowerUp=false;
            getWindow().close();
        }

    }


    public void setInfoLabel(String info) {
        infoLabel.setText(info);
    }

    private void checkChoice(String name, CubeColour color) {
        if (toChose == 1) {
            setMessage(new PowerUpChoiceEvent(getGui().getUser(), name, color));
            getWindow().close();
        } else {
            powerUpChoice.add(name);
            colorChoice.add(color);
        }
        finishButtom.setDisable(false);
        skipChoiceButton.setDisable(true);

    }

    public void setTrueEndOfRoundPowerUp() {
        endOfRoundPowerUp=true;
    }

    public boolean isWantToUse() {
        return wantToUse;
    }

    public void setSkipChoiceButtonDisable(boolean mode) {
        skipChoiceButton.setDisable(mode);
    }
}
