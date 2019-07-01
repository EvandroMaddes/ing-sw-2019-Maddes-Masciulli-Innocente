package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AbstractController {
    private GUI gui;
    private Stage primaryStage;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public GUI getGui() {
        return gui;
    }

    public void sendChoice(Event choice){
        gui.sendMessage(choice);
    }

    public void showScene(Scene scene){
    primaryStage.setScene(scene);
    primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
