package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginMain extends Application implements Runnable {

   private Parent root;
   private LoginController loginController;

    /**
     *
     * @return
     */
    public LoginController getLoginController() {
        return loginController;
    }

    @Override
    public void run() {
        Application.launch(LoginMain.class);
    }


    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loginFxml = new FXMLLoader(getClass().getResource("/loginScene.fxml"));

        try {
            root = loginFxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginController = loginFxml.getController();
        primaryStage.setTitle("ADRENALINE");
        primaryStage.setScene(new Scene(root, 800, 560));
        loginController.getEnterButton().setDisable(true);
        primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }


}

