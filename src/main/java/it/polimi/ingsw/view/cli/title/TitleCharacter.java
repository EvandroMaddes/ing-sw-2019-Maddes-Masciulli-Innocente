package it.polimi.ingsw.view.cli.title;

public abstract class TitleCharacter {
   protected static final int LENGHT = 10;
   protected static final int HEIGHT = 9;
   protected static final Color color = Color.ANSI_WHITE;
   protected int startRow;
   protected String[] string=new String[HEIGHT];



    public String getRow(int row) {
        return string[row];
    }

    public Color getColor() {
        return color;
    }


}