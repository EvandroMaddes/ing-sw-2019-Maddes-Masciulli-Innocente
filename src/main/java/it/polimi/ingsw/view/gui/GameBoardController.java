package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.player.Character;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
    private GridPane gameTrack;
    
    @FXML
    private HBox yourPowerUp;


    @FXML
    private HBox yourWeapon;


    @FXML
    private ImageView rightMap;

    @FXML
    private ImageView leftMap;

    @FXML
    private TextArea infoArea;

    @FXML
    private GridPane gridMap;

    @FXML
    private AnchorPane anchorPane;
    
    //PLAYERBOARD
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
    private HBox spawnBlue;

    @FXML
    private HBox spawnYellow;

    @FXML
    private HBox spawnRed;

    private Map<Character, GridPane[]> mapCharacterAmmoCube = new HashMap<Character,GridPane[]>();
    
    private int numberOfSkull = 0;

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
     * it removes every single image on a grid pane
     * @param toClean grid pane to clean
     */
    private void gridImageClean(GridPane toClean){
        for (int i=0;i <toClean.getChildren().size();i++){
            ((ImageView)toClean.getChildren().get(i)).setImage(null);
            
        }
    }

    /**
     * /**
     * It removes every single image of a hBox
     * @param hBox grid pane to clean
     */
    public void hBoxImageClean(HBox hBox){
        for (int i=0;i <hBox.getChildren().size();i++){
            ((ImageView)hBox.getChildren().get(i)).setImage(null);

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

    /**
     * It Sets weapons of player
     * @param weapon weapons to set
     */
    public void setPlayerWeapon(Image[] weapon){
        hBoxImageClean(yourWeapon);
            for (int i = 0; i< weapon.length; i++){
                ((ImageView) yourWeapon.getChildren().get(i)).setImage(weapon[i]);
            }
        }

    /**
     * It sets weapons on spawn square
     * @param x column which identifier a spawn square
     * @param spawnWeapon weapon to add
     */
    public void setSpawnWeapon(int x, Image[] spawnWeapon){

        HBox current = new HBox();
            if (x==0){
                hBoxImageClean(spawnRed);
                current=spawnRed;
            }else if (x==2){
                hBoxImageClean(spawnBlue);
                current=spawnBlue;
            }else if (x==3){
                hBoxImageClean(spawnYellow);
                current=spawnYellow;
            }

            for (int i=0; i<spawnWeapon.length;i++){
                ((ImageView)current.getChildren().get(i)).setImage(spawnWeapon[i]);
            }
        }
    /**
     * It sets power up of player
     * @param powerUp power up to set
     */
    public void setPlayerPowerUp(Image[] powerUp) {
        for (int i = 0; i< powerUp.length; i++){
            ((ImageView)yourPowerUp.getChildren().get(i)).setImage(powerUp[i]);
        }
    }

    /**
     * It shows a new information on text area
     * @param toSet
     */
    public void setInfo(String toSet){
        infoArea.setText(toSet);
    }
    
    public void removeSkull(){
        if (numberOfSkull<9) {
            ((ImageView) gameTrack.getChildren().get(numberOfSkull)).setImage(null);
            numberOfSkull++;
        }
        // TODO: 02/07/2019 aggiungere segnalini giocatori 
    }
}
