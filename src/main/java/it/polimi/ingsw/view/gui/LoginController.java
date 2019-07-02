package it.polimi.ingsw.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * It controls login scene
 */
public class LoginController extends AbstractController{
    private String userString;
    private String connectionString;
    private String ipAddresString;
    @FXML
    private AnchorPane anchorPanel;

    @FXML
    private Button enterButton;

    @FXML
    private TextField IPaddress;


    @FXML
    private TextField connection;

    @FXML
    private TextField username;

    private String usernameString;

    public Button getEnterButton() {
        return enterButton;
    }

    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * It manage click of a newGame button. It create a new GUI and start a thread which controls interaction between server and client
     * @param event click action
     * @throws Exception
     */
    @FXML
    void enterClick(ActionEvent event) {
        GUI gui = new GUI((Stage) enterButton.getScene().getWindow());
        setGui(gui);
        String[] choices = new String[3];
        ipAddresString = IPaddress.getText();
        userString = username.getText();
        connectionString = connection.getText();

        choices[0] = userString ;
        choices[1] = connectionString;
        choices[2] = ipAddresString;
        getGui().setClientChoices(choices);
        getGui().setUser(username.getText());
        gui.initialize();
        gui.setClientChoices(choices);
        gui.setPrimaryStage(stage);
        new Thread(()->gui.startInterface()).start();
    }


    @FXML
    void usernameType(ActionEvent event)
    {
    }

    @FXML
    void connectionType(ActionEvent event) {
        //check sulla correttezza dell'input oppure settarlo a default

    }
    @FXML
    void ipType(ActionEvent event) {
        //check sulla correttezza dell'input oppure settarlo a default

        enterButton.setDisable(false);
    }

}
