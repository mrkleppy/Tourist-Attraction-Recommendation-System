package UI;

import Class.*;
import graph.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class AdminModuleUI extends UI {

    // Main menu for Admin Panel
    public static void adminMenuUI(Graph graph) {
        do {
            System.out.println("Admin Panel");
            System.out.println("\t1. Add a city\n\t2. Remove a city\n\t3. Create new attraction\n\t4. Remove an attraction\n\t5. View all attractions\n\t0. Exit");
            System.out.print("Selection: ");
            String choice = sc.nextLine();

            clearScreen();
            switch (choice) {
                case "1":
                    addCityUI(graph);
                    break;
                case "2":
                    removeCityUI(graph);
                    break;
                case "3":
                    addAttractionUI(graph);
                    break;
                case "4":
                    removeAttractionUI(graph);
                    break;
                case "5":
                    viewAttractionUI(graph);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Enter 1, 2, 3, 4, 5, or 0!");
                    break;
            }

        } while(true);
    }

    // Add City UI
    public static void addCityUI(Graph graph) {
        do {
            System.out.println("Enter 'q' to go back to the previous menu.");
            System.out.println("Add city");
            System.out.print("City name: ");
            String cityInput = sc.nextLine();
            
            if (cityInput.equalsIgnoreCase("q")) {
                clearScreen();
                return;
            }
            
            if (cityInput.isEmpty()) {
                clearScreen();
                System.out.println("City name cannot be empty!");
                continue;
            }
            
            // Check for duplicate city names
            boolean duplicate = false;
            List <City> cities = File.readCityFile();
            for (City city : cities) {
                if (cityInput.equalsIgnoreCase(city.getName())) {
                    clearScreen();
                    System.out.println("Error: City is already added!");
                    duplicate = true;
                    break;
                }
            }
            
            if (duplicate) {
                clearScreen();
                continue;
            }
            
            // If there is no duplicate city names, prompt the user to enter the state name for the city
            System.out.print("State name: ");
            String stateInput = sc.nextLine();

            clearScreen();
            State matchedState = State.findState(stateInput);
            if (matchedState == null) {
                // If the state is not found, display an error message and prompt the user to try again
                System.out.println("Error: Current state '" + stateInput + "' not found!");               
            } else {
                // If the state is found, add the city to the file and display a success message
                City city = Admin.addCity(cityInput, matchedState, graph);
                System.out.printf("%s is now in %s!\n", city.getName(), matchedState.toString());
                return;
            }
        } while (true);
    }

    // Remove City UI
    public static void removeCityUI(Graph graph) {

        // Load the list of cities from the file
        List<City> cities = File.readCityFile();        
        List<Attraction> attractions = getAllAttractionsViaGraph(graph);
        
        do {
            System.out.println("Enter 'q' to go back to the previous menu.");
            System.out.print("Enter an city to remove: ");
            String cityToRemove = sc.nextLine();
            
            clearScreen();
            if (cityToRemove.equalsIgnoreCase("q")) {
                return;
            } 
            
            // If the city is found and removed successfully, display a success message; otherwise, display an error message
            if (Admin.removeCity(cities, cityToRemove, attractions, graph)) {
                System.out.println("City " + cityToRemove + " is now removed!");
                return;
            } else {
                System.out.println("City name not found!");
            }
        } while (true);
    }

    // Add Attraction UI
    public static void addAttractionUI(Graph graph) {
        List<Attraction> attractions = getAllAttractionsViaGraph(graph);
        
        do {
            System.out.println("Enter 'q' to go back to the previous menu.");
            System.out.println("Add attraction");
            System.out.print("Attraction name: ");
            String attractionInput = sc.nextLine();
            
            if (attractionInput.equalsIgnoreCase("q")) {
                clearScreen();
                return;
            }
            
            if (attractionInput.isEmpty()) {
                clearScreen();
                System.out.println("Attraction name cannot be empty!");
                continue;
            }
            
            // Check for duplicate attraction names
            boolean duplicate = false;
            for (Attraction attraction : attractions) {
                if (attractionInput.equalsIgnoreCase(attraction.getName())) {
                    clearScreen();
                    System.out.println("Error: Attraction is already added!");
                    duplicate = true;
                    break;
                }
            }
            
            if (duplicate) {
                clearScreen();
                continue;
            }
            
            // If there is no duplicate attraction names, prompt the user to enter the city name for the attraction
            System.out.print("City name: ");
            String cityInput = sc.nextLine();

            clearScreen();
            City matchedCity = City.findCity(cityInput);
            if (matchedCity == null) {
                // If the city is not found, display an error message and prompt the user to try again
                System.out.println("Error: Current city " + cityInput + " not found!");
            } else {
                // If the city is found, generate a new attraction ID, add the attraction to the file, and display a success message
                String id = Attraction.generateNextAttractionId();
                Attraction attraction = Admin.addAttraction(id, attractionInput, matchedCity, graph);
                System.out.printf("%s is now in %s!\n", attraction.getName(), matchedCity.getName());
                return;   
            }
        } while (true);
    }

    // Remove Attraction UI
    public static void removeAttractionUI(Graph graph) {
        List<Attraction> attractions = getAllAttractionsViaGraph(graph);
        
        do {
            System.out.println("Enter 'q' to go back to the previous menu.");
            System.out.print("Enter an attraction to remove: ");
            String attractionToRemove = sc.nextLine();
            
            clearScreen();  
            if (attractionToRemove.equalsIgnoreCase("q")) {
                return;
            } 
            
            // If the attraction is found and removed successfully, display a success message; otherwise, display an error message
            if (Admin.removeAttraction(attractions, attractionToRemove, graph)) {
                System.out.println("Attraction " + attractionToRemove + " is now removed!");
                return;
            } else {
                System.out.println("Attraction name not found!");
            }
        } while (true);
    }

    // View Attraction UI
    public static void viewAttractionUI(Graph graph) {
        do {
            System.out.println("Enter 'q' to go back to the previous menu.\n");
            System.out.println(underline + "View attractions" + reset);

            // Display all formatted state names for the user to choose from
            for (State state : State.values()) {
                System.out.println("- " + State.formatStateName(state));
            }

            System.out.print("\nEnter a state to view every attractions in it: ");
            String stateInput = sc.nextLine().trim();

            if (stateInput.equalsIgnoreCase("q")) {
                clearScreen();
                return;
            }

            State selectedState = State.findState(stateInput);
            if (selectedState == null) {
                // If the state is not found, display an error message and prompt the user to try again
                clearScreen();
                System.out.println("Error: State '" + stateInput + "' not found! Please try again.");
                continue;
            }
            
            // Get the list of attractions for the selected state
            List<Attraction> attractions = getAllAttractionsFromState(selectedState.name(), graph);

            // Format the state name for display purposes
            String stateName = State.formatStateName(selectedState);

            if (attractions.isEmpty()) {
                // If there are no attractions in the selected state, display a message indicating that there are no attractions available
                System.out.println(underline + "Attractions in " + stateName + reset);
                System.out.println("\nNo attractions available in " + stateName + ".");
            } else {
                // If there are attractions in the selected state, display the list of attractions grouped by city
                Map<String, List<Attraction>> cityAttractions = new LinkedHashMap<>();

                // Group attractions by city
                for (Attraction attraction : attractions) {
                    String cityName = attraction.getCity().getName();
                    cityAttractions.putIfAbsent(cityName, new ArrayList<>());
                    cityAttractions.get(cityName).add(attraction);
                }

                // Display the attractions grouped by city and the number of cities in that state
                System.out.println(underline + "Attractions in " + stateName + reset + " (" + cityAttractions.size() + " cities)");

                // Iterate through the map of city attractions and print the details for each city and its attractions
                for (Map.Entry<String, List<Attraction>> entry : cityAttractions.entrySet()) {
                    // Get the city name and the list of attractions for that city
                    String cityName = entry.getKey();
                    List<Attraction> attractionList = entry.getValue();

                    // Print the city name and the number of attractions in that city, followed by the list of attractions for that city
                    System.out.println(" " + underline + cityName + reset + " (" + attractionList.size() + " attractions)");
                    for (Attraction attraction : attractionList) {
                        // Print the attraction name for each attraction in the list of attractions for that city
                        System.out.println("  - " + attraction.getName());
                    }

                    System.out.println();
                }
            }

            System.out.print("\nPress Enter to view another state, or type q to go back: ");
            String choice = sc.nextLine().trim();

            // If the user types 'q', return to the previous menu; otherwise, continue the loop to allow the user to view another state
            if (choice.equalsIgnoreCase("q")) {
                clearScreen();
                return;
            }
        } while(true);
    }
    
    // Helper method to gather all Attraction objects across all states via BFS
    private static List<Attraction> getAllAttractionsViaGraph(Graph graph) {
        List<Attraction> allAttractionsFromFile = File.readAttractionFile();
        Set<Attraction> attractionSet = new LinkedHashSet<>();
        List<Attraction> attractions = new ArrayList<>();
        
        for (State state : State.values()) {
            List<Vertex> attractionVertices = graph.findVerticesByTypeBFS(state.name(), "ATTRACTION");
            for (Vertex v : attractionVertices) {
                // Match graph vertex name with Attraction file objects
                for (Attraction a : allAttractionsFromFile) {
                    if (a.getName().equalsIgnoreCase(v.getName()) && !attractions.contains(a)) {
                        attractionSet.add(a);
                        break;
                    }
                }
            }
        }
        
        attractions.addAll(attractionSet);
        return attractions;
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

