package it.polimi.ingsw.view.cli.graph;

/**
 * It's title adrenaline
 *
 * @author Francesco Masciulli
 */
public abstract class TitleCharacter {

    public static final int HEIGHT = 9;
    public static final Color color = Color.ANSI_GREEN;
    protected String[] string = new String[HEIGHT];


    /**
     * getter
     *
     * @param row row
     * @return
     */
    public String getRow(int row) {
        return string[row];
    }


    /**
     * getter
     *
     * @return COLOR
     */
    public Color getColor() {
        return color;
    }


}