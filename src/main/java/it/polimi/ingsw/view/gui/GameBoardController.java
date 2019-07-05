package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.player.Character;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * It controls scene of gameboard
 *
 * @author Evandro Maddes
 */
public class GameBoardController extends AbstractController {

    //Square
    @FXML
    private VBox vboxSquare0;

    @FXML
    private VBox vboxSquare1;

    @FXML
    private VBox vboxSquare2;

    @FXML
    private VBox vboxSquare3;

    @FXML
    private VBox vboxSquare4;

    @FXML
    private VBox vboxSquare5;

    @FXML
    private VBox vboxSquare6;

    @FXML
    private VBox vboxSquare7;

    @FXML
    private VBox vboxSquare8;

    @FXML
    private VBox vboxSquare9;


    @FXML
    private VBox vboxSquare11;

    @FXML
    private VBox vboxSquare10;


    //game track
    @FXML
    private GridPane gameTrack;

    //User area
    @FXML
    private HBox yourPowerUp;

    @FXML
    private HBox yourWeapon;

    @FXML
    private GridPane yourSkull;

    @FXML
    private TextArea infoArea;

    //Map
    @FXML
    private ImageView rightMap;

    @FXML
    private ImageView leftMap;

    //PLAYERBOARD
    @FXML
    private ImageView downLeftPlayerboard;
    @FXML
    private ImageView topCenterPlayerboard;
    @FXML
    private ImageView downCenterPlayerboard;
    @FXML
    private ImageView topLeftPlayerboard;
    @FXML
    private ImageView principalPlayerBoard;
    @FXML
    private GridPane downCenterDamage;

    @FXML
    private GridPane downCenterMarks;

    @FXML
    private GridPane downCenterAmmoCube;

    @FXML
    private GridPane topLeftDamage;

    @FXML
    private GridPane topLeftMarks;

    @FXML
    private GridPane topLeftAmmoCube;

    @FXML
    private GridPane principalDamage;

    @FXML
    private GridPane principalMarks;

    @FXML
    private GridPane principalAmmoCube;

    @FXML
    private GridPane topCenterDamage;

    @FXML
    private GridPane topCenterMarks;

    @FXML
    private GridPane topCenterAmmoCube;


    @FXML
    private GridPane downLeftDamage;

    @FXML
    private GridPane downLeftMarks;

    @FXML
    private GridPane downLeftAmmoCube;
    //SPAWNSQUARE
    @FXML
    private HBox spawnBlue;

    @FXML
    private HBox spawnYellow;

    @FXML
    private HBox spawnRed;

    /**
     * Image of one skull
     */
    private Image skullImage;
    /**
     * link between character and his damage, mark and ammo cube
     */
    private Map<Character, GridPane[]> mapCharacterAmmoCube = new EnumMap<>(Character.class);
    /**
     * link between character and his position
     */
    private Map<Character, ImageView> mapCharcaterPosition = new EnumMap<Character, ImageView>(Character.class);
    /**
     * link between square and its vbox
     */
    private Map<VBox, int[]> mapSquareVBox = new HashMap<>();
    /**
     * number of skull's player
     */
    private int numberOfSkull = 0;
    /**
     * List of square(vbox)
     */
    private ArrayList<VBox> square = new ArrayList<>();
    /**
     * Number of player in game
     */
    private int numberOfPlayers = 0;


    /**
     * It initializes controller and sets its attributes
     */
    void init() {

        gridImageAmmoCubeSetting(topLeftAmmoCube);
        gridImageAmmoCubeSetting(principalAmmoCube);
        gridImageAmmoCubeSetting(topCenterAmmoCube);
        gridImageAmmoCubeSetting(downLeftAmmoCube);
        gridImageAmmoCubeSetting(downCenterAmmoCube);
        gridImageClean(topLeftDamage);
        gridImageClean(principalDamage);
        gridImageClean(topCenterDamage);
        gridImageClean(downLeftDamage);
        gridImageClean(downCenterDamage);
        int[] position0 = {0, 0};
        int[] position1 = {1, 0};
        int[] position2 = {2, 0};
        int[] position3 = {3, 0};
        int[] position4 = {0, 1};
        int[] position5 = {1, 1};
        int[] position6 = {2, 1};
        int[] position7 = {3, 1};
        int[] position8 = {0, 2};
        int[] position9 = {1, 2};
        int[] position10 = {2, 2};
        int[] position11 = {3, 2};

        mapSquareVBox.put(vboxSquare0, position0);
        mapSquareVBox.put(vboxSquare1, position1);
        mapSquareVBox.put(vboxSquare2, position2);
        mapSquareVBox.put(vboxSquare3, position3);
        mapSquareVBox.put(vboxSquare4, position4);
        mapSquareVBox.put(vboxSquare5, position5);
        mapSquareVBox.put(vboxSquare6, position6);
        mapSquareVBox.put(vboxSquare7, position7);
        mapSquareVBox.put(vboxSquare8, position8);
        mapSquareVBox.put(vboxSquare9, position9);
        mapSquareVBox.put(vboxSquare10, position10);
        mapSquareVBox.put(vboxSquare11, position11);

        square.add(vboxSquare0);
        square.add(vboxSquare1);
        square.add(vboxSquare2);
        square.add(vboxSquare3);
        square.add(vboxSquare4);
        square.add(vboxSquare5);
        square.add(vboxSquare6);
        square.add(vboxSquare7);
        square.add(vboxSquare8);
        square.add(vboxSquare9);
        square.add(vboxSquare10);
        square.add(vboxSquare11);

        infoArea.setDisable(true);
        skullImage = new Image("skull.png");
        gameTrack.setVisible(false);
    }


    /**
     * It set on scene map selected
     *
     * @param lefMap   left map selected
     * @param rightmap right map selected
     */
    void setMap(Image lefMap, Image rightmap) {
        this.leftMap.setImage(lefMap);
        this.rightMap.setImage(rightmap);
        gameTrack.setVisible(true);
    }

    /**
     * getter
     *
     * @return left map image view
     */
    public ImageView getLeftMap() {
        return leftMap;
    }

    /**
     * setter: It link one player to his playerboard
     *
     * @param playerboard image of a playerboard
     * @param character   character of playerboard
     */
    void setPrincipalPlayerboard(Image playerboard, Character character) {
        principalPlayerBoard.setImage(playerboard);
        mapCharacterAmmoCube.put(character, new GridPane[]{principalDamage, principalAmmoCube, principalMarks});
        initAmmocube(principalAmmoCube);
    }

    /**
     * setter: It link one player to his playerboard
     *
     * @param playerboard image of a playerboard
     * @param character   character of playerboard
     */
    private void setTopLeftPlayerboard(Image playerboard, Character character) {
        topLeftPlayerboard.setImage(playerboard);
        mapCharacterAmmoCube.put(character, new GridPane[]{topLeftDamage, topLeftAmmoCube, topLeftMarks});
    }

    /**
     * setter: It link one player to his playerboard
     *
     * @param playerboard image of a playerboard
     * @param character   character of playerboard
     */
    private void setTopCenterPlayerboard(Image playerboard, Character character) {
        topCenterPlayerboard.setImage(playerboard);
        mapCharacterAmmoCube.put(character, new GridPane[]{topCenterDamage, topCenterAmmoCube, topCenterMarks});
    }

    /**
     * setter: It link one player to his player board
     *
     * @param playerboard image of a player board
     * @param character   character of player board
     */
    private void setDownLeftPlayerboardLeftPlayerboard(Image playerboard, Character character) {
        downLeftPlayerboard.setImage(playerboard);
        mapCharacterAmmoCube.put(character, new GridPane[]{downLeftDamage, downLeftAmmoCube, downLeftMarks});
    }

    /**
     * setter: It link one player to his player board
     *
     * @param playerboard image of a player board
     * @param character   character of player board
     */
    private void setDownCenterPlayerboardPlayerboard(Image playerboard, Character character) {
        downCenterPlayerboard.setImage(playerboard);
        mapCharacterAmmoCube.put(character, new GridPane[]{downCenterDamage, downCenterAmmoCube, downCenterMarks});
    }

    /**
     * it sets images on a grid pane
     *
     * @param toAdd    images to add on a grid pane
     * @param toRefill grid pane in which add images
     */
    private void setImageOnGrid(Image[] toAdd, GridPane toRefill) {
        gridImageClean(toRefill);
        for (int i = 0; i < toAdd.length; i++) {
            ImageView currImageView = ((ImageView) toRefill.getChildren().get(i));
            currImageView.setImage(toAdd[i]);
        }
    }

    /**
     * it removes every single image on a grid pane
     *
     * @param toClean grid pane to clean
     */
    private void gridImageClean(GridPane toClean) {
        for (int i = 0; i < toClean.getChildren().size(); i++) {
            ((ImageView) toClean.getChildren().get(i)).setImage(null);

        }


    }

    /**
     * It removes every single image of a hBox
     *
     * @param hBox grid pane to clean
     */
    private void hBoxImageClean(HBox hBox) {
        for (int i = 0; i < hBox.getChildren().size(); i++) {
            ((ImageView) hBox.getChildren().get(i)).setImage(null);
        }
    }

    /**
     * it sets dimension of an ammoCube
     *
     * @param toSet current grid to set
     */
    private void gridImageAmmoCubeSetting(GridPane toSet) {
        for (int i = 0; i < toSet.getChildren().size(); i++) {
            ((ImageView) toSet.getChildren().get(i)).setFitHeight(15);
            ((ImageView) toSet.getChildren().get(i)).setFitWidth(15);
            ((ImageView) toSet.getChildren().get(i)).setImage(null);
        }

    }

    /**
     * It sets ammo on a playerBoard
     *
     * @param character player board of this character
     * @param ammo      available ammo to add
     */
    void setAmmo(Character character, Image[] ammo) {
        GridPane curr = mapCharacterAmmoCube.get(character)[1];
        setImageOnGrid(ammo, curr);
    }

    /**
     * it sets damages on a player board
     *
     * @param character player board of this character
     * @param damages   damage to add
     */
    void setDamage(Character character, Image[] damages) {
        GridPane curr = mapCharacterAmmoCube.get(character)[0];
        setImageOnGrid(damages, curr);
    }

    /**
     * It sets weapons of player
     *
     * @param weapon weapons to set
     */
    void setPlayerWeapon(Image[] weapon) {
        hBoxImageClean(yourWeapon);
        for (int i = 0; i < weapon.length; i++) {
            ((ImageView) yourWeapon.getChildren().get(i)).setImage(weapon[i]);
        }
    }

    /**
     * It sets weapons on spawn square
     *
     * @param x           column which identifier a spawn square
     * @param spawnWeapon weapon to add
     */
    void setSpawnWeapon(int x, Image[] spawnWeapon) {

        HBox current = new HBox();
        if (x == 0) {
            hBoxImageClean(spawnRed);
            current = spawnRed;
        } else if (x == 2) {
            hBoxImageClean(spawnBlue);
            current = spawnBlue;
        } else if (x == 3) {
            hBoxImageClean(spawnYellow);
            current = spawnYellow;
        }

        for (int i = 0; i < spawnWeapon.length; i++) {
            ((ImageView) current.getChildren().get(i)).setImage(spawnWeapon[i]);
        }
    }

    /**
     * It sets power up of player
     *
     * @param powerUp power up to set
     */
    void setPlayerPowerUp(Image[] powerUp) {
        hBoxImageClean(yourPowerUp);
        System.out.println(" rimossi power up");
        for (int i = 0; i < powerUp.length; i++) {
            ((ImageView) yourPowerUp.getChildren().get(i)).setImage(powerUp[i]);
        }
    }

    /**
     * It shows a new information on text area
     *
     * @param toSet information to show
     */
    void setInfo(String toSet) {
        infoArea.setText(toSet);
        infoArea.setOpacity(1.0);
    }

    /**
     * It remove skull from game track
     *
     * @param numberOfToken number of player token to replace
     * @param token         image of token
     */
    void removeSkull(int numberOfToken, Image token) {
        ImageView current = ((ImageView) gameTrack.getChildren().get(numberOfSkull));
        if (numberOfSkull < 8) {
            current.setImage(token);
            numberOfSkull++;
            if (numberOfToken == 2) {
                ((ImageView) gameTrack.getChildren().get(numberOfSkull + 7)).setImage(token);
            }
        }
    }

    /**
     * it cleans game track
     */
    void gameTrackClean() {
        for (int i = numberOfSkull; i >= 0; i--) {
            ((ImageView) gameTrack.getChildren().get(numberOfSkull)).setImage(null);
        }
        numberOfSkull = 0;
    }

    /**
     * It remove characterImage from old position and it sets characterImage ii  the new one
     *
     * @param x              is the Square column on the map
     * @param y              is the Square row on the map
     * @param character      characterImage to set
     * @param characterImage is the character image
     */
    public void setPosition(int x, int y, Image characterImage, Character character) {
        removeCharacter(character);

        VBox square = null;
        for (VBox currentSquare : this.square
        ) {
            if (mapSquareVBox.get(currentSquare)[0] == x && mapSquareVBox.get(currentSquare)[1] == y) {
                square = currentSquare;
            }
        }
        //gli passo null perche mi serve il primo posto libero sul quadrato
        ImageView newPosition = getPosition(square, null);
        newPosition.setImage(characterImage);
        if (mapCharcaterPosition.containsKey(character))
            mapCharcaterPosition.replace(character, newPosition);
        else
            mapCharcaterPosition.put(character, newPosition);

    }

    /**
     * It remove one character from the map
     *
     * @param character character to remove
     */
    private void removeCharacter(Character character) {
        if (mapCharcaterPosition.containsKey(character))
            mapCharcaterPosition.get(character).setImage(null);
    }


    /**
     * It find in a square the first free position to put an image
     *
     * @param square square in which search
     * @return location in which set an image
     */
    private ImageView getPosition(VBox square, Image toFind) {
        ImageView position = new ImageView();

        for (int i = 0; i < 3; i++) {
            ImageView currentImageView = ((ImageView) ((HBox) square.getChildren().get(0)).getChildren().get(i));
            if (currentImageView.getImage() == toFind) {
                position = currentImageView;
                break;
            }
        }

        for (int j = 0; j < 3; j++) {
            ImageView currentImageViewBis = ((ImageView) ((HBox) square.getChildren().get(1)).getChildren().get(j));
            if (currentImageViewBis.getImage() == toFind) {
                position = currentImageViewBis;
                break;
            }
        }

        return position;
    }

    /**
     * It adds skull on player board
     *
     * @param skullNumber number of skull to add
     */
    void addPlayerSkull(int skullNumber) {
        for (int i = 0; i < skullNumber; i++) {
            ((ImageView) yourSkull.getChildren().get(i)).setImage(skullImage);
        }
    }

    /**
     * It add an image on a square in section for ammo tile
     *
     * @param x     coordinate x
     * @param y     coordinate y
     * @param toAdd image to add
     */
    private void setAmmoTile(int x, int y, Image toAdd) {
        for (VBox currentSquare : square
        ) {
            if (mapSquareVBox.get(currentSquare)[0] == x && mapSquareVBox.get(currentSquare)[1] == y) {
                ((ImageView) currentSquare.getChildren().get(2)).setImage(toAdd);
            }
        }
    }

    /**
     * It remove an ammo tile on a square
     *
     * @param x coordinate x
     * @param y coordinate y
     */
    void removeAmmoTileOnMap(int x, int y) {
        setAmmoTile(x, y, null);
    }

    /**
     * It add an image of ammo tile on a square
     *
     * @param x     coordinate x
     * @param y     coordinate y
     * @param toAdd ammo tile to add
     */
    void addAmmoTileOnMap(int x, int y, Image toAdd) {
        setAmmoTile(x, y, toAdd);
    }

    /**
     * it adds marks on player board
     *
     * @param character player board in which add marks
     * @param damages   number of marks
     */
    public void setMark(Character character, Image[] damages) {
        GridPane curr = mapCharacterAmmoCube.get(character)[2];
        setImageOnGrid(damages, curr);
    }

    /**
     * It find free position to add a playerboard
     *
     * @param playerBoard image to add
     * @param character   character choose
     */
    void setNewPlayer(Image playerBoard, Character character) {
        switch (numberOfPlayers) {

            case 0:
                setTopLeftPlayerboard(playerBoard, character);
                initAmmocube(topLeftAmmoCube);
                numberOfPlayers++;
                break;
            case 1:
                setTopCenterPlayerboard(playerBoard, character);
                initAmmocube(topCenterAmmoCube);
                numberOfPlayers++;
                break;
            case 2:
                setDownLeftPlayerboardLeftPlayerboard(playerBoard, character);
                initAmmocube(downLeftAmmoCube);
                numberOfPlayers++;
                break;
            case 3:
                setDownCenterPlayerboardPlayerboard(playerBoard, character);
                initAmmocube(downLeftAmmoCube);
                numberOfPlayers++;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * It adds three ammo cube in a grid map
     *
     * @param ammoCubeGrid grid in which put ammo cube
     */
    private void initAmmocube(GridPane ammoCubeGrid) {
        ((ImageView) ammoCubeGrid.getChildren().get(0)).setImage(new Image("ammoCube/yellowammobox.png"));
        ((ImageView) ammoCubeGrid.getChildren().get(1)).setImage(new Image("ammoCube/blueammobox.png"));
        ((ImageView) ammoCubeGrid.getChildren().get(2)).setImage(new Image("ammoCube/redammobox.png"));
    }

}
