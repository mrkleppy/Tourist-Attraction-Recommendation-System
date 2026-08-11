package UI;

public class MemberModuleUI extends UI {
    public static void memberMenuUI() {
        clearScreen();
        do {
            System.out.println("Welcome to Malaysia Tourist Attraction Recommendations!");
            System.out.println("\t1. View Recommendations\n\t2. View History\n\t0. Exit");
            System.out.print("Selection: ");
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    stateRecommendationsUI();
                    break;
                case "2":
                    viewHistoryUI();
                    break;
                case "0":
                    clearScreen();
                    return;
                default:
                    clearScreen();
                    System.out.println("Enter 1, 2, or 0 Only!");
            }
        } while(true);
    }

    public static void stateRecommendationsUI() {
        clearScreen();
        do {
            System.out.println("Enter q to go back...");
            System.out.print("Enter a state: ");
            String state = sc.nextLine();

            if (state.equalsIgnoreCase("q")) {
                clearScreen();
                return;
            }

            // TODO: State validation (Match if got state or not)

            

            // TODO: List all of the available attractions in that state



            System.out.print("Is there an attraction you want to go in " + state + "?:");
            String attraction = sc.nextLine();

            // TODO: Check if attraction is inside the list

            System.out.print("What state are you currently in? ");
            String userLocation = sc.nextLine();

            // TODO: State validation for userLocation



            // TODO: substring the entire location into a full variable named location, the variable will store Attraction, City, State

            String location = userLocation; // temporary
            locationGetterUI(location, userLocation);

        } while(true);
    }

    public static void locationGetterUI(String location, String userLocation) {
        clearScreen();
        System.out.println("In order to get to " + location + " from " + userLocation);

        // TODO: BFS Algorithm that reaches the location and a guide towards the attraction.


        System.out.println("Press any key to go back......");
        String anyKey = sc.nextLine();
    }

    public static void viewHistoryUI() {
        clearScreen();
        System.out.println("View History");

        // TODO
    }
}
