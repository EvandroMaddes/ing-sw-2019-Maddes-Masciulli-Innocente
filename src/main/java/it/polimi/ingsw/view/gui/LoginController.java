package it.polimi.ingsw.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * It controls login scene
 *
 * @author Evandro Maddes
 * @author Francesco Masciulli
 */
public class LoginController extends AbstractController {
    /**
     * It is used to check the correctness of the input data
     */
    private boolean accepted = false;

    @FXML
    private Button enterButton;

    @FXML
    private TextField ipAddress;

    @FXML
    private TextField connection;

    @FXML
    private TextField username;


    /**
     * stage showed
     */
    private Stage stage;

    /**
     * getter
     *
     * @return enterButton
     */
    public Button getEnterButton() {
        return enterButton;
    }

    /**
     * setter
     *
     * @param stage showing stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * It manage click of a newGame button. It create a new GUI and start a thread which controls interaction between server and client
     *
     * @param event click action
     * @throws Exception
     */
    @FXML
    void enterClick(ActionEvent event) {
        GUI gui = new GUI((Stage) enterButton.getScene().getWindow());
        setGui(gui);
        String[] choices = new String[3];
        String ipAddresString = ipAddress.getText();
        String userString = username.getText();
        String connectionString = connection.getText();
        choices[0] = userString;
        choices[1] = connectionString;
        choices[2] = ipAddresString;
        getGui().setClientChoices(choices);
        getGui().setUser(username.getText());
        gui.initialize();
        gui.setClientChoices(choices);
        new Thread(() -> gui.startInterface()).start();
    }

    /**
     * It sets username of player
     *
     * @param event typing on username box
     */
    @FXML
    void usernameType(ActionEvent event) {
        username.getText();
    }

    /**
     * It sets type connection choice of aplayer
     *
     * @param event typing on connection box
     */
    @FXML
    void connectionType(ActionEvent event) {
        try {
            if (!connection.getText().equalsIgnoreCase("RMI") && !connection.getText().equalsIgnoreCase("SOCKET")) {
                accepted = false;
            } else {
                accepted = true;
                ipType(event);
            }
        } catch (NullPointerException exc) {
            accepted = false;
        }
    }

    /**
     * It set ip addres
     *
     * @param event typing on address box
     */
    @FXML
    void ipType(ActionEvent event) {
        if (accepted) {
            enterButton.setDisable(false);
        }
    }


}
