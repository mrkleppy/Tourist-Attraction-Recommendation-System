package UI;

import Class.*;

public class AdminModuleUI extends UI {

    public static void adminMenuUI() {

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
                    clearScreen();
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
        do {
            System.out.println("Add city");

            // TODO: UI and everything
            System.out.print("City name: ");
            String cityInput = sc.nextLine();
            System.out.print("State name: ");
            String stateInput = sc.nextLine();

            State matchedState = State.findState(stateInput);
            if (matchedState == null) {
                clearScreen();
                System.out.println("Error: Current state '" + stateInput + "' not found!");
                continue;
            }
            
            City city = new City(cityInput, matchedState);
            File.AppendCityFile(city);
            
            clearScreen();
            System.out.printf("%s is now in %s!\n", city.getName(), matchedState.toString());
            return;
        } while (true);
    }

    public static void removeCityUI() {
        clearScreen();
        do {
            System.out.println("Remove city");
            
            // TODO: UI and everything
            
        } while (true);
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

