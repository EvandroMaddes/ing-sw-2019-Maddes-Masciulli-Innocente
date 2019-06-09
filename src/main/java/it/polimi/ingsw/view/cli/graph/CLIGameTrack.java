package it.polimi.ingsw.view.cli.graph;

public class CLIGameTrack {
    private static final int MAXCOLUMN = 32;
    private static final int MAXROW = 3;
    private String[][] track = new String[MAXCOLUMN][MAXROW];

    public String[][] getTrack() {
        return track;
    }

    public void createGameTrack(){
        for(int i=0; i<MAXROW; i++){
            for (int j=0; j<MAXCOLUMN; j++){
                track[j][i]=Color.ANSI_BLACK_BACKGROUND.escape()+" ";
            }
        }
        track[1][0]=Color.ANSI_GREEN.escape()+"GAMETRACK : ";
        track[3][0]=Color.ANSI_GREEN.escape()+"BASIC MODE";

        for(int h = 2;h<MAXCOLUMN; h = h+4){
            track[h][1]= Color.ANSI_GREEN.escape()+" ☠";
        }

        for (int lastLine =0; lastLine<MAXCOLUMN; lastLine++){
            track[lastLine][2] =Color.ANSI_GREEN.escape()+"﹎";
        }
    }

    public void removeSkull(int damageToken, String colorEscape,int column){
          if(damageToken!=0) {
              if (track[column][1].contains("☠")) {
                  track[column][1] = Color.ANSI_BLACK_BACKGROUND.escape()+ colorEscape + "| ▼";
                  if (damageToken == 2) {
                      track[column + 1][1] = Color.ANSI_BLACK_BACKGROUND.escape()+colorEscape + "▼";
                  }
              }

          }
    }

}
