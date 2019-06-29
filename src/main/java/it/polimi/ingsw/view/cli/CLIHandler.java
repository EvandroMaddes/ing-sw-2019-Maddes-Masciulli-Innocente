package it.polimi.ingsw.view.cli;


import it.polimi.ingsw.view.cli.graph.Color;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIHandler {
    private static final Scanner inputScanner = new Scanner(System.in);

    /**
     * It prints one string and it reads from input buffer
     * @param printedString string to print
     * @return string typed
     */
    public static String stringPrintAndRead(String printedString){
        String returnedString = "";
        System.out.flush();
        while(returnedString.isEmpty()) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape()+ Color.ANSI_GREEN.escape()+"\n"+printedString+"\t");
            System.out.flush();
            returnedString =stringRead();
        }
        return returnedString;
    }


    /**
     * It print an array
     * @param printedStrings array to print
     */
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
     * @param X column
     * @param Y row
     * @return square selected
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
     * This method reads an input number
     * @return integer selected
     */
    public static int intRead(){
        try {
            int returned = inputScanner.nextInt();
            inputScanner.nextLine();
            return returned;
        } catch (Exception e){
            inputScanner.nextLine();
            return 404;
        }

    }

    /**
     * print an array list e check correct selection from user
     * @param available ArrayList to print
     * @return index of element selected
     */
    public static int arrayListPrintRead(ArrayList<String> available){
        int choice = 404;
        int i=0;
        for (String current:available
        ) {
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + current+" option "+ i);
            i++;
        }
        while(!(choice>-1 && choice<available.size())){
            System.out.flush();
            System.out.println(Color.ANSI_BLACK_BACKGROUND.escape() + Color.ANSI_GREEN.escape() + "Select one option:");
            choice=CLIHandler.intRead();
        }
        return choice;
    }

    /**
     * it reads an input string
     * @return string typed
     */
    public static String stringRead(){

        return inputScanner.nextLine();


    }

}
