package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;

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
}
