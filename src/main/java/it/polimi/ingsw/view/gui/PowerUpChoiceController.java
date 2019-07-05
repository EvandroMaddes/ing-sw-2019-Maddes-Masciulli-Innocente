package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.viewcontrollerevent.EndRoundPowerUpChoiceEvent;
import it.polimi.ingsw.event.viewcontrollerevent.PowerUpChoiceEvent;
import it.polimi.ingsw.event.viewcontrollerevent.SkipActionChoiceEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * It controls powerUp choice scene
 * @author Evandro Maddes
 * @author Francesco Masciulli
 */
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
    private TextField infoArea;

    /**
     * Contains user choices
     */
    private ArrayList<String> powerUpChoice ;
    /**
     *   * Contains user choices
     */
    private ArrayList<CubeColour> colorChoice;
    /**
     * type of choice
     */
    private boolean endOfRoundPowerUp= false;
    /**
     * parameter to GUI
     */
    private boolean wantToUse = false;

    /**
     * Number of powerUp to choose
     */
    private int toChose = 1;

    /**
     * It set scene of powerUp choice
     * @param powerUpNames name of available powerUp
     * @param powerUpColours color of available powerUp
     * @param toChose number of powerUp to choose
     */
    public void setController(String[] powerUpNames, CubeColour[] powerUpColours, int toChose) {
        this.toChose = toChose;
        powerUpChoice = new ArrayList<>(Arrays.asList(powerUpNames));
        colorChoice = new ArrayList<>(Arrays.asList(powerUpColours));
        DecodeMessage decoder = new DecodeMessage();
        for (int i = 0; i < powerUpNames.length; i++) {
            whichImage(i).setImage(decoder.powerUpImage(powerUpNames[i], powerUpColours[i]));
            whichButton(i).setDisable(false);
        }
        finishButtom.setDisable(true);

    }

    /**
     * It find the correct image
     * @param i number of image
     * @return image view
     */
    private ImageView whichImage(int i) {
        if (i == 0) {
            return powerUp1Image;
        } else if (i == 1) {
            return powerUp2Image;
        } else {
            return powerUp3Image;
        }
    }

    /**
     * It find the correct button
     * @param i number of button
     * @return correct button
     */
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
        System.out.println("Seleziono power up 1");
        checkChoice(powerUpChoice.get(0), colorChoice.get(0));
        System.out.println("Seleziono power up 1");

    }

    @FXML
    void powerUp2Click(ActionEvent event) {
        wantToUse=true;
        powerUp2Button.setDisable(true);
        checkChoice(powerUpChoice.get(1), colorChoice.get(1));
    }

    @FXML
    void powerUp3Click(ActionEvent event) {
        wantToUse=true;
        powerUp3Button.setDisable(true);
        checkChoice(powerUpChoice.get(2), colorChoice.get(2));
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
        infoArea.setText(info);
        infoArea.setDisable(true);
        infoArea.setOpacity(1.0);
    }

    private void checkChoice(String name, CubeColour color) {
        if (toChose == 1) {
            System.out.println("invio messaggio di spawn");
            setMessage(new PowerUpChoiceEvent(getGui().getUser(), name, color));
            getWindow().close();
            System.out.println("invio messaggio di spawn");

        } else {
            powerUpChoice.add(name);
            colorChoice.add(color);
        }
        finishButtom.setDisable(false);
        skipChoiceButton.setDisable(true);

    }

    /**
     * it set true endOfRoundPowerUp
     */
    public void setTrueEndOfRoundPowerUp() {
        endOfRoundPowerUp=true;
    }

    /**
     *
     * @return
     */
    public boolean isWantToUse() {
        return wantToUse;
    }

    /**
     * setter
     * @param mode able or disable skipChoiceButton
     */
    public void setSkipChoiceButtonDisable(boolean mode) {
        skipChoiceButton.setDisable(mode);
    }
}
