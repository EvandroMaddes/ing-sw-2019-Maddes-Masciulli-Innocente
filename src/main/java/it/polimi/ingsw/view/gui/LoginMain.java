package it.polimi.ingsw.view.gui;

import javafx.application.Application;
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
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loginFxml = new FXMLLoader(getClass().getResource("/loginScene.fxml"));

        primaryStage.setTitle("ADRENALINE");
        try {
            root = loginFxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loginController = loginFxml.getController();

        loginController.setGui(gui);

        loginController.setPrincipale(this);
        primaryStage.setScene(new Scene(root, 800, 560));
        primaryStage.show();

    }


    public static GUI getGui() {
        return gui;
    }

    /*public void setGui(GUI gui) {
        this.gui = gui;
    }
*/
    public static void main(String[] args) {
        launch(args);
    }


}

