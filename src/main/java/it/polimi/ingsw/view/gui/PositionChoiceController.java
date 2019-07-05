package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.viewcontrollerevent.MoveChoiceEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

/**
 * It controls scene of position choice
 *
 * @author Evandro Maddes
 * @author Francesco Masciulli
 */
public class PositionChoiceController extends AbstractController {
    @FXML
    private ImageView mapImageView;

    @FXML
    private Button square7Button;

    @FXML
    private Button square10Button;

    @FXML
    private Button square0Button;

    @FXML
    private Button square2Button;

    @FXML
    private Button square5Button;

    @FXML
    private Button square6Button;

    @FXML
    private Button square1Button;

    @FXML
    private Button square4Button;

    @FXML
    private Button square9Button;

    @FXML
    private Button square11Button;

    @FXML
    private Button square3Button;

    @FXML
    private Button square8Button;


    /**
     * link between square and button
     */
    private HashMap<Integer, Button[]> mapSquareButton = new HashMap<>();

    /**
     * It sets mapping between square and button
     */
    void init() {
        mapSquareButton.put(0, new Button[]{square0Button, square1Button, square2Button, square3Button});
        mapSquareButton.put(1, new Button[]{square4Button, square5Button, square6Button, square7Button});
        mapSquareButton.put(2, new Button[]{square8Button, square9Button, square10Button, square11Button});

    }

    /**
     * It sets button and their opacity
     *
     * @param x x position of button
     * @param y y position of button
     */
    void setController(int[] x, int[] y) {
        for (int i = 0; i < 4; i++) {
            mapSquareButton.get(0)[i].setDisable(true);
            mapSquareButton.get(1)[i].setDisable(true);
            mapSquareButton.get(2)[i].setDisable(true);

            mapSquareButton.get(0)[i].setOpacity(0.5);
            mapSquareButton.get(1)[i].setDisable(true);
            mapSquareButton.get(1)[i].setOpacity(0.5);
            mapSquareButton.get(2)[i].setDisable(true);
            mapSquareButton.get(2)[i].setOpacity(0.5);
        }
        for (int i = 0; i < x.length; i++) {
            mapSquareButton.get(x[i])[y[i]].setDisable(false);
            mapSquareButton.get(x[i])[y[i]].setOpacity(0.0);

        }

    }

    @FXML
    void square0Click() {
        System.out.println("pressato");
        setMessage(new MoveChoiceEvent(getGui().getUser(), 0, 0));
        getWindow().close();
    }

    @FXML
    void square1Click() {
        System.out.println("pressato");

        setMessage(new MoveChoiceEvent(getGui().getUser(), 0, 1));
        getWindow().close();
    }

    @FXML
    void square2Click() {
        System.out.println("pressato");

        setMessage(new MoveChoiceEvent(getGui().getUser(), 0, 2));
        getWindow().close();
    }

    @FXML
    void square3Click() {
        System.out.println("pressato");

        setMessage(new MoveChoiceEvent(getGui().getUser(), 0, 3));
        getWindow().close();
    }

    @FXML
    void square4Click() {
        setMessage(new MoveChoiceEvent(getGui().getUser(), 1, 0));
        getWindow().close();
    }

    @FXML
    void square5Click() {
        setMessage(new MoveChoiceEvent(getGui().getUser(), 1, 1));
        getWindow().close();
    }

    @FXML
    void square6Click() {
        setMessage(new MoveChoiceEvent(getGui().getUser(), 1, 2));
        getWindow().close();
    }

    @FXML
    void square7Click(ActionEvent event) {
        setMessage(new MoveChoiceEvent(getGui().getUser(), 1, 3));
        getWindow().close();

    }

    @FXML
    void square8Click() {
        setMessage(new MoveChoiceEvent(getGui().getUser(), 2, 0));
        getWindow().close();
    }

    @FXML
    void square9Click() {
        setMessage(new MoveChoiceEvent(getGui().getUser(), 2, 1));
        getWindow().close();
    }

    @FXML
    void square10Click() {
        setMessage(new MoveChoiceEvent(getGui().getUser(), 2, 2));
        getWindow().close();
    }

    @FXML
    void square11Button() {
        setMessage(new MoveChoiceEvent(getGui().getUser(), 2, 3));
        getWindow().close();
    }

    /**
     * setter: it seta image on image view
     *
     * @param mapNumber map selected
     */
    void setMapImage(int mapNumber) {
        Image image;
        switch (mapNumber) {
            case 0:
                image = new Image("map/map0.png");
                break;
            case 1:
                image = new Image("map/map1.png");
                break;
            case 2:
                image = new Image("map/map2.png");
                break;
            case 3:
                image = new Image("map/map3.png");
                break;
            default:
                image = new Image("map/map0.png");

        }

        mapImageView.setImage(image);
    }

}
