package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.event.viewcontrollerevent.UpdateChoiceEvent;
import it.polimi.ingsw.utils.CustomLogger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * It is a controller
 */
public abstract class AbstractController {
    private GUI gui;
    private Stage primaryStage;
    private Event message;
    private Stage window;

    public Event getMessage() {
        return message;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public Stage getWindow() {
        return window;
    }

    public void setStage(Stage window) {
        this.window = window;
    }

    public void setMessage(Event message) {
        this.message = message;
    }

    /**
     * setter
     *
     * @param gui
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * getter
     *
     * @return GUI
     */
    public GUI getGui() {
        return gui;
    }

    /**
     * getter
     *
     * @return primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * setter
     *
     * @param primaryStage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * @return
     */
    public Event ask(Scene scene) {
        final Task<Event> query = new Task<Event>() {
            @Override
            public Event call() throws Exception {
                window.setScene(scene);
                window.showAndWait();
                Event event = message;
                return event;
            }
        };
        return ask(query);
    }


    public final Event ask(Task<Event> query) {
        Platform.runLater(query);
        try {
            return query.get();
        } catch (Exception interrupted) {
            CustomLogger.logException(interrupted);
            return null;
        }
    }

}
