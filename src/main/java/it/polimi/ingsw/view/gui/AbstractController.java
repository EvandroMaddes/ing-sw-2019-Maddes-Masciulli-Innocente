package it.polimi.ingsw.view.gui;

public abstract class AbstractController {
    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public GUI getGui() {
        return gui;
    }
}
