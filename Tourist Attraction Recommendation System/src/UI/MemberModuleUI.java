package UI;

import Class.*;
import graph.*;
import java.util.List;

public class MemberModuleUI extends UI {
    public static void memberMenuUI() {
        do {
            System.out.println(underline + "Welcome to Malaysia Tourist Attraction Recommendations!" + reset);
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
                    return;
                default:
                    System.out.println("Enter 1, 2, or 0 Only!");
            }
        } while(true);
    }

    public static void stateRecommendationsUI() {
        
        do {
            Graph graph = new Graph();
            graph.loadGraph();
            
            System.out.println("Enter q to go back...");
            System.out.println(underline + "View Recommendations" + reset);
            System.out.print("Enter a state: ");
            String stateInput = sc.nextLine();

            if (stateInput.equalsIgnoreCase("q")) {
                return;
            }

            // Validate State Input
            State matchedState = State.findState(stateInput);
            if (matchedState == null) {
                System.out.println("Error: State '" + stateInput + "' not found! Please try again.");
                continue;
            }
                      
            // Query Attractions for Validated State
            String stateName = matchedState.toString();
            List<Attraction> attractions = graph.getAttractionsByState(stateName); 

            if (attractions.isEmpty()) {
                System.out.println("No attractions available in " + stateName + ".");
                continue;
            }
                       
            System.out.println("\nIn " + stateName + ", you can visit:");
            Member.viewRecommendationByState(attractions);
            
            do {
                // Prompt and Validate Selected Attraction
                System.out.print("\nEnter the attraction name you want to visit (or 'q' to cancel): ");
                String attractionInput = sc.nextLine().trim();

                if (attractionInput.equalsIgnoreCase("q")) {
                    break;
                }

                Attraction selectedAttraction = null;
                for (Attraction a : attractions) {
                    if (a.getName().equalsIgnoreCase(attractionInput)) {
                        selectedAttraction = a;
                        break;
                    }
                }

                if (selectedAttraction == null) {
                    System.out.println("Error: Attraction '" + attractionInput + "' is not listed in " + stateName + "!");
                    continue;
                }

                // Prompt and Validate User's Current Location
                System.out.print("What state are you currently in? ");
                String userLocationInput = sc.nextLine().trim();

                if (userLocationInput.equalsIgnoreCase("q")) {
                    break;
                }

                State userState = State.findState(userLocationInput);
                if (userState == null) {
                    System.out.println("Error: Current state '" + userLocationInput + "' not found!");
                    continue;
                }

                // Proceed to Location Route Processing
                String targetLocation = selectedAttraction.toString(); // e.g. Attraction, City, State format
                locationGetterUI(targetLocation, userState.toString());
                break;
            } while (true);
        } while (true);
    } 

    public static void locationGetterUI(String location, String userLocation) {
        System.out.println("In order to get to " + location + " from " + userLocation);

        // TODO: BFS Algorithm that reaches the location and a guide towards the attraction.


        System.out.println("Press any key to go back......");
        String anyKey = sc.nextLine();
        
    }

    public static void viewHistoryUI() {
        System.out.println(underline + "View History" + reset);

        // TODO
    }
}
