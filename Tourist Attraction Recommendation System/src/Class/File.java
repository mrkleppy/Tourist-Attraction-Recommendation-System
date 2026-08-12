package Class;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class File {

    private static final String CITY_FILE_PATH = "src/Data/City.csv";
    private static final String ATTRACTION_FILE_PATH = "src/Data/Attraction.csv";
    private static final String USER_FILE_PATH = "src/Data/Credential.csv";

    public static List<City> readCityFile() {
        List<City> cities = new ArrayList<>();
        Path path = Paths.get(CITY_FILE_PATH);

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

                State matchedState = State.findState(stateInput);
                if (matchedState != null) {
                    cities.add(new City(cityName, matchedState));
                } else {
                    System.err.println("WARNING: State '" + stateInput + "' not found for city '" + cityName + "'");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading city file.");
        }

        return cities;
    }

    public static List<Attraction> readAttractionFile(Map<String, Attraction> attractionByName) {
        List<City> cities = readCityFile();
        List<Attraction> attractions = new ArrayList<>();
        // Fixed: pointed to Attraction.csv instead of City.csv
        Path path = Paths.get(ATTRACTION_FILE_PATH);

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
            System.out.println("Error reading attraction file.");
        }

        return attractions;
    }
    
    public static List<User> readCredentialFile() {
        List<User> users = new ArrayList<>();
        Path path = Paths.get(USER_FILE_PATH);

        if (!Files.exists(path)) {
            System.out.println("File not found: " + path.toAbsolutePath());
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            if (reader == null) return users;

            String line;
            
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;               

                // Split on comma and trim whitespace
                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String username = parts[0].trim();
                String password = parts[1].trim();
                String role = parts[2].trim();

                if (
                    username.equalsIgnoreCase("username") && 
                    password.equalsIgnoreCase("password") &&
                    role.equalsIgnoreCase("role")
                ) {
                    continue;
                }

                if (role.equalsIgnoreCase("admin")) {
                    users.add(new Admin(username, password));
                } else if (role.equalsIgnoreCase("member")) {
                    users.add(new Member(username, password));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading credential file.");
        }

        return users;
    }

    public static void AppendCityFile(City city) {
        Path path = Paths.get(CITY_FILE_PATH);
        // Formats city output explicitly as "CityName,StateName"
        String contentToAppend = city.getName() + "," + city.getState();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(contentToAppend);
            writer.newLine(); 
        } catch (IOException e) {
            System.out.println("Error appending to city file.");
        }
    }

    public static void AppendAttractionFile(Attraction attraction) {
        Path path = Paths.get(ATTRACTION_FILE_PATH);
        String contentToAppend = attraction.getName() + "," + attraction.getCity().getName();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(contentToAppend);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error appending to attraction file.");
        }
    }

    public static void AppendCredentialFile(User user) {
        Path path = Paths.get(USER_FILE_PATH);
        String contentToAppend = user.getUsername() + "," + user.getPassword() + "," + (user instanceof Admin ? "admin" : "member");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(contentToAppend);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error appending to credential file.");
        }
    }
}