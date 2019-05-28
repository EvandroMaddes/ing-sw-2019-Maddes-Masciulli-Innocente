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
    public CLIPrintableElement(Boolean isWeapon){

    }

    public CLIPrintableElement(boolean isPowerUp, String[] colors){
        Color[] colorPrint = new Color[3];
        for(int i=0; i<colors.length; i++) {
            if (Color.ANSI_RED.name().contains("RED"))
                colorPrint[i] = Color.ANSI_RED;
            else if (Color.ANSI_YELLOW.name().contains("YELLOW"))
                colorPrint[i] = Color.ANSI_YELLOW;
            else colorPrint[i]= Color.ANSI_BLUE;
        }
        resource = colorPrint[0].escape()+"█"+colorPrint[1].escape() + "█";
        if(isPowerUp) {
                    resource = resource + Color.ANSI_WHITE.escape()+"P";
        }
        else{
            resource = resource + colorPrint[2].escape() + "█";
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
