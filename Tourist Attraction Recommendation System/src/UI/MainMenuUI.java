package UI;

public class MainMenuUI extends UI {
    public static void mainMenuUI() {

        clearScreen();
        do {
            System.out.println("Welcome to Malaysia Tourist Attraction Recommendations!");
            System.out.println("\t1. View Recommendations\n\t2. Login as Admin\n\t0. Exit");
            System.out.print("Selection: ");
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    TouristModuleUI.mainMenuUI();
                    break;
                case "2":
                    AdminModuleUI.loginUI();
                    break;
                case "0":
                    clearScreen();
                    System.out.println("Bye, Have a nice trip!");
                    System.exit(0);
                    break;
                default:
                    clearScreen();
                    System.out.println("Enter 1, 2, or 0 Only!");
            }
        } while(true);
    }
}
