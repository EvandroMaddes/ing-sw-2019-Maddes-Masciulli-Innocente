package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.player.Character;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.util.HashMap;
import java.util.Map;

/**
 * It controls scene of gameboard
 */
public class GameBoardController extends AbstractController{
    @FXML
    private HBox yourWeapon;


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
    private GridPane dstructorDamage;

    @FXML
    private GridPane dstructorAmmoCube;

    @FXML
    private GridPane bansheeDamage;

    @FXML
    private GridPane bansheeAmmoCube;

    @FXML
    private GridPane violetDamage;

    @FXML
    private GridPane violetAmmoCube;

    @FXML
    private GridPane dozerDamage;

    @FXML
    private GridPane dozerAmmoCube;

    @FXML
    private GridPane sprogAmmoCube;

    @FXML
    private GridPane sprogDamage;

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
        mapCharacterAmmoCube.put(Character.BANSHEE,new GridPane[]{bansheeDamage,bansheeAmmoCube});
        mapCharacterAmmoCube.put(Character.VIOLET,new GridPane[]{violetDamage,violetAmmoCube});
        mapCharacterAmmoCube.put(Character.DOZER,new GridPane[]{dozerDamage,dozerAmmoCube});
        mapCharacterAmmoCube.put(Character.SPROG,new GridPane[]{sprogDamage,sprogAmmoCube});
        mapCharacterAmmoCube.put(Character.D_STRUCT_OR,new GridPane[]{dstructorDamage,dstructorAmmoCube});
        gridImageAmmoCubeSetting(bansheeAmmoCube);
        gridImageAmmoCubeSetting(violetAmmoCube);
        gridImageAmmoCubeSetting(dozerAmmoCube);
        gridImageAmmoCubeSetting(sprogAmmoCube);
        gridImageAmmoCubeSetting(dstructorAmmoCube);
        gridImageClean(bansheeDamage);
        gridImageClean(violetDamage);
        gridImageClean(dozerDamage);
        gridImageClean(sprogDamage);
        gridImageClean(dstructorDamage);
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

    /**
     * It set on scene map selected
     * @param lefMap
     * @param rightmap
     */
    public void setMap(Image lefMap, Image rightmap){
        this.leftMap.setImage(lefMap);
        this.rightMap.setImage(rightmap);
    }

    /**
     * it sets images on a grid pane
     * @param toAdd images to add on a grid pane
     * @param toRefill grid pane in which add images
     */
    private void setImageOnGrid(Image[] toAdd,GridPane toRefill){
        gridImageClean(toRefill);
        for(int i=0; i<toAdd.length;i++){
            ImageView currImageView = ((ImageView)toRefill.getChildren().get(i));
            currImageView.setImage(toAdd[i]);
        }
    }

    /**
     * it removes evry singlle image on a grid pane
     * @param toClean grid pane to clean
     */
    private void gridImageClean(GridPane toClean){
        for (int i=0;i <toClean.getChildren().size();i++){
            ((ImageView)toClean.getChildren().get(i)).setImage(null);
            
        }
    }

    /**
     * it sets dimension of an ammoCube
     * @param toSet current grid to set
     */
    private void gridImageAmmoCubeSetting(GridPane toSet){
        for (int i=0;i <toSet.getChildren().size();i++){
            ((ImageView)toSet.getChildren().get(i)).setFitHeight(15);
            ((ImageView)toSet.getChildren().get(i)).setFitWidth(15);
        }
    }

    /**
     * It sets ammo on a playerBoard
     * @param character
     * @param ammo
     */
    public void setAmmo(Character character, Image[] ammo){
        GridPane curr = mapCharacterAmmoCube.get(character)[1];
        setImageOnGrid(ammo,curr);
    }

    /**
     * it sets Damages on a playerboard
     * @param character
     * @param damages
     */
    public void setDemage(Character character, Image[] damages){
            GridPane curr = mapCharacterAmmoCube.get(character)[0];
            setImageOnGrid(damages,curr);
        }

        public void setPlayerWeapon(Image[] weapon){
            for (int i=0; i<weapon.length;i++){
                ((ImageView)yourWeapon.getChildren().get(i)).setImage(weapon[i]);
            }
        }
}
