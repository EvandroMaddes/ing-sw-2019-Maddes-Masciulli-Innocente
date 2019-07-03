package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.utils.CustomLogger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * It controls scene of gameboard
 */
public class GameBoardController extends AbstractController{

    @FXML
    private VBox vboxSquare0;

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

    //prova popUp
    @FXML
    private AnchorPane popUpPane;

    AbstractController popUpController;

    private Map<Character, GridPane[]> mapCharacterAmmoCube = new HashMap<Character,GridPane[]>();
    private Map<Integer[],VBox> mapSquareVBox = new HashMap<Integer[], VBox>();
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
        // TODO: 02/07/2019 settare le associazioni square Vbox 
        // TODO: 02/07/2019 settare tutte le immagini
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

    public void removeSkull(int numberOfToken,Image token){
      ImageView current = ((ImageView) gameTrack.getChildren().get(numberOfSkull));
        if (numberOfSkull<8) {
            current.setImage(token);
            numberOfSkull++;
            if (numberOfToken==2){
                ((ImageView)gameTrack.getChildren().get(numberOfSkull+7)).setImage(token);
            }
        }
    }

    public void gameTrackClean(){
        for (int i = numberOfSkull; i>=0; i--){
            ((ImageView) gameTrack.getChildren().get(numberOfSkull)).setImage(null);
        }
        numberOfSkull = 0 ;
    }

    /**
     * 
     * @param square position:{x,y}
     * @param character
     */
    public void setPosition(int[] square, Image character){
        // TODO: 02/07/2019 rimuoverlo dalla precedente posizione
        VBox currentSquare = ((VBox)(mapSquareVBox.get(square).getChildren()));
        getFreePosition(currentSquare).setImage(character);
        
        
    }

    /**
     * IT find in a square the first free position to put an image
     * @param square
     * @return
     */
    private ImageView getFreePosition(VBox square){
        ImageView curentImageView = new ImageView();
        int j=0;
        for (int i=0;i<3;i++){
            curentImageView =((ImageView)((HBox)square.getChildren().get(j)).getChildren().get(i));
            if (curentImageView.getImage()==null){
                i=3;
            }
            if (i==2){
                j++;
                i=0;
            }
        }
        
        return curentImageView;
    }

    // TODO: 02/07/2019 setare ammo sugli square playerboard marchi


    public AbstractController getPopUpController() {
        return popUpController;
    }

    /**
     *
     * @return
     */
    public Event askPopUp(AbstractController popUpController, Path popUpPath, Scene scene){
        setPopUpPane(popUpPath);
        //popUpController.setWindow(getWindow());

        Event returnedEvent = ask(scene);
        cleanPopUpPane();
        return returnedEvent;
    }

    /**
     * set the popUpPane in the invisible AnchorPane and make it visible
     * @param popUpPath is the popUp fxml Path given to te loader;
     */
    private void setPopUpPane(Path popUpPath) {
        Task query = new Task() {
            @Override
            protected AnchorPane call() throws Exception {
                try{
                    Pane popUp = FXMLLoader.load(getClass().getResource(popUpPath.toString()));
                    popUpPane.getChildren().add(popUp);
                    popUpPane.setStyle("-fx-background-color: grey;");
                }catch (IOException noFXML){
                    CustomLogger.logException(noFXML);
                }
                return popUpPane;
            }

        };
        Platform.runLater(query);
    }

    /**
     *
     */
    private void cleanPopUpPane(){
        Task query = new Task() {
            @Override
            protected AnchorPane call() throws Exception {
                popUpPane.getChildren().addAll(popUpPane.getChildren());
                popUpPane.setStyle("-fx-background-color: rgba(0, 255, 0, 0);");
                return popUpPane;
            }

        };
        Platform.runLater(query);
    }

}
