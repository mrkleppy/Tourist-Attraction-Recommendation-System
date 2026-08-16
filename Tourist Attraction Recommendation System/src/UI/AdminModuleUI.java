package UI;

import Class.*;
import graph.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminModuleUI extends UI {

    public static void adminMenuUI(List<User> users) {
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
                    System.out.println("Enter 1, 2, 3, 4, 5, or 0!");
                    break;
            }

        } while(true);
    }

    public static void addCityUI() {
        do {
            System.out.println("Add city");

            // TODO: UI and everything
            System.out.print("City name: ");
            String cityInput = sc.nextLine();
            System.out.print("State name: ");
            String stateInput = sc.nextLine();

            State matchedState = State.findState(stateInput);
            if (matchedState == null) {
                System.out.println("Error: Current state '" + stateInput + "' not found!");
                continue;
            }
            
            City city = new City(cityInput, matchedState);
            File.appendCityFile(city);
            
            System.out.printf("%s is now in %s!\n", city.getName(), matchedState.toString());
            return;
        } while (true);
    }

    public static void removeCityUI() {
        do {
            System.out.println("Remove city");
            
            // TODO: UI and everything
            
        } while (true);
    }

    public static void addAttractionUI() {
        System.out.println("Add attraction");

        // TODO: UI and everything
        
    }

    public static void removeAttractionUI() {
        System.out.println("Remove attraction");

        // TODO: UI and everything

    }

    public static void viewAttractionUI() {
        Graph graph = new Graph();
        graph.loadGraph();

        do {
            System.out.println("Enter q to go back");
            System.out.println(underline + "View attractions" + reset);

            for (State state : State.values()) {
                System.out.println("- " + State.formatStateName(state));
            }

            System.out.print("\nEnter a state to view every attractions in it: ");
            String stateInput = sc.nextLine().trim();

            if (stateInput.equalsIgnoreCase("q")) {
                return;
            }

            State selectedState = State.findState(stateInput);
            if (selectedState == null) {
                System.out.println("Error: State '" + stateInput + "' not found! Please try again.");
                continue;
            }

            List<Attraction> attractions = graph.getAttractionsByState(selectedState.toString());

            String stateName = State.formatStateName(selectedState);

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
                return;
            }

        } while(true);
    }
}

