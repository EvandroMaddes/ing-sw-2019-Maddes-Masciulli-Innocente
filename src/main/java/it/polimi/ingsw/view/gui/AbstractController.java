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
    /**
     * User interface
     */
    private GUI gui;
    /**
     * user choice
     */
    private Event message;
    /**
     * stage showed
     */
    private Stage window;

    /**
     * fetter
     *
     * @return message
     */
    public Event getMessage() {
        return message;
    }

    /**
     * setter
     *
     * @param window window to show
     */
    void setWindow(Stage window) {
        this.window = window;
    }

    /**
     * getter
     *
     * @return window
     */
    public Stage getWindow() {
        return window;
    }

    /**
     * setter
     *
     * @param window stage to show
     */
    public void setStage(Stage window) {
        this.window = window;
    }

    /**
     * setter
     *
     * @param message event
     */
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
     * It show a request and wait user choice
     *
     * @return user choice
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
     * It take user choice
     *
     * @param query stage showed
     * @return user choice
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
