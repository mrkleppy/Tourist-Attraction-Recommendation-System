package UI;

public class AdminModuleUI extends UI {

    public static void loginUI() {

        clearScreen();
        do {
            System.out.print("'q' to exit\nUsername: ");
            String username = sc.nextLine();

            if (username.equalsIgnoreCase("q")) {
                return;
            };

            // TODO: admin validation

            System.out.print("Password: ");
            String password = sc.nextLine();

            // TODO: password validation

            mainMenuUI();
        } while (true);

    }

    public static void mainMenuUI() {

        clearScreen();
        do {
            System.out.println("Admin Panel");
            System.out.println("\t1. Add a city\n\t2. Remove a city\n\t3. Create new attraction\n\t4. Remove an attraction\n\t5. View all attractions\n\t0. Exit");
            System.out.print("Selection: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    addCityUI();
                    break;
                case "2":
                    removeCityUI();
                    break;
                case "3":
                    addAttractionUI();
                    break;
                case "4":
                    removeAttractionUI();
                    break;
                case "5":
                    viewAttractionUI();
                    break;
                case "0":
                    return;
                default:
                    clearScreen();
                    System.out.println("Enter 1, 2, 3, 4, 5, or 0!");
                    break;
            }

        } while(true);
    }

    public static void addCityUI() {
        clearScreen();
        System.out.println("Add city");

        // TODO: UI and everything

    }

    public static void removeCityUI() {
        clearScreen();
        System.out.println("Remove city");

        // TODO: UI and everything

    }

    public static void addAttractionUI() {
        clearScreen();
        System.out.println("Add attraction");

        // TODO: UI and everything
        
    }

    public static void removeAttractionUI() {
        clearScreen();
        System.out.println("Remove attraction");

        // TODO: UI and everything

    }

    public static void viewAttractionUI() {
        clearScreen();
        System.out.println("View attraction");

        // TODO: UI and everything

    }
}

