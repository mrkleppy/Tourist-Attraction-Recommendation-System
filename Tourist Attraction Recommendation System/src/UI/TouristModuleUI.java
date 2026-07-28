package UI;

public class TouristModuleUI extends UI {
    public static void mainMenuUI() {

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

        // TODO: DFS Algorithm that reaches the location and a guide towards the attraction.


        System.out.println("Press any key to go back......");
        String anyKey = sc.nextLine();
    }
}
