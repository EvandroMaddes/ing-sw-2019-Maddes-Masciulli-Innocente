package it.polimi.ingsw.view.cli.graph;

import it.polimi.ingsw.model.player.Character;


/**
 * Its constructors allocate an AmmoTile or a Player representation by a string, depending on the arguments given
 */
public class CLIPrintableElement {
    private String resource = Color.ANSI_BLACK_BACKGROUND.escape();
    private boolean isPlayer;


    /**
     * constructor: it sets one player on the map
     * @param player charcacter to set
     * @param color color of his character
     */
    public CLIPrintableElement(Character player, String color){
        char firstChar = player.name().charAt(0);
        resource =  resource+ color+firstChar;
        isPlayer = true;
    }



    /**
     * getter
     * @return RESOURCE
     */
    public String getResource() {
        return resource;
    }

    /**
     * getter
     * @return isPlayer
     */
    public boolean isPlayer() {
        return isPlayer;
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

    /**
     * constructor: it sets ammoTile on one square
     * @param isPowerUp if ammoTile contains one power up
     * @param colors colors of ammoCube
     */
    public CLIPrintableElement(boolean isPowerUp, String[] colors){
        Color[] colorPrint = new Color[3];
        for(int i=0; i<colors.length; i++) {
            if (colors[i].equalsIgnoreCase("red"))
                colorPrint[i] = Color.ANSI_RED;
            else if (colors[i].equalsIgnoreCase("yellow"))
                colorPrint[i] = Color.ANSI_YELLOW;
            else if (colors[i].equalsIgnoreCase("blue"))
                colorPrint[i]= Color.ANSI_BLUE;
            else colorPrint[i] = Color.ANSI_WHITE;
        }
        resource = resource + colorPrint[0].escape()+"A"+ resource + colorPrint[1].escape() + "A";
        if(isPowerUp) {
                    resource = resource + Color.ANSI_BLACK_BACKGROUND.escape() + colorPrint[2].escape()+"P";
        }
        else{
            resource = resource + Color.ANSI_BLACK_BACKGROUND.escape() + colorPrint[2].escape() + "A";

        }
        resource = resource + Color.RESET.escape();
        isPlayer = false;
    }
}
