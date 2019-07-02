package it.polimi.ingsw.view.cli.graph;

/**
 * It manage title "ADRENALINE"
 */
public class Title {
    private static final int LENGHT = 210;
    private static final int HEIGHT = 9;


    /**
     * It prints title "ADRENALINE"
     */
    public static void printTitle(){
        String[] strings = new String[LENGHT];
        TitleCharacter[] title = new TitleCharacter[10];
        title[0] = new ATitleCharacter();
        title[1] = new DTitleCharacter();
        title[2] = new RTitleCharacter();
        title[3] = new ETitleCharacter();
        title[4] = new NTitleCharacter();
        title[5] = new ATitleCharacter();
        title[6] = new LTitleCharacter();
        title[7] = new ITitleCharacter();
        title[8] = new NTitleCharacter();
        title[9] = new ETitleCharacter();

        for (int i = 0; i < HEIGHT; i++) {
            for (TitleCharacter currCharacter :title) {
                if(strings[i]==null){
                    strings[i]= currCharacter.getRow(i);
                }
                else {
                    strings[i] = strings[i] + currCharacter.getRow(i);
                }
                }
        }

        System.out.println(Color.ANSI_BLACK_BACKGROUND.escape());
        for (int i = 0; i < HEIGHT; i++) {

            for (int j = 0; j < i; j++) {

                System.out.print(" ");
            }
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape());
            System.out.println(title[0].getColor().escape() + strings[i]);

        }



    }
}
