package it.polimi.ingsw.view.cli.graph;

import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;

/**
 * Its constructors allocate an AmmoTile or a Player representation by a string, depending on the arguments given
 */
public class CLIPrintableElement {
    private String resource;
    private boolean isPlayer;


    public CLIPrintableElement(Character player, Color color){
        char firstChar = player.name().charAt(0);
        resource =  color.escape()+firstChar;
        isPlayer = true;
    }

    public CLIPrintableElement(boolean isPowerUp, Color[] colors){
        resource = colors[0].escape()+"█"+colors[1].escape() + "█";
        if(isPowerUp) {
                    resource = resource + Color.ANSI_WHITE.escape()+"P";
        }
        else{
            resource = resource + colors[2].escape() + "█";
        }
        resource = resource + Color.RESET.escape();
        isPlayer = false;
    }
    public CLIPrintableElement(String currentWeapon, String currentSpawnSquare){

    }

    public String getResource() {
        return resource;
    }

    public boolean isPlayer() {
        return isPlayer;
    }
}
