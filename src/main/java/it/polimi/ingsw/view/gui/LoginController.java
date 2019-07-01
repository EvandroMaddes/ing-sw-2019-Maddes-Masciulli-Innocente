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

    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    void enterClick(ActionEvent event) throws Exception {
        GUI gui = new GUI((Stage) enterButton.getScene().getWindow());
        setGui(gui);
        String[] choices = new String[3];
        ipAddresString = IPaddress.getText();
        userString = username.getText();
        connectionString = connection.getText();

        choices[0] = userString ;
        choices[1] = connectionString;
        choices[2] = ipAddresString;

        System.out.println("\nUser: " + choices[0] +
                "\nConnection: " + choices[1] +
                "\nIp: " + choices[2]);

        getGui().setClientChoices(choices);
        getGui().setUser(username.getText());
        getGui().initialize();
        gui.initialize();
        gui.setClientChoices(choices);
        new Thread(()->gui.startInterface()).start();
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
