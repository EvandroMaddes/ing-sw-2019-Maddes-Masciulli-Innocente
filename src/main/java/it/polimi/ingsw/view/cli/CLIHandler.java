package it.polimi.ingsw.view.cli;


import it.polimi.ingsw.view.cli.graph.Color;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIHandler {
    private static final Scanner inputScanner = new Scanner(System.in);

    public static String stringPrintAndRead(String printedString){
        String returnedString = "";
        //System.out.print(Color.RESET.escape());
        System.out.flush();
        while(returnedString.isEmpty()) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+ Color.ANSI_GREEN.escape()+"\n"+printedString+"\t");
            System.out.flush();
            returnedString =stringRead();
        }
        return returnedString;
    }

    public static void arrayPrint(ArrayList<String> printedStrings){

        for (String currObject: printedStrings) {
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+currObject+"\t\t");
            System.out.flush();
        }

            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+ Color.ANSI_GREEN.escape()+"\nSelect from " +
                    "available choices:\t");
            System.out.flush();


    }

    /**
     * this method prints an array of Square and read square selected by user
     * @param X
     * @param Y
     * @return
     */
    public static int[] coordinatePrintAndRead(int X[], int Y[]){
        int returnedX = 404, returnedY = 404;
        System.out.print(Color.RESET.escape());

        for (int i =0; i<X.length;i++) {

            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+"\n"
                    +"coordinate X = "+X[i]+"\t"+"coordinate Y:"+Y[i]+"\t\t");
            System.out.flush();
        }
        while(returnedX == 404 && returnedY==404) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+ Color.ANSI_GREEN.escape()+"\n"+"Select Square:");
            System.out.flush();
            returnedX = intRead();
            returnedY = intRead();
        }
        int[] coordinateSelected = {returnedX,returnedX};
        return coordinateSelected;
    }

    /**
     * this method reads an input number
     * todo check su maxInt ammissibile, se fuori, richiedo.. MESSAGGIO TORNA 1 non 0 ??
     * @return
     */
    public static int intRead(){
        int chosenInt;

       return chosenInt = inputScanner.nextInt();


    }

    public static String stringRead(){
        String chosenString;

        return chosenString = inputScanner.nextLine();


    }

}
