package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * It launch GUI interface
 * @author Evandro Maddes
 */
public class LoginMain extends Application {

    /**
     * It loads first controller and first scene and then shows it
     * @param primaryStage stage to show
     */
    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        LoginController loginController;
        FXMLLoader loginFxml = new FXMLLoader(getClass().getResource("/fxml/loginScene.fxml"));
        primaryStage.setTitle("ADRENALINE");
        try {
            root = loginFxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginController = loginFxml.getController();
        loginController.getEnterButton().setDisable(true);
        loginController.setStage(primaryStage);
        primaryStage.setScene(new Scene(root, 880, 620));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * It starts application gui
     */
    public static void guiMain() {
        launch();
    }



}

