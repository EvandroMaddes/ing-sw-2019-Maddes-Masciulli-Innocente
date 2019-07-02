package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginMain extends Application{

   private Parent root;
   private static Stage loginStage;
   private LoginController loginController;
   private static GUI gui;

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

        FXMLLoader loginFxml = new FXMLLoader(getClass().getResource("/fxml/loginScene.fxml"));
        primaryStage.setTitle("ADRENALINE");
        try {
            root = loginFxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginController = loginFxml.getController();
        DecodeMessage decodeTEST= new DecodeMessage();
        loginController.setStage(primaryStage);
        primaryStage.setScene(new Scene(root, 800, 560));
        primaryStage.show();
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

