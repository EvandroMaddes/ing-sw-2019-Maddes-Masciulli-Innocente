package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.view_controller_event.ActionChoiceEvent;
import it.polimi.ingsw.model.game_components.ammo.AmmoCube;
import it.polimi.ingsw.model.player.Character;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * It controls scene of gameboard
 */
public class GameBoardController extends AbstractController{

    @FXML
    private ImageView rightMap;

    @FXML
    private ImageView leftMap;


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


    private Map<Character, GridPane[]> mapCharacterAmmoCube = new HashMap<Character,GridPane[]>();

    public void init(){
        mapCharacterAmmoCube.put(Character.BANSHEE,new GridPane[]{bansheeDamege,bansheeAmmoCube});
        mapCharacterAmmoCube.put(Character.VIOLET,new GridPane[]{violetDamege,violetAmmoCube});
        mapCharacterAmmoCube.put(Character.DOZER,new GridPane[]{dozerDamege,dozerAmmoCube});
        mapCharacterAmmoCube.put(Character.SPROG,new GridPane[]{sprogDamege,sprogAmmoCube});
        mapCharacterAmmoCube.put(Character.D_STRUCT_OR,new GridPane[]{dstructorDamege,dstructorAmmoCube});

    }

    public Button getFireButton() {
        return fireButton;
    }

    @FXML
    void moveButtonPress(ActionEvent event) {

    }

    @FXML
    void grabButtonPress(ActionEvent event) {

    }




    @FXML
    void fireButtonPress() {

    }

    public void setFirstWeaponSpawnBlueImage(Image firstWeaponSpawnBlue) {
        this.firstWeaponSpawnBlue.setImage(firstWeaponSpawnBlue);
    }

    public void setMap(Image lefMap, Image rightmap){
        this.leftMap.setImage(lefMap);
        this.rightMap.setImage(rightmap);
    }

    public void setAmmo(Image[] ammo, Character character){
        GridPane currPane = mapCharacterAmmoCube.get(character)[0];
        gridImageClean(currPane);
        for(int i=0; i<ammo.length;i++){
            ImageView currImageView = ((ImageView)currPane.getChildren().get(i));
            currImageView.setImage(ammo[i]);
        }
    }

    public void gridImageClean(GridPane toClean){
        for (int i=0;i <toClean.getChildren().size();i++){
            ((ImageView)toClean.getChildren().get(i)).setImage(null);
        }
    }

}
