package it.polimi.ingsw.view.cli.graph;

/**
 * this class represents a square on map
 */
 class CLISquare {
     static final int MAXSQUARECOLUMN = 10;
    public static final int MAXSQUAREROW = 5;
    private String[][] squareString = new String[MAXSQUARECOLUMN][MAXSQUAREROW];
    private String currColorEscape;
    private boolean isSpawnSquare;


    /**
     * constructor: it set color and property of one square(spawn square or not)
     * @param color color of the square
     * @param isSpawnSquare property of spawn
     */
    CLISquare(Color color, boolean isSpawnSquare){
        this.isSpawnSquare = isSpawnSquare;
        currColorEscape = Color.ANSI_BLACK_BACKGROUND.escape()+color.escape();
        for (int i = 1; i < MAXSQUARECOLUMN; i++) {
            squareString[i][0]="─";
            for (int j = 1; j < MAXSQUAREROW-1; j++) {
                squareString[i][j] = " ";
            }
            squareString[i][MAXSQUAREROW-1]="─";
        }
        for (int i = 1; i <MAXSQUAREROW -1; i++) {
            squareString[0][i] ="│";
            for (int j = 1; j < MAXSQUARECOLUMN-1; j++) {
                squareString[j][i] = " ";
            }
            squareString[MAXSQUARECOLUMN-1][i] = "│";
        }
        squareString[0][0] = "┌";
        squareString[MAXSQUARECOLUMN-1][0] = "┐";
        squareString[0][MAXSQUAREROW-1] = "└";
        squareString[MAXSQUARECOLUMN-1][MAXSQUAREROW-1]="┘";
        for (int i = 0; i < MAXSQUARECOLUMN; i++) {
            for (int j = 0; j < MAXSQUAREROW; j++) {
                squareString[i][j] = currColorEscape + squareString[i][j];
            }
        }
    }


    /**
     * getter:
     * @return SQUARESTRING
     */
     String[][] getSquareString() {
        return squareString;
    }

    /**
     * depending on the arguments passed, this method insert a door in the right wall
     * @param isRow is true if the door must be added on a row
     * @param isLastLane is true if the door must be added on the right/bottom side of the square
     *                     (respectively if isRow is false/true)
     */
     void insertDoor(boolean isRow, boolean isLastLane){
        int constantIndex;


        String[] replacedStrings = new String[3];
        replacedStrings[0] =currColorEscape;
        replacedStrings[1] = currColorEscape;
        replacedStrings[2] = currColorEscape;
        if(isRow&&!isLastLane){
            constantIndex = 0;
            replacedStrings[0] += "┘";
            replacedStrings[1] += " ";
            replacedStrings[2] += "└";
            squareString[MAXSQUARECOLUMN/2-1][constantIndex]=replacedStrings[0];
            squareString[MAXSQUARECOLUMN/2][constantIndex]=replacedStrings[1];
            squareString[MAXSQUARECOLUMN/2+1][constantIndex]=replacedStrings[2];
        }
        else if (isRow){
            constantIndex = MAXSQUAREROW-1;
            replacedStrings[0] += "┐";
            replacedStrings[1] += " ";
            replacedStrings[2] += "┌";
            squareString[MAXSQUARECOLUMN/2-1][constantIndex]=replacedStrings[0];
            squareString[MAXSQUARECOLUMN/2][constantIndex]=replacedStrings[1];
            squareString[MAXSQUARECOLUMN/2+1][constantIndex]=replacedStrings[2];
        }
        else if (!isLastLane){
            constantIndex = 0;

            replacedStrings[0] += "┘";
            replacedStrings[1] += " ";
            replacedStrings[2] += "┐";
            squareString[constantIndex][MAXSQUAREROW/2-1]=replacedStrings[0];
            squareString[constantIndex][MAXSQUAREROW/2]=replacedStrings[1];
            squareString[constantIndex][MAXSQUAREROW/2+1]=replacedStrings[2];
        }
        else{
            constantIndex = MAXSQUARECOLUMN-1;

            replacedStrings[0] += "└";
            replacedStrings[1] += " ";
            replacedStrings[2] += "┌";
            squareString[constantIndex][MAXSQUAREROW/2-1]=replacedStrings[0];
            squareString[constantIndex][MAXSQUAREROW/2]=replacedStrings[1];
            squareString[constantIndex][MAXSQUAREROW/2+1]=replacedStrings[2];
        }
    }

    /**
     * depending on the arguments passed, this method erase the right wall between two squares of the same room
     * @param isRow is true if the door must be added on a row
     * @param isLastLane is true if the door must be added on the right/bottom side of the square
     *                     (respectively if isRow is false/true)
     */
     void eraseWall(boolean isRow, boolean isLastLane){
        int constantIndex;
        String[] replacedStrings = new String[3];

        if(isRow&&!isLastLane){
            constantIndex = 0;
            for (int i = 1; i < MAXSQUARECOLUMN-1; i++) {
                squareString[i][constantIndex] =currColorEscape + " ";
            }
            if (squareString[0][constantIndex].contains("┌")){
                squareString[0][constantIndex]=currColorEscape + "│";
            }
            else{
                squareString[0][constantIndex]=currColorEscape + " ";
            }
            if (squareString[MAXSQUARECOLUMN-1][constantIndex].contains("┐")){
                squareString[MAXSQUARECOLUMN-1][constantIndex]=currColorEscape+"│";
            }
            else{
                squareString[MAXSQUARECOLUMN-1][constantIndex]=currColorEscape+" ";
            }
            //
        }
        else if(isRow){
            constantIndex = MAXSQUAREROW-1;
            for (int i = 1; i < MAXSQUARECOLUMN-1; i++) {
                squareString[i][constantIndex] =currColorEscape + " ";
            }
            if (squareString[0][constantIndex].contains("└")){
                squareString[0][constantIndex]=currColorEscape + "│";
            }
            else{
                squareString[0][constantIndex]=currColorEscape + " ";
            }
            if (squareString[MAXSQUARECOLUMN-1][constantIndex].contains("┘")){
                squareString[MAXSQUARECOLUMN-1][constantIndex]=currColorEscape+"│";
            }
            else{
                squareString[MAXSQUARECOLUMN-1][constantIndex]=currColorEscape+" ";
            }
        }
        else if(!isLastLane){
            constantIndex = 0;
            for (int i = 1; i < MAXSQUAREROW-1; i++) {
                squareString[constantIndex][i] =currColorEscape + " ";
            }
            if (squareString[constantIndex][0].contains("┌")){
                squareString[constantIndex][0]=currColorEscape + "─";
            }
            else{
                squareString[constantIndex][0]=currColorEscape + " ";
            }
            if (squareString[constantIndex][MAXSQUAREROW-1].contains("└")){
                squareString[constantIndex][MAXSQUAREROW-1]=currColorEscape+"─";
            }
            else{
                squareString[constantIndex][MAXSQUAREROW-1]=currColorEscape+" ";
            }
        }
        else{
            constantIndex = MAXSQUARECOLUMN-1;
            for (int i = 1; i < MAXSQUAREROW-1; i++) {
                squareString[constantIndex][i] =currColorEscape + " ";
            }
            if (squareString[constantIndex][0].contains("┐")){
                squareString[constantIndex][0]=currColorEscape + "─";
            }
            else{
                squareString[constantIndex][0]=currColorEscape + " ";
            }
            if (squareString[constantIndex][MAXSQUAREROW-1].contains("┘")){
                squareString[constantIndex][MAXSQUAREROW-1]=currColorEscape+"─";
            }
            else{
                squareString[constantIndex][MAXSQUAREROW-1]=currColorEscape+" ";
            }
        }

    }
}
