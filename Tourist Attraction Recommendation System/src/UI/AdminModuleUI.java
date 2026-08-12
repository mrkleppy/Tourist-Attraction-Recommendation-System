package UI;

import Class.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminModuleUI extends UI {

    public static void adminMenuUI(List<User> users, List<City> cities) {

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
                    viewAttractionUI(cities);
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

    public static void viewAttractionUI(List<City> cities) {
        clearScreen();

        State selectedState = null;
        do {
            System.out.println(underline + "View attractions" + reset);
            
            for (State state : State.values()) {
                System.out.println(State.formatStateName(state));
            }

            System.out.print("\nEnter a state to view every attractions in it: ");
            String stateInput = sc.nextLine().trim();

            if (stateInput.equalsIgnoreCase("q")) {
                clearScreen();
                return;
            }

            selectedState = State.findState(stateInput);
            if (selectedState == null) {
                clearScreen();
                System.out.println("Error: State '" + stateInput + "' not found! Please try again.");
                continue;
            }

            break;
        } while(true);
        
        do {
            Map<String, Attraction> attractionByName = new HashMap<>();
            List<Attraction> attractions = File.readAttractionFile(attractionByName);
            
            Map<City, List<Attraction>> cityAttractions = new LinkedHashMap<>();

            // Only cities from the selected state
            for (City city : cities) {
                if (city.getState() == selectedState) {
                    cityAttractions.put(city, new ArrayList<>());
                }
            }

            // Put attractions into the matching cities
            for (Attraction attraction : attractions) {
                City attractionCity = attraction.getCity();

                for (City city : cityAttractions.keySet()) {
                    if (city.equals(attractionCity)) {
                        cityAttractions.get(city).add(attraction);
                        break;
                    }
                }
            }

            clearScreen();
            String stateName = State.formatStateName(selectedState);
            System.out.println(underline + "Attractions in " + stateName + reset + " (" + cityAttractions.size() + " cities)");

            for (Map.Entry<City, List<Attraction>> entry : cityAttractions.entrySet()) {
                City city = entry.getKey();
                List<Attraction> attractionList = entry.getValue();
                
                System.out.println(" " + underline + city.getName() + reset + " (" + attractionList.size() + " attractions)");
            
                if (attractionList.isEmpty()) {
                    System.out.println("  - No attractions available.");
                } else {
                    for (Attraction attraction : attractionList) {
                        System.out.println("  - " + attraction.getName());
                    }
                }
            }
            
            System.out.print("\nPress Enter to go back...");
            sc.nextLine();
            return;
        } while(true);
    }
}

