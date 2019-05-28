package it.polimi.ingsw.view.cli.graph;

import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;

/**
 * Its constructors allocate an AmmoTile or a Player representation by a string, depending on the arguments given
 */
public class CLIPrintableElement {
    private String resource;

    public CLIPrintableElement(Character player, Color color){
        resource =  player.name()+color.escape();
    }

    public CLIPrintableElement(boolean isPowerUp, Color[] colors){
        resource = colors[0].escape()+"A"+colors[1].escape() + "A";
        if(isPowerUp) {
                    resource = resource + Color.ANSI_WHITE.escape()+"P";
        }
        else{
            resource = resource + colors[2].escape() + "A";
        }
        resource = resource + Color.RESET.escape();
    }

    public String getResource() {
        return resource;
    }
}
