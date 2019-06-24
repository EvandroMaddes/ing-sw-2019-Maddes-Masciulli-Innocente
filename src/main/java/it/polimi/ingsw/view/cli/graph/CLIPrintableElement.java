package it.polimi.ingsw.view.cli.graph;

import it.polimi.ingsw.model.player.Character;

import java.util.ArrayList;
import java.util.Map;

/**
 * Its constructors allocate an AmmoTile or a Player representation by a string, depending on the arguments given
 */
public class CLIPrintableElement {
    private String resource = Color.ANSI_BLACK_BACKGROUND.escape();
    private boolean isPlayer;


    public CLIPrintableElement(Character player, String color){
        char firstChar = player.name().charAt(0);
        resource =  resource+ color+firstChar;
        isPlayer = true;
    }

    /**
     * is called when an update require removing an ammotile or a weapon on the CLIMap
     * @param isWeapon is true if the replaced resource is a weapon, false if is an ammotile
     */
    public CLIPrintableElement(boolean isWeapon){
        if(!isWeapon){
            resource = resource + "  " + "  " + "  ";
        }
        else{
            resource = " ";
        }

    }

    public CLIPrintableElement(boolean isPowerUp, String[] colors){
        Color[] colorPrint = new Color[3];
        for(int i=0; i<colors.length; i++) {
            if (colors[i].equalsIgnoreCase("red"))
                colorPrint[i] = Color.ANSI_RED;
            else if (colors[i].equalsIgnoreCase("yellow"))
                colorPrint[i] = Color.ANSI_YELLOW;
            else colorPrint[i]= Color.ANSI_BLUE;
        }
        resource = resource + colorPrint[0].escape()+"A"+ resource + colorPrint[1].escape() + "A";
        if(isPowerUp) {
                    resource = resource + Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_WHITE.escape()+"P";
        }
        else{
            resource = resource + Color.ANSI_BLACK_BACKGROUND.escape() + colorPrint[2].escape() + "A";
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
