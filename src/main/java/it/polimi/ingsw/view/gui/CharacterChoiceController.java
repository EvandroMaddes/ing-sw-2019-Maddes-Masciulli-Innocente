package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.viewcontrollerevent.CharacterChoiceEvent;
import it.polimi.ingsw.model.player.Character;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;

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

    private ArrayList<Character> availableCharacters;

    public void setCharacterChoice(ArrayList<Character> availableCharacters){
        this.availableCharacters = availableCharacters;
        if(availableCharacters.contains(Character.BANSHEE)){
            getBansheeButton().setDisable(false);
        }
        else{
            getBansheeButton().setDisable(true);
        }
        if(availableCharacters.contains(Character.D_STRUCT_OR)){
            getDstructorButton().setDisable(false);
        }
        else{
            getDstructorButton().setDisable(true);
        }
        if(availableCharacters.contains(Character.DOZER)){
            getDozerButton().setDisable(false);
        }
        else{
            getDozerButton().setDisable(true);
        }
        if(availableCharacters.contains(Character.SPROG)){
            getSprogButton().setDisable(false);
        }
        else{
            getSprogButton().setDisable(true);
        }
        if(availableCharacters.contains(Character.VIOLET)){
            getVioletButton().setDisable(false);
        }
        else{
            getVioletButton().setDisable(true);
        }
    }

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

    @FXML
    void sprogClick() {
        setMessage(new CharacterChoiceEvent(getGui().getUser(),Character.SPROG));
        getWindow().close();
    }

    @FXML
    void bansheeClick() {
        setMessage(new CharacterChoiceEvent(getGui().getUser(),Character.BANSHEE));
        getWindow().close();
    }

    @FXML
    void dstructorClick() {
        setMessage(new CharacterChoiceEvent(getGui().getUser(),Character.D_STRUCT_OR));
        getWindow().close();
    }

    @FXML
    void violetClick() {
        setMessage(new CharacterChoiceEvent(getGui().getUser(),Character.VIOLET));
        getWindow().close();
    }

    @FXML
    void dozerClick() {
        setMessage(new CharacterChoiceEvent(getGui().getUser(),Character.DOZER));
        getWindow().close();
    }

}
