package UI;

import Class.*;
import graph.*;
import java.util.List;

public class MemberModuleUI extends UI {
    public static void memberMenuUI(List<SearchHistory> searchHistories) {
        do {
            System.out.println(underline + "Welcome to Malaysia Tourist Attraction Recommendations!" + reset);
            System.out.println("\t1. View Recommendations\n\t2. View History\n\t0. Exit");
            System.out.print("Selection: ");
            String choice = sc.nextLine();
            
            clearScreen();
            switch (choice) {
                case "1":
                    stateRecommendationsUI(searchHistories);
                    break;
                case "2":
                    viewHistoryUI(searchHistories);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Enter 1, 2, or 0 Only!");
            }
        } while(true);
    }

    public static void stateRecommendationsUI(List<SearchHistory> searchHistories) {
        Graph graph = new Graph();
        graph.loadGraph();
            
        do {
            System.out.println("Enter q to go back...");
            System.out.println(underline + "View Recommendations" + reset);
            System.out.print("Enter a state: ");
            String stateInput = sc.nextLine();

            clearScreen();
            if (stateInput.equalsIgnoreCase("q")) {
                clearScreen();
                return;
            }

            // Validate State Input
            State matchedState = State.findState(stateInput);
            if (matchedState == null) {
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
            List<Attraction> attractions = graph.getAttractionsByState(stateName); 

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

                clearScreen();
                if (userLocationInput.equalsIgnoreCase("q")) {
                    break;
                }

                State userState = State.findState(userLocationInput);
                if (userState == null) {
                    System.out.println("Error: Current state '" + userLocationInput + "' not found!");
                    continue;
                }

                // Proceed to Location Route Processing
                locationGetterUI(selectedAttraction, userState, graph);
                break;
            } while (true);
        } while (true);
    } 

    public static void viewHistoryUI(List<SearchHistory> searchHistories) {
        Graph graph = new Graph();
        graph.loadGraph();
        SearchHistory searchHistory = new SearchHistory();
        
        System.out.println(underline + "Search History" + reset);

        for (int i = 0; i < searchHistories.size(); i++) {
            if (searchHistories.get(i).getMember().getUsername().equals(Authentication.getCurrentUser())) {
                searchHistory = searchHistories.get(i);
            }
        }

        List<Attraction> attractions = graph.getAttractionsByHistory(searchHistory);

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
                if (a.getName().equalsIgnoreCase(attractionInput)) {
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

            clearScreen();
            if (userLocationInput.equalsIgnoreCase("q")) {
                break;
            }

            State userState = State.findState(userLocationInput);
            if (userState == null) {
                System.out.println("Error: Current state '" + userLocationInput + "' not found!");
                continue;
            }

            // Proceed to Location Route Processing
            locationGetterUI(selectedAttraction, userState, graph);
            break;
        } while (true);
    }
    
    public static void locationGetterUI(Attraction destination, State start, Graph graph) {
        System.out.println("In order to get to " + destination.toString() + " from " + State.formatStateName(start));
        
        List<String> route = graph.findRouteToAttraction(destination.getName(), start.name());
        Member.viewRoute(route);

        System.out.println("\nPress any key to go back......");
        sc.nextLine();
        
        clearScreen();
    }
}
