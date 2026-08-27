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

<<<<<<< Updated upstream
    public static void adminMenuUI(List<User> users) {
=======
    // Main menu for Admin Panel
    public static void adminMenuUI(Graph graph) {
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    public static void addCityUI() {
=======
    // Add City UI
    public static void addCityUI(Graph graph) {
>>>>>>> Stashed changes
        do {
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
                continue;
            }
            
            System.out.print("State name: ");
            String stateInput = sc.nextLine();

            clearScreen();
            State matchedState = State.findState(stateInput);
            if (matchedState == null) {
                System.out.println("Error: Current state '" + stateInput + "' not found!");               
            } else {
<<<<<<< Updated upstream
                City city = Admin.addCity(cityInput, matchedState);
=======
                // If the state is found, add the city to the file and display a success message
                City city = Admin.addCity(cityInput, matchedState, graph);
>>>>>>> Stashed changes
                System.out.printf("%s is now in %s!\n", city.getName(), matchedState.toString());
                return;
            }
        } while (true);
    }

<<<<<<< Updated upstream
    public static void removeCityUI() {
        List<City> cities = File.readCityFile();
        
        Graph graph = new Graph();
        graph.loadGraph();
        
        List<Attraction> attractions = new ArrayList<>();
        
        for (State state : State.values()) {
            attractions.addAll(graph.getAttractionsByState(state.name()));
        }
=======
    // Remove City UI
    public static void removeCityUI(Graph graph) {

        // Load the list of cities from the file
        List<City> cities = File.readCityFile();        
        List<Attraction> attractions = getAllAttractionsViaGraph(graph);
>>>>>>> Stashed changes
        
        do {
            System.out.print("Enter an city to remove: ");
            String cityToRemove = sc.nextLine();
            
            clearScreen();
            if (cityToRemove.equalsIgnoreCase("q")) {
                return;
            } 
            
<<<<<<< Updated upstream
          
            if (Admin.removeCity(cities, cityToRemove, attractions)) {
=======
            // If the city is found and removed successfully, display a success message; otherwise, display an error message
            if (Admin.removeCity(cities, cityToRemove, attractions, graph)) {
>>>>>>> Stashed changes
                System.out.println("City " + cityToRemove + " is now removed!");
                return;
            } else {
                System.out.println("City name not found!");
            }
        } while (true);
    }

<<<<<<< Updated upstream
    public static void addAttractionUI() {
        Graph graph = new Graph();
        graph.loadGraph();
        
        List<Attraction> attractions = new ArrayList<>();
                
        for (State state : State.values()) {
            attractions.addAll(graph.getAttractionsByState(state.name()));
        }
=======
    // Add Attraction UI
    public static void addAttractionUI(Graph graph) {
        List<Attraction> attractions = getAllAttractionsViaGraph(graph);
>>>>>>> Stashed changes
        
        do {
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
                continue;
            }
            
            System.out.print("City name: ");
            String cityInput = sc.nextLine();

            clearScreen();
            City matchedCity = City.findCity(cityInput);
            if (matchedCity == null) {
                System.out.println("Error: Current city " + cityInput + " not found!");
            } else {
                String id = Attraction.generateNextAttractionId();
<<<<<<< Updated upstream
                
                Attraction attraction = Admin.addAttraction(id, attractionInput, matchedCity);
=======
                Attraction attraction = Admin.addAttraction(id, attractionInput, matchedCity, graph);
>>>>>>> Stashed changes
                System.out.printf("%s is now in %s!\n", attraction.getName(), matchedCity.getName());
                return;   
            }
        } while (true);
        
    }

<<<<<<< Updated upstream
    public static void removeAttractionUI() {
        Graph graph = new Graph();
        graph.loadGraph();
        
        List<Attraction> attractions = new ArrayList<>();
                
        for (State state : State.values()) {
            attractions.addAll(graph.getAttractionsByState(state.name()));
        }
=======
    // Remove Attraction UI
    public static void removeAttractionUI(Graph graph) {
        List<Attraction> attractions = getAllAttractionsViaGraph(graph);
>>>>>>> Stashed changes
        
        do {
            System.out.print("Enter an attraction to remove: ");
            String attractionToRemove = sc.nextLine();
            
            clearScreen();
            if (attractionToRemove.equalsIgnoreCase("q")) {
                return;
            } 
            
<<<<<<< Updated upstream
            clearScreen();
            if (Admin.removeAttraction(attractions, attractionToRemove)) {
=======
            // If the attraction is found and removed successfully, display a success message; otherwise, display an error message
            if (Admin.removeAttraction(attractions, attractionToRemove, graph)) {
>>>>>>> Stashed changes
                System.out.println("Attraction " + attractionToRemove + " is now removed!");
                return;
            } else {
                System.out.println("Attraction name not found!");
            }
        } while (true);
    }

<<<<<<< Updated upstream
    public static void viewAttractionUI() {
        Graph graph = new Graph();
        graph.loadGraph();

=======
    // View Attraction UI
    public static void viewAttractionUI(Graph graph) {
>>>>>>> Stashed changes
        do {
            System.out.println(underline + "View attractions" + reset);

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
                clearScreen();
                System.out.println("Error: State '" + stateInput + "' not found! Please try again.");
                continue;
            }
<<<<<<< Updated upstream

            List<Attraction> attractions = graph.getAttractionsByState(selectedState.toString());
=======
            
            // Get the list of attractions for the selected state
            List<Attraction> attractions = getAllAttractionsFromState(selectedState.name(), graph);
>>>>>>> Stashed changes

            String stateName = State.formatStateName(selectedState);

            clearScreen();
            if (attractions.isEmpty()) {
                System.out.println(underline + "Attractions in " + stateName + reset);
                System.out.println("\nNo attractions available in " + stateName + ".");
            } else {
                Map<String, List<Attraction>> cityAttractions = new LinkedHashMap<>();

                for (Attraction attraction : attractions) {
                    String cityName = attraction.getCity().getName();
                    cityAttractions.putIfAbsent(cityName, new ArrayList<>());
                    cityAttractions.get(cityName).add(attraction);
                }

                System.out.println(underline + "Attractions in " + stateName + reset + " (" + cityAttractions.size() + " cities)");

                for (Map.Entry<String, List<Attraction>> entry : cityAttractions.entrySet()) {
                    String cityName = entry.getKey();
                    List<Attraction> attractionList = entry.getValue();

                    System.out.println(" " + underline + cityName + reset + " (" + attractionList.size() + " attractions)");
                    for (Attraction attraction : attractionList) {
                        System.out.println("  - " + attraction.getName());
                    }

                    System.out.println();
                }
            }

            System.out.print("\nPress Enter to view another state, or type q to go back: ");
            String choice = sc.nextLine().trim();

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

