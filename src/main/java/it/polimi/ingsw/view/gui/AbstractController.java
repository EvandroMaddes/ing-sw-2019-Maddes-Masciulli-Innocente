package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * It is a controller
 */
public abstract class AbstractController {
    private GUI gui;
    private Stage primaryStage;

    /**
     * setter
     * @param gui
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * getter
     * @return GUI
     */
    public GUI getGui() {
        return gui;
    }

    /**
     * It send a choice to server
     * @param choice user choice
     */
    public void sendChoice(Event choice){
        gui.sendMessage(choice);
    }

    /**
     * It change scene on a stage
     * @param scene scene to show
     */
    public void showScene(Scene scene){
    primaryStage.setScene(scene);
    primaryStage.show();
    }

    /**
     * getter
     * @return primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * setter
     * @param primaryStage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
