package Class;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import graph.*;

public class Admin extends User {
    public Admin() {
        super("", "", "admin");
    }

    public Admin(String username, String password) {
        super(username, password, "admin");
    }
    
<<<<<<< Updated upstream
    public static City addCity(String cityInput, State matchedState) {
        City city = new City(cityInput, matchedState);
        File.appendCityFile(city);
        
        return city;
    }
    
    public static boolean removeCity(List<City> cities, String cityToRemove, List<Attraction> attractions) {
=======
    // Add City function
    public static City addCity(String cityInput, State matchedState, Graph graph) {
        // Construct an object for the new city
        City newCity = new City(cityInput, matchedState);
        
        // Once constructed, append it to the City File
        File.appendCityFile(newCity);
        
        // Update the graph
        if (graph != null) {
            graph.addVertex(newCity.getName(), "CITY");
            graph.addEdge(newCity.getName(), "CITY", matchedState.name(), "STATE");
        }
        
        // Return the new city object
        return newCity;
    }
    
    // Remove City function
    public static boolean removeCity(List<City> cities, String cityToRemove, List<Attraction> attractions, Graph graph) {
        // For-each loop to identify the matching city with the city name input
>>>>>>> Stashed changes
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(cityToRemove)) {
                cities.remove(city);
                File.overwriteCityFile(cities);
                
<<<<<<< Updated upstream
=======
                // Update in the graph
                if (graph != null) {
                    graph.removeVertex(city.getName());
                }
                
                // Create a temporary arraylist of every attractions
                // To remove all of the attractions that is in the removed city
                // Without this, some atraction may still not be deleted
>>>>>>> Stashed changes
                List<Attraction> temp = new ArrayList<>(attractions);
                
                for (Attraction attraction : temp) {
                    if (attraction.getCity().equals(city)) {
<<<<<<< Updated upstream
                        removeAttraction(attractions, attraction.getName());
=======
                        // Calls the remove attraction function to remove the attraction
                        removeAttraction(attractions, attraction.getName(), graph);
>>>>>>> Stashed changes
                    }
                }
                
                return true;
            }
        }
        
        return false;
    }
    
<<<<<<< Updated upstream
    public static Attraction addAttraction(String id, String attractionInput, City matchedCity) {
        Attraction attraction = new Attraction(id, attractionInput, matchedCity);
        File.appendAttractionFile(attraction);
        
        return attraction;
    }
    
    public static boolean removeAttraction(List<Attraction> attractions, String attractionToRemove) {
=======
    public static Attraction addAttraction(String id, String attractionInput, City matchedCity, Graph graph) {
        // Builds the object for the newAttraction
        Attraction newAttraction = new Attraction(id, attractionInput, matchedCity);
        
        // Adds the new attraction to the file.
        File.appendAttractionFile(newAttraction);
        
        // update in graph
        if (graph != null) {
            graph.addVertex(newAttraction.getName(), "ATTRACTION");
            graph.addEdge(newAttraction.getName(), "ATTRACTION", matchedCity.getName(), "CITY");
        }
        
        // Returns the new attraction object
        return newAttraction;
    }
    
    public static boolean removeAttraction(List<Attraction> attractions, String attractionToRemove, Graph graph) {
        // Use an iterator to iterate through the attractions
>>>>>>> Stashed changes
        Iterator<Attraction> it = attractions.iterator();
        boolean removed = false;
        
<<<<<<< Updated upstream
=======
        String removedAttractionName = null;
        
        // When the iterator still has a next element, we keep executing the function.
>>>>>>> Stashed changes
        while (it.hasNext()) {
            Attraction attraction = it.next();

<<<<<<< Updated upstream
            if (attraction.getName().equalsIgnoreCase(attractionToRemove) ||
                attraction.getId().equalsIgnoreCase(attractionToRemove)) {
=======
            // Checks if the temporary attraction is equal to the name or the ID
            if (temporaryAttraction.getName().equalsIgnoreCase(attractionToRemove) ||
                temporaryAttraction.getId().equalsIgnoreCase(attractionToRemove)) {
                // If yes, we remove it from the iterator and set the removed flag to true, to indicate success removal
                removedAttractionName = temporaryAttraction.getName();
>>>>>>> Stashed changes
                it.remove();
                removed = true;
                break;
            }
        }

        if (!removed) {
            return false;
        }
<<<<<<< Updated upstream

=======
        
        // Update in graph
        if (graph != null && removedAttractionName != null) {
            graph.removeVertex(removedAttractionName);
        }
        
        // Reassign every attractions with a new ID according to the iterator (follows sequence in STATE.java)
>>>>>>> Stashed changes
        for (int i = 0; i < attractions.size(); i++) {
            attractions.get(i).setId(String.format("A%04d", i+1));
        }
        
        File.overwriteAttractionFile(attractions);
        return true;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        
        Admin admin = (Admin)obj;
        return this.getUsername().equals(admin.getUsername());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.getUsername());
    }
}