package it.polimi.ingsw.view.cli.graph;

// TODO: 02/07/2019 completare commenti 
public abstract class TitleCharacter {

    // TODO: 25/06/2019 serve LENGHT? 
   public static final int LENGHT = 10;
   public static final int HEIGHT = 9;
   public static final Color color = Color.ANSI_GREEN;
   protected String[] string=new String[HEIGHT];


    /**
     * getter
     * @param row row
     * @return
     */
    public String getRow(int row) {
        return string[row];
    }


    /**
     * getter
     * @return COLOR
     */
    public Color getColor() {
        return color;
    }


}