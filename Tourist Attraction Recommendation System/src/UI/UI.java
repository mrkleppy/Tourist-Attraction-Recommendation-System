package UI;

import java.util.Scanner;

public abstract class UI {
    public static Scanner sc = new Scanner(System.in);
    public static String underline = "\u001b[4m";
    public static String reset = "\u001b[0m"; // Needed to remove additional text properties like underline, bold, etc
    
    public static void clearScreen(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.flush();
    }
}
