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
            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+currObject+"\t\t");
            System.out.flush();
        }

            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+ Color.ANSI_GREEN.escape()+"\nSelect from " +
                    "available choices:[type name]\t");
            System.out.flush();

    }

    /**
     * this method prints an array of Square and read square selected by user
     * @param X
     * @param Y
     * @return
     */
    public static int[] coordinatePrintAndRead(int X[], int Y[]){
        int index = 404;
        System.out.print(Color.RESET.escape());

        for (int i =0; i<X.length;i++) {

            System.out.print(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_CYAN.escape()+"\n"
                    +"Row:"+X[i]+" Column:"+Y[i]+" option "+i);
            System.out.flush();
        }
        while(index==404) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+ Color.ANSI_GREEN.escape()+"\n"+"Select Square:[option number]");
            System.out.flush();
             index = intRead();
             if(index<0 || index>=X.length){
                 System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+Color.ANSI_GREEN.escape()+"Select one choice available");
                 index=404;
             }

        }
        int[] coordinateSelected = {X[index],Y[index]};
        return coordinateSelected;
    }

    /**
     * this method reads an input number, the
     *
     * @return
     */
    public static int intRead(){
        try {
            return inputScanner.nextInt();
        } catch (Exception e){
            inputScanner.nextLine();
            return 404;
        }

    }

    public static String stringRead(){
        String chosenString;

        return chosenString = inputScanner.nextLine();


    }

}
