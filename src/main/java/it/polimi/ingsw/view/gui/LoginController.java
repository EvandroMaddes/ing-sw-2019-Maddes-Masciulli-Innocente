package it.polimi.ingsw.view.gui;
import it.polimi.ingsw.network.client.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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

    @FXML
    void enterClick(ActionEvent event) throws Exception {
        ipAddresString = IPaddress.getText();
        userString = username.getText();
        connectionString = connection.getText();

        if(getGui()==null){
            System.out.println("serveGUI");
        }
        getGui().setUser(username.getText());
        //Ho provate a ripasare la gui al loginMain
        getGui().setPrimaryStage((Stage)enterButton.getScene().getWindow());
        System.out.println("IP= "+ipAddresString);
        System.out.println(2);
        getGui().initialize();

        //getGui().gameInit(new String[]{userString,connectionString,ipAddresString});
    }


    @FXML
    void usernameType(ActionEvent event)        //todo username della GUI lo setto qui??
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
