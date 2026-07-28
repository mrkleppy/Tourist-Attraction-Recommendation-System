package UI;

import java.util.Scanner;

public abstract class UI {
    public static Scanner sc = new Scanner(System.in);
    public static String underline = "\u001b[4m";
    public static String reset = "\u001b[0m"; // Needed to remove additional text properties like underline, bold, etc

    public static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String promptRequiredInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("0")) {
                clearScreen();
                return null;
            }

            if (input.isEmpty()) {
                System.out.println("Error: input cannot be blank.\n");
                continue;
            }

            return input;
        }
    }
}
