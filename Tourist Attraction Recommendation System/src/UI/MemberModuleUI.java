package UI;

import Class.*;
import graph.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MemberModuleUI extends UI {

    // Member Menu UI
    public static void memberMenuUI(List<SearchHistory> searchHistories, Graph graph) {
        do {
            System.out.println(underline + "Welcome to Malaysia Tourist Attraction Recommendations!" + reset);
            System.out.println("\t1. View Recommendations\n\t2. View History\n\t0. Exit");
            System.out.print("Selection: ");
            String choice = sc.nextLine();
            
            clearScreen();
            switch (choice) {
                case "1":
                    stateRecommendationsUI(searchHistories, graph);
                    break;
                case "2":
                    viewHistoryUI(searchHistories, graph);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Enter 1, 2, or 0 Only!");
            }
        } while(true);
    }

    // State Recommendations UI
    public static void stateRecommendationsUI(List<SearchHistory> searchHistories, Graph graph) {
        do {
            System.out.println("Enter q to go back...");
            System.out.println(underline + "View Recommendations" + reset);
            System.out.print("Enter a state: ");
            String stateInput = sc.nextLine();

            if (stateInput.equalsIgnoreCase("q")) {
                clearScreen();
                return;
            }

            // Validate State Input
            State matchedState = State.findState(stateInput);
            if (matchedState == null) {
                clearScreen();
                System.out.println("Error: State '" + stateInput + "' not found! Please try again.");
                continue;
            } else {
                for (int i = 0; i < searchHistories.size(); i++) {
                    if (searchHistories.get(i).getMember().getUsername().equals(Authentication.getCurrentUser())) {
                        Member.updateHistory(searchHistories, i, matchedState);
                    }
                }
            }                        
                      
            // Query Attractions for Validated State
            String stateName = matchedState.toString();
            List<Attraction> attractions = getAllAttractionsFromState(stateName, graph);

            if (attractions.isEmpty()) {
                System.out.println("No attractions available in " + stateName + ".");
                continue;
            }
                       
            do {
                System.out.println("\nIn " + State.formatStateName(matchedState) + ", you can visit:");
                Member.viewRecommendation(attractions);
                
                // Prompt and Validate Selected Attraction
                System.out.print("\nEnter the attraction ID you want to visit (or 'q' to cancel): ");
                String attractionInput = sc.nextLine().trim();

                if (attractionInput.equalsIgnoreCase("q")) {
                    clearScreen();
                    break;
                }

                Attraction selectedAttraction = null;
                for (Attraction a : attractions) {
                    if (attractionInput.equalsIgnoreCase(a.getId())) {
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

                if (userLocationInput.equalsIgnoreCase("q")) {
                    clearScreen();
                    break;
                }

                State userState = State.findState(userLocationInput);
                if (userState == null) {
                    clearScreen();
                    System.out.println("Error: Current state '" + userLocationInput + "' not found!");
                    continue;
                }

                // Proceed to Location Route Processing
                locationGetterUI(selectedAttraction, userState, graph);
                break;
            } while (true);
        } while (true);
    } 

    // View History UI
    public static void viewHistoryUI(List<SearchHistory> searchHistories, Graph graph) {
        SearchHistory searchHistory = new SearchHistory();
        
        System.out.println(underline + "Search History" + reset);

        // Find the search history for the currently logged-in member
        for (int i = 0; i < searchHistories.size(); i++) {
            if (searchHistories.get(i).getMember().getUsername().equals(Authentication.getCurrentUser())) {
                searchHistory = searchHistories.get(i);
            }
        }

        // Get attractions based on the member's search history (With the most recent searches at the top)
        List<Attraction> attractions = new ArrayList<>();
        
        for (State state : searchHistory.getStates()) {
            attractions.addAll(getAllAttractionsFromState(state.name(), graph));
        }

        do {
            System.out.println("\nAccording to your history, you can visit:");
            Member.viewRecommendation(attractions);
            // Prompt and Validate Selected Attraction
            System.out.print("\nEnter the attraction name you want to visit (or 'q' to cancel): ");
            String attractionInput = sc.nextLine().trim();

            if (attractionInput.equalsIgnoreCase("q")) {
                clearScreen();
                break;
            }

            Attraction selectedAttraction = null;
            for (Attraction a : attractions) {
                if (a.getId().equalsIgnoreCase(attractionInput)) {
                    selectedAttraction = a;
                    break;
                }
            }

            if (selectedAttraction == null) {
                clearScreen();
                System.out.println("Error: Attraction '" + attractionInput + "' is not listed!");
                continue;
            }

            // Prompt and Validate User's Current Location
            System.out.print("What state are you currently in? ");
            String userLocationInput = sc.nextLine().trim();

            if (userLocationInput.equalsIgnoreCase("q")) {
                clearScreen();
                break;
            }

            State userState = State.findState(userLocationInput);
            if (userState == null) {
                clearScreen();
                System.out.println("Error: Current state '" + userLocationInput + "' not found!");
                continue;
            }

            // Proceed to Location Route Processing
            locationGetterUI(selectedAttraction, userState, graph);
            break;
        } while (true);
    }
    
    public static void locationGetterUI(Attraction destination, State start, Graph graph) {

        // Display the route from the user's current state to the selected attraction
        System.out.println("\nIn order to get to " + destination.toString() + " from " + State.formatStateName(start));
        
        // Find the route using the graph's findRouteToAttraction method and display it
        List<String> route = graph.findShortestPathBFS(start.name(), destination.getName());
        Member.viewRoute(route);

        System.out.println("\nPress any key to go back......");
        sc.nextLine();
        
        clearScreen();
    }
    
    // Helper method to gather all Attraction objects across all states via BFS
    private static List<Attraction> getAllAttractionsFromState(String stateName, Graph graph) {
        List<Attraction> allAttractionsFromFile = File.readAttractionFile();
        List<Attraction> attractions = new ArrayList<>();
        
        List<Vertex> attractionVertices = graph.findVerticesByTypeBFS(stateName, "ATTRACTION");
        for (Vertex v : attractionVertices) {
            // Match graph vertex name with Attraction file objects
            for (Attraction a : allAttractionsFromFile) {
                if (a.getName().equalsIgnoreCase(v.getName()) && !attractions.contains(a) && a.getCity().getState().name().equals(stateName)) {
                    attractions.add(a);
                    break;
                }
            }
        }
        return attractions;
    }
}
