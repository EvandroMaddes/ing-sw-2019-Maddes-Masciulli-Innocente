package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.viewcontrollerevent.CharacterChoiceEvent;
import it.polimi.ingsw.event.viewcontrollerevent.WeaponPlayersTargetChoiceEvent;
import it.polimi.ingsw.model.player.Character;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;

/**
 * It controls choice character scene
 *
 * @author Francesco Masciulli
 * @author Evandro Maddes
 */
public class CharacterChoiceController extends AbstractController {

    @FXML
    private Button finishButtom;

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

    @FXML
    private Label infoText;

    /**
     * it collects user target choices
     */
    private ArrayList<Character> targets = new ArrayList<>();

    /**
     * If user must choice target for a weapon
     */
    private boolean weaponTarget = false;

    private int numberOfTargets;


    /**
     * It set button on screen from available characters
     *
     * @param availableCharacters characters to choose
     */
    void setCharacterChoice(ArrayList<Character> availableCharacters, int numberOfTargets, boolean weaponTarget) {
        this.numberOfTargets = numberOfTargets;
        this.weaponTarget = weaponTarget;

        if (weaponTarget) {
            finishButtom.setDisable(false);
        } else {
            finishButtom.setDisable(true);
            finishButtom.setStyle("-fx-background-color: RGB(0,0,1,0.0)");
        }
        if (availableCharacters.contains(Character.BANSHEE)) {
            bansheeButton.setDisable(false);
        } else {
            bansheeButton.setDisable(true);
        }
        if (availableCharacters.contains(Character.D_STRUCT_OR)) {
            dstructorButton.setDisable(false);
        } else {
            dstructorButton.setDisable(true);
        }
        if (availableCharacters.contains(Character.DOZER)) {
            dozerButton.setDisable(false);
        } else {
            dozerButton.setDisable(true);
        }
        if (availableCharacters.contains(Character.SPROG)) {
            sprogButton.setDisable(false);
        } else {
            sprogButton.setDisable(true);
        }
        if (availableCharacters.contains(Character.VIOLET)) {
            violetButton.setDisable(false);
        } else {
            violetButton.setDisable(true);
        }
    }


    /**
     * User chooses to select one character
     */
    @FXML
    void sprogClick() {
        if (weaponTarget) {
            targets.add(Character.SPROG);

        } else {
            setMessage(new CharacterChoiceEvent(getGui().getUser(), Character.SPROG));
            getWindow().close();
        }
        sprogButton.setDisable(true);
        checkOthersTargets();
    }

    /**
     * User chooses to select one character
     */
    @FXML
    void bansheeClick() {
        if (weaponTarget) {
            targets.add(Character.BANSHEE);
        } else {
            setMessage(new CharacterChoiceEvent(getGui().getUser(), Character.BANSHEE));
            getWindow().close();
        }
        bansheeButton.setDisable(true);
        checkOthersTargets();
    }

    /**
     * User chooses to select one character
     */
    @FXML
    void dstructorClick() {
        if (weaponTarget) {
            targets.add(Character.D_STRUCT_OR);
        } else {
            setMessage(new CharacterChoiceEvent(getGui().getUser(), Character.D_STRUCT_OR));
            getWindow().close();
        }
        dstructorButton.setDisable(true);
        checkOthersTargets();
    }

    /**
     * User chooses to select one character
     */
    @FXML
    void violetClick() {
        if (weaponTarget) {
            targets.add(Character.VIOLET);
        } else {
            setMessage(new CharacterChoiceEvent(getGui().getUser(), Character.VIOLET));
            getWindow().close();
        }
        violetButton.setDisable(true);
        checkOthersTargets();
    }

    /**
     * User chooses to select one character
     */
    @FXML
    void dozerClick() {
        if (weaponTarget) {
            targets.add(Character.DOZER);
        } else {
            setMessage(new CharacterChoiceEvent(getGui().getUser(), Character.DOZER));
            getWindow().close();
        }
        dozerButton.setDisable(true);
        checkOthersTargets();
    }

    /**
     * It send message for user choice
     */
    @FXML
    void finishClick() {
        setMessage(new WeaponPlayersTargetChoiceEvent(getGui().getUser(), targets));
        getWindow().close();
    }

    private void checkOthersTargets(){
        if(weaponTarget){
            numberOfTargets--;
            if(numberOfTargets==0){
                finishButtom.setDisable(false);
                finishClick();
            }
        }
    }

    /**
     * It set text on the label
     *
     * @param string text to set
     */
    void setInfoText(String string) {
        infoText.setText(string);
    }

    /**
     * setter: set weapon target true
     */
    void trueWeaponTarget() {
        weaponTarget = true;
    }


}
