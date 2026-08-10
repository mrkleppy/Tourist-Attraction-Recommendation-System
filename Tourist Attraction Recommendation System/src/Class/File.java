package Class;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class File {
    private static BufferedReader getResourceReader(String filename) {
        // Leading '/' tells Java to search relative to the root classpath: /Data/City.csv
        String resourcePath = "/Data/" + filename;
        InputStream is = File.class.getResourceAsStream(resourcePath);

        if (is == null) {
            System.err.println("ERROR: Could not find resource at classpath: " + resourcePath);
            return null;
        }

        return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
    }

    public static List<City> readCityFile() {
        List<City> cities = new ArrayList<>();

        try (BufferedReader reader = getResourceReader("City.csv")) {
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

        try (BufferedReader reader = getResourceReader("Attraction.csv")) {
            if (reader == null) return attractions;

            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                
                if (firstLine && (line.toLowerCase().startsWith("attractionName"))) {
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
}