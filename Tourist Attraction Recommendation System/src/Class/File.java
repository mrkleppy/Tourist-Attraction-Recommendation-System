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
    private static final String SEARCHHISTORY_FILE_PATH = "src/Data/History.csv";    
    
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
                if (parts.length < 3) continue;

                String id = parts[0].trim();
                String attractionName = parts[1].trim();
                String cityName = parts[2].trim();
                
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
                    Attraction attraction = new Attraction(id, attractionName, matchedCity);
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
    
    public static List<SearchHistory> readSearchHistoryFile() {
        List<SearchHistory> searchHistories = new ArrayList<>();
        Path path = Paths.get(SEARCHHISTORY_FILE_PATH);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;               

                String[] parts = line.split(",");

                // Ensure row has at least username + at least 1 state
                if (parts.length < 2) continue;

                String username = parts[0].trim();
                String firstState = parts[1].trim();

                // Header check: Skip the header row ("username,states...")
                if (username.equalsIgnoreCase("username") && firstState.equalsIgnoreCase("states")) {
                    continue;
                }

                Member member = new Member();
                member.setUsername(username);

                // Fresh list for each row so states don't leak between users
                List<State> states = new ArrayList<>();

                // Loop from index 1 through the last element (parts.length)
                for (int i = 1; i < parts.length; i++) {               
                    State state = State.findState(parts[i].trim());
                    if (state != null) {
                        states.add(state);
                    }
                }

                SearchHistory searchHistory = new SearchHistory(member, (ArrayList<State>)states);     
                searchHistories.add(searchHistory);
            }
        } catch (IOException e) {
            System.err.println("Error reading search history file: " + e.getMessage());
        }

        return searchHistories;
    }

    public static void appendCityFile(City city) {
        Path path = Paths.get(CITY_FILE_PATH);
        // Formats city output explicitly as "CityName, StateName"
        String contentToAppend = city.getName() + ", " + city.getState();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(contentToAppend);
            writer.newLine(); 
        } catch (IOException e) {
            System.out.println("Error appending to city file.");
        }
    }

    public static void appendAttractionFile(Attraction attraction) {
        Path path = Paths.get(ATTRACTION_FILE_PATH);
        String contentToAppend = attraction.getId() + ", " +attraction.getName() + ", " + attraction.getCity().getName();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(contentToAppend);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error appending to attraction file.");
        }
    }

    public static void appendCredentialFile(User user) {
        Path path = Paths.get(USER_FILE_PATH);
        String contentToAppend = user.getUsername() + "," + user.getPassword() + "," + (user instanceof Admin ? "admin" : "member");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(contentToAppend);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error appending to credential file.");
        }
    }
    
    public static void appendSearchHistoryFile(SearchHistory searchHistory) {
        Path path = Paths.get(SEARCHHISTORY_FILE_PATH);
        
        String contentToAppend = searchHistory.getMember().getUsername();
        ArrayList<State> states = searchHistory.getStates();
        
        for (int i = 0; i < states.size(); i++) {
            contentToAppend += ("," + states.get(i).name());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(contentToAppend);
            writer.newLine(); 
        } catch (IOException e) {
            System.out.println("Error appending to search history file.");
        }
    }
    
    public static void overwriteCityFile(List<City> cities) {
        Path path = Paths.get(CITY_FILE_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            // Write header
            writer.write("CityName, StateName");
            writer.newLine();

            // Write data rows
            for (City city : cities) {
                String cityName = city.getName();
                String stateName = city.getState().name();

                String line = cityName + ", " + stateName;

                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to city file: " + e.getMessage());
        }
    }
    
    public static void overwriteAttractionFile(List<Attraction> attractions) {
        Path path = Paths.get(ATTRACTION_FILE_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            // Write header
            writer.write("ID, AttractionName, CityName");
            writer.newLine();

            // Write data rows
            for (Attraction attraction : attractions) {
                String id = attraction.getId();
                String attractionName = attraction.getName();
                String cityName = attraction.getCity().getName();

                String line = id + ", " + attractionName + ", " + cityName;

                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to attraction file: " + e.getMessage());
        }
    }
    
    public static void overwriteSearchHistoryFile(List<SearchHistory> searchHistories) {
        Path path = Paths.get(SEARCHHISTORY_FILE_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            // Write header
            writer.write("Username, States");
            writer.newLine();

            // Write data rows
            for (SearchHistory searchHistory : searchHistories) {
                String username = searchHistory.getMember().getUsername();
                List<State> states = searchHistory.getStates();

                StringBuilder line = new StringBuilder(username);
                for (int i = 0; i < states.size(); i++) {
                    line.append(',').append(states.get(i).name());
                }

                writer.write(line.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to search history file: " + e.getMessage());
        }
    }
}