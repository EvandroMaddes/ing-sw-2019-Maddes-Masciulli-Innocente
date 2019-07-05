package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.viewcontrollerevent.WeaponChoiceEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * It controls action choice scene
 *
 * @author Francesco Masciulli
 * @author Evandro Maddes
 */
public class WeaponChoiceController extends AbstractController {

    @FXML
    private TextField infoText;

    @FXML
    private Button weapon3Button;

    @FXML
    private ImageView weapon1Image;

    @FXML
    private ImageView weapon3Image;


    @FXML
    private ImageView weapon4Image;

    @FXML
    private ImageView weapon2Image;

    @FXML
    private Button weapon1Button;

    @FXML
    private Button weapon4Button;

    @FXML
    private Button weapon2Button;

    /**
     * Weapon available
     */
    private String[] weapon;
    /**
     * Image view on screen
     */
    private ArrayList<ImageView> weaponImageView = new ArrayList<>();
    /**
     *
     */
    ArrayList<Button> weaponButton = new ArrayList<>();
    /**
     * Use to get image
     */
    private DecodeMessage decodeMessage;

    /**
     * Constructor: set image view on array list
     */
    void init() {
        decodeMessage = new DecodeMessage();
        weaponImageView.add(weapon1Image);
        weaponButton.add(weapon1Button);
        weaponImageView.add(weapon2Image);
        weaponButton.add(weapon2Button);
        weaponImageView.add(weapon3Image);
        weaponButton.add(weapon2Button);
        weaponImageView.add(weapon4Image);
        weaponButton.add(weapon3Button);
        weapon1Image.setImage(null);
        weapon2Image.setImage(null);
        weapon3Image.setImage(null);
        weapon4Image.setImage(null);
    }

    /**
     * It sets available weapon and its image
     * @param weapon weapons available
     */
    public void setController(ArrayList<String> weapon) {
        this.weapon = weapon.toArray(this.weapon);
        for (Button currentButton: weaponButton){
            currentButton.setDisable(true);
        }
        for (int i = 0; i < weapon.size(); i++) {
            Image currentImage = decodeMessage.weaponImage(weapon.get(i));
            weaponImageView.get(i).setImage(currentImage);
            weaponButton.get(i).setDisable(false);
        }
    }

    @FXML
    void weapon1Click(ActionEvent event) {
        setMessage(new WeaponChoiceEvent(getGui().getUser(), weapon[0]));
        getWindow().close();
    }

    @FXML
    void weapon2Click(ActionEvent event) {
        setMessage(new WeaponChoiceEvent(getGui().getUser(), weapon[1]));
        getWindow().close();

    }

    @FXML
    void weapon3Click(ActionEvent event) {
        setMessage(new WeaponChoiceEvent(getGui().getUser(), weapon[2]));
        getWindow().close();

    }

    @FXML
    void weapon4Click(ActionEvent event) {
        setMessage(new WeaponChoiceEvent(getGui().getUser(), weapon[4]));
        getWindow().close();

    }

    void  setText(String info){
        infoText.setText(info);
        infoText.setDisable(true);
        infoText.setOpacity(1.0);
    }

}
