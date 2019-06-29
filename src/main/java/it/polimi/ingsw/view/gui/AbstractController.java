package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import javafx.application.Platform;
import javafx.stage.Stage;

public abstract class AbstractController {
    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public GUI getGui() {
        return gui;
    }

    public void sendChoice(Event choice){
        gui.sendMessage(choice);
    }

    public void showStage(Stage stage){
    stage.show();
    System.out.println("shoew");
    }
}
