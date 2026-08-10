package UI;

import Class.*;
import graph.Graph;
import java.util.List;

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
        
        Graph graph = new Graph();
        graph.loadGraph();
        
        while (true) {
            System.out.println("\nEnter 'q' to go back...");
            System.out.print("Enter a state: ");
            String stateInput = sc.nextLine().trim();

            if (stateInput.equalsIgnoreCase("q")) {
                clearScreen();
                return;
            }

            // Validate State Input
            State matchedState = findState(stateInput);
            if (matchedState == null) {
                clearScreen();
                System.out.println("Error: State '" + stateInput + "' not found! Please try again.");
                continue;
            }

            // Query Attractions for Validated State
            String stateName = matchedState.toString();
            List<Attraction> attractions = graph.getAttractionsByState(stateName);
            System.out.println(attractions); //debug           

            if (attractions.isEmpty()) {
                System.out.println("No attractions available in " + stateName + ".");
                continue;
            }

            System.out.println("\nIn " + stateName + ", you can visit:");
            Member.viewRecommendationByState(attractions);

            // Prompt and Validate Selected Attraction
            System.out.print("\nEnter the attraction name you want to visit (or 'q' to cancel): ");
            String attractionInput = sc.nextLine().trim();

            if (attractionInput.equalsIgnoreCase("q")) {
                continue;
            }

            Attraction selectedAttraction = null;
            for (Attraction a : attractions) {
                if (a.getName().equalsIgnoreCase(attractionInput)) {
                    selectedAttraction = a;
                    break;
                }
            }

            if (selectedAttraction == null) {
                clearScreen();
                System.out.println("Error: Attraction '" + attractionInput + "' is not listed in " + stateName + "!");
                continue;
            }

            // Prompt and Validate User's Current Location
            System.out.print("What state are you currently in? ");
            String userLocationInput = sc.nextLine().trim();

            State userState = findState(userLocationInput);
            if (userState == null) {
                clearScreen();
                System.out.println("Error: Current state '" + userLocationInput + "' not found!");
                continue;
            }

            // Proceed to Location Route Processing
            String targetLocation = selectedAttraction.toString(); // e.g. Attraction, City, State format
            locationGetterUI(targetLocation, userState.toString());
        }
    }

    private static State findState(String input) {
        for (State s : State.values()) {
            if (s.name().equalsIgnoreCase(input) || s.toString().equalsIgnoreCase(input)) {
                return s;
            }
        }
        return null;
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
