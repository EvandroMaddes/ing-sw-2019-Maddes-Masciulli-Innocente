package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.utils.CustomLogger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * It is a controller with common method of all controller
 *
 * @author Evandro Maddes
 */
public abstract class AbstractController {
    private GUI gui;
    private Event message;
    private Stage window;

    public Event getMessage() {
        return message;
    }

    void setWindow(Stage window) {
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
     * @param gui is the GUI instance
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
     * Handle the respectively scene update and
     * @param scene is the choice respectively scene
     * @return the Event representing the user choice
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


    /**
     * Call a Task get using Platform.runLater and waiting the update on graphic interface
     * @param query is the Task implementation that show the scene and wait for an answer
     * @return the Event representing the user choice
     */
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
