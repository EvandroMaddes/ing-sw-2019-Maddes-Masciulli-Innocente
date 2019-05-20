package it.polimi.ingsw.view.cli.graph;

import it.polimi.ingsw.view.cli.graph.Color;

public abstract class TitleCharacter {
   public static final int LENGHT = 10;
   public static final int HEIGHT = 9;
   public static final Color color = Color.ANSI_WHITE;
   protected int startRow;
   protected String[] string=new String[HEIGHT];



    public String getRow(int row) {
        return string[row];
    }

    public Color getColor() {
        return color;
    }


}