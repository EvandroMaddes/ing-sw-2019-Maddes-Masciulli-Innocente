package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.viewcontrollerevent.GenericPayChoiceEvent;
import it.polimi.ingsw.event.viewcontrollerevent.WeaponReloadPaymentChoiceEvent;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * It controls scene of payment
 * @author Evandro Maddes
 * @author Francesco Masciulli
 */
public class GenericPaymentController extends AbstractController {
    @FXML
    private Button skipAction;
    //Ammo button
    @FXML
    private Button button8;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    //Image view of ammoCube
    @FXML
    private ImageView ammo1Image;

    @FXML
    private ImageView ammo2Image;

    @FXML
    private ImageView ammo3Image;
    //Button of power up
    @FXML
    private Button powerUp1Button;

    @FXML
    private Button powerUp2Button;

    @FXML
    private Button powerUp3Button;


    //Image view of powerUp
    @FXML
    private ImageView powerUp3Image;
    @FXML
    private ImageView powerUp2Image;
    @FXML
    private ImageView powerUp1Image;

    @FXML
    private Button finishButton;

    @FXML
    private TextField infoArea;
    /**
     * Image of a ammo cube yellow
     */
    private Image cubeYellow = new Image("ammoCube/yellowammobox.png");

    /**
     * Image of a ammo cube red
     */
    private Image cubeRed = new Image("ammoCube/redammobox.png");

    /**
     * Image of a ammo cube blue
     */
    private Image cubeBlue = new Image("ammoCube/blueammobox.png");
    /**
     * List of power up image view
     */
    private ArrayList<ImageView> powerUpImageView;
    /**
     * Use to find powerUp images
     */
    private DecodeMessage decodeMessage;
    /**
     * number of power up request at least
     */
    private int min;

    /**
     * number of maximum power up chosen
     */
    private int max;
    /**
     * name of available power up
     */
    private String[] powerUpName;
    /**
     * color of available power up
     */
    private CubeColour[] powerUpColor;
    /**
     * name of available power up chosen
     */
    private ArrayList<String> powerUpNameChoice;
    /**
     * color of available power up chosen
     */
    private ArrayList<CubeColour> powerUpColorChoice;
    /**
     * type of payment
     */
    private boolean genericPayment;


    void init() {
        powerUpImageView = new ArrayList<>();
        powerUpImageView.add(powerUp1Image);
        powerUpImageView.add(powerUp2Image);
        powerUpImageView.add(powerUp3Image);
        decodeMessage = new DecodeMessage();
        powerUpNameChoice = new ArrayList<>();
        powerUpColorChoice = new ArrayList<>();
    }

    /**
     * It sets scene to pay with more then one item
     *
     * @param powerUpPayRequest     string to set on display
     * @param powerUpNames          name of powerUp available
     * @param powerUpColours        color of power up available
     * @param minimumPowerUpRequest if player ammo are not enough he must pay with powerUp
     * @param maximumPowerUpRequest max number of power up that user can use
     */
    public void setUpController(String powerUpPayRequest, String[] powerUpNames, CubeColour[] powerUpColours, int minimumPowerUpRequest, int maximumPowerUpRequest) {
        powerUpNameChoice.clear();
        powerUpColorChoice.clear();
        min = maximumPowerUpRequest;
        max = maximumPowerUpRequest;
        infoArea.setText(powerUpPayRequest);
        infoArea.setDisable(true);
        infoArea.setOpacity(1.0);
        powerUpName = powerUpNames;
        powerUpColor = powerUpColours;
        for (int i = 0; i < powerUpNames.length; i++) {
            Image currImage = decodeMessage.powerUpImage(powerUpNames[i], powerUpColours[i]);
            powerUpImageView.get(i).setImage(currImage);
        }
        finishButton.setDisable(true);
        skipAction.setDisable(false);
        setPowerUpButtons(powerUpNames);

    }

    /**
     * It set scene to pay one with only one item(power up or ammo cube)
     *
     * @param ammoCube       it sets ammo available tu use
     * @param powerUpsType   name of powerUp available
     * @param powerUpsColour color of power up available
     */
    public void genericPayment(boolean[] ammoCube, String[] powerUpsType, CubeColour[] powerUpsColour) {
        genericPayment = true;
        powerUpName = powerUpsType;
        this.powerUpColor = powerUpsColour;
        infoArea.setText("YOU MUST CHOOSE ONE ITEM TO PAY:");
        infoArea.setDisable(true);
        skipAction.setDisable(true);
        for (int i = 0; i < powerUpsType.length; i++) {
            Image currImage = decodeMessage.powerUpImage(powerUpsType[i], powerUpsColour[i]);
            powerUpImageView.get(i).setImage(currImage);
        }
        if (ammoCube[0]) {
            ammo1Image.setImage(cubeRed);
        }
        if (ammoCube[1]) {
            ammo2Image.setImage(cubeYellow);
        }
        if (ammoCube[2]) {
            ammo3Image.setImage(cubeBlue);
        }

        setPowerUpButtons(powerUpsType);
    }

    @FXML
    void button1Click(ActionEvent event) {
        setMessage(new GenericPayChoiceEvent(getGui().getUser(), new boolean[]{true, false, false}, null, null));
        getWindow().close();
        skipAction.setDisable(false);

    }

    @FXML
    void button2Click(ActionEvent event) {
        setMessage(new GenericPayChoiceEvent(getGui().getUser(), new boolean[]{false, true, false}, null, null));
        getWindow().close();
        skipAction.setDisable(false);

    }

    @FXML
    void button3Click(ActionEvent event) {
        setMessage(new GenericPayChoiceEvent(getGui().getUser(), new boolean[]{false, false, true}, null, null));
        getWindow().close();
    }

    @FXML
    void powerUp1Click(ActionEvent event) {
        powerUpNameChoice.add(powerUpName[0]);
        if (genericPayment) {
            setMessage(new GenericPayChoiceEvent(getGui().getUser(), new boolean[]{false, false, true}, powerUpName[0], powerUpColor[0]));
            getWindow().close();
        } else checkAnswer();
        skipAction.setDisable(false);

    }

    @FXML
    void powerUp2Click(ActionEvent event) {
        powerUpNameChoice.add(powerUpName[1]);
        if (genericPayment) {
            setMessage(new GenericPayChoiceEvent(getGui().getUser(), new boolean[]{false, false, true}, powerUpName[1], powerUpColor[1]));
            getWindow().close();
        } else checkAnswer();
        skipAction.setDisable(false);

    }

    @FXML
    void powerUp3Click(ActionEvent event) {
        powerUpNameChoice.add(powerUpName[2]);
        if (genericPayment) {
            setMessage(new GenericPayChoiceEvent(getGui().getUser(), new boolean[]{false, false, true}, powerUpName[2], powerUpColor[2]));
            getWindow().close();
        } else checkAnswer();
        skipAction.setDisable(false);

    }

    @FXML
    void finishClick(ActionEvent event) {

        finishButton.setDisable(false);
        setMessage(new WeaponReloadPaymentChoiceEvent(getGui().getUser(), (String[]) powerUpNameChoice.toArray(), (CubeColour[]) powerUpColorChoice.toArray()));
        getWindow().close();
        skipAction.setDisable(false);

    }

    /**
     * It checks if user can choose more item to pay
     */
    private void checkAnswer() {
        if (min == powerUpNameChoice.size()) {
            finishButton.setDisable(false);
        }
        if (max == powerUpNameChoice.size()) {
            setMessage(new WeaponReloadPaymentChoiceEvent(getGui().getUser(), (String[]) powerUpNameChoice.toArray(), (CubeColour[]) powerUpColorChoice.toArray()));
            getWindow().close();
        }
        skipAction.setDisable(false);

    }

    /**
     * It sets disable power up button on display
     *
     * @param powerUpNames available power up
     */
    private void setPowerUpButtons(String[] powerUpNames) {

        if (powerUpNames.length == 0) {
            powerUp1Button.setDisable(true);
            powerUp2Button.setDisable(true);
            powerUp3Button.setDisable(true);
        }
        if (powerUpNames.length == 1) {
            powerUp1Button.setDisable(true);
            powerUp2Button.setDisable(true);
        }
        if (powerUpNames.length == 2) {
            powerUp1Button.setDisable(true);
        }
    }

    @FXML
    void skipActionClick(ActionEvent event) {
        setMessage(new WeaponReloadPaymentChoiceEvent(getGui().getUser(), new String[]{},new CubeColour[]{}));
        getWindow().close();
    }
}
