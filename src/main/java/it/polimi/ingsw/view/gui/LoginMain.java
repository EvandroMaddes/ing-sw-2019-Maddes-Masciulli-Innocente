package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginMain extends Application{

   private Parent root;
   private LoginController loginController;
   private static final GUI gui = new GUI();

    /**
     *
     * @return
     */
    public LoginController getLoginController() {
        return loginController;
    }

    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loginFxml = new FXMLLoader(getClass().getResource("/loginScene.fxml"));

        primaryStage.setTitle("ADRENALINE");
        try {
            root = loginFxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginController = loginFxml.getController();
        loginController.setGui(gui);
        primaryStage.setScene(new Scene(root, 800, 560));
        primaryStage.show();
    }

    public static GUI getGui() {
        return gui;
    }


    public static void guiMain() {
        launch();
    }


/*
    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loginFxml = new FXMLLoader(getClass().getResource("/loginScene.fxml"));

        primaryStage.setTitle("ADRENALINE");
        try {
            root = loginFxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loginController = loginFxml.getController();

        loginController.setGui(gui);

        primaryStage.setScene(new Scene(root, 800, 560));
        // longrunning operation runs on different thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {

        primaryStage.setScene(new Scene(root, 800, 560));
                    }
                };
                https://riptutorial.com/it/home 102
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }
        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();
        primaryStage.show();

    }
*/

}

