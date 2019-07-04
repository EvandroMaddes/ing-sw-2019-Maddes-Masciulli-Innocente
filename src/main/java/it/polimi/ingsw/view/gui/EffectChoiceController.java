package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.viewcontrollerevent.SkipActionChoiceEvent;
import it.polimi.ingsw.event.viewcontrollerevent.WeaponEffectChioceEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * It controls effectChoice scene
 * @author Evandro Maddes
 *
 */
public class EffectChoiceController extends AbstractController {
    @FXML
    private Button effect2Buttom;

    @FXML
    private Button effect1Buttom;

    @FXML
    private Button effect3Buttom;

    @FXML
    private Button skipAction;


    /**
     * It sets button on screen
     * @param available available effect
     */
     void setController(boolean[] available){
            effect1Buttom.setDisable(!available[0]);
            effect2Buttom.setDisable(!available[1]);
            effect3Buttom.setDisable(!available[2]);
            skipAction.setDisable(false);

     }


    @FXML
    void effect1Click(ActionEvent event) {
         skipAction.setDisable(true);
        setMessage(new WeaponEffectChioceEvent(getGui().getUser(),1));
        getWindow().close();
    }

    @FXML
    void effect2Click(ActionEvent event) {
        skipAction.setDisable(true);
        setMessage(new WeaponEffectChioceEvent(getGui().getUser(),2));
        getWindow().close();

    }


    @FXML
    void effect3Click(ActionEvent event) {
        skipAction.setDisable(true);
        setMessage(new WeaponEffectChioceEvent(getGui().getUser(),3));
        getWindow().close();

    }

    @FXML
    void skipActionClick(ActionEvent event) {
        skipAction.setDisable(true);
        setMessage(new SkipActionChoiceEvent(getGui().getUser()));
        getWindow().close();
    }

}
