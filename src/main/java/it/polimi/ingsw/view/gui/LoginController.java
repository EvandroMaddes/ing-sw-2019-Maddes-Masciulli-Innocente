package it.polimi.ingsw.view.gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends AbstractController{


    private String userString;
    private String connectionString;
    private String ipAddresString;
    @FXML
    private Button enterButton;

    @FXML
    private TextField IPaddress;


    @FXML
    private TextField connection;

    @FXML
    private TextField username;

    @FXML
    void enterClick(ActionEvent event) {
        ipAddresString = IPaddress.getText();
        userString = username.getText();
        connectionString = connection.getText();
        setGui(new GUI());
        getGui().setStage((Stage)enterButton.getScene().getWindow());
        System.out.println("IP= "+ipAddresString);

        getGui().gameInit(new String[]{userString,connectionString,ipAddresString});
    }


    @FXML
    void usernameType(ActionEvent event) {
        getGui().setUser(username.getText());
    }

    @FXML
    void connectionType(ActionEvent event) {

    }
    @FXML
    void ipType(ActionEvent event) {
        sendChoice(null);
    }

}
