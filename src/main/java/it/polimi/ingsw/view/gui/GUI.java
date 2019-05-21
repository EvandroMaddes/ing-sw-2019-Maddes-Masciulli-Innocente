package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

public class GUI extends RemoteView {

/*    public GUI(String user) {
        super(user);
    }

 */

    @Override
    public String[] gameInit() {
        return new String[0];
    }

    @Override
    public Event characterChoice(ArrayList<Character> availableCharacters) {
        return null;
    }

    @Override
    public void printScreen() {

    }

}
