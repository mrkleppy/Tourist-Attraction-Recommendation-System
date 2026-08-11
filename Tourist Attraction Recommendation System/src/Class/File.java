package Class;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class File {

    public static List<City> readCityFile() {
        List<City> cities = new ArrayList<>();
        Path path = Paths.get("Data", "City.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            if (reader == null) return cities;

            String line;
            
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;               

                // Split on comma and trim whitespace
                String[] parts = line.split(",");
                if (parts.length < 2) continue;

                String cityName = parts[0].trim();
                String stateInput = parts[1].trim();
                
                if (
                    cityName.equalsIgnoreCase("cityName") || 
                    stateInput.equalsIgnoreCase("stateName")
                ) {
                    continue;
                }

                // Case-insensitive state matching
                for (State state : State.values()) {
                    if (stateInput.equalsIgnoreCase(state.toString()) || stateInput.equalsIgnoreCase(state.name())) {
                        cities.add(new City(cityName, state));
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }

    public static List<Attraction> readAttractionFile(Map<String, Attraction> attractionByName) {
        List<City> cities = readCityFile();
        List<Attraction> attractions = new ArrayList<>();
        // Fixed: pointed to Attraction.csv instead of City.csv
        Path path = Paths.get("Data", "Attraction.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            if (reader == null) return attractions;

            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                
                if (firstLine && (line.toLowerCase().startsWith("attractionname"))) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 2) continue;

                String attractionName = parts[0].trim();
                String cityName = parts[1].trim();
                
                if (
                    attractionName.equalsIgnoreCase("attractionName") || 
                    cityName.equalsIgnoreCase("cityName")
                ) {
                    continue;
                }
                
                // Find matching city
                City matchedCity = null;
                for (City city : cities) {
                    if (cityName.equalsIgnoreCase(city.getName())) {
                        matchedCity = city;
                        break;
                    }
                }

                if (matchedCity != null) {
                    Attraction attraction = new Attraction(attractionName, matchedCity);
                    attractions.add(attraction);

                    if (attractionByName != null) {
                        attractionByName.put(attractionName, attraction);
                    }
                } else {
                    System.err.println("WARNING: City '" + cityName + "' not found for attraction '" + attractionName + "'");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return attractions;
    }
    
    public static void AppendCityFile(City city) {
        Path path = Paths.get("Data", "City.csv");
        // Formats city output explicitly as "CityName,StateName"
        String contentToAppend = city.getName() + "," + city.getState();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(contentToAppend);
            writer.newLine(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void AppendAttractionFile(Attraction attraction) {
        Path path = Paths.get("Data", "Attraction.csv");
        String contentToAppend = attraction.getName() + "," + attraction.getCity().getName();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(contentToAppend);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}