package Class;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Admin extends User {
    
    // Default constructor
    public Admin() {
        super("", "", "admin");
    }

    // Parameterised constructor
    public Admin(String username, String password) {
        super(username, password, "admin");
    }
    
    // Add City function
    public static City addCity(String cityInput, State matchedState) {
        // Construct an object for the new city
        City newCity = new City(cityInput, matchedState);
        
        // Once constructed, append it to the City File
        File.appendCityFile(newCity);
        
        // Return the new city object
        return newCity;
    }
    
    // Remove City function
    public static boolean removeCity(List<City> cities, String cityToRemove, List<Attraction> attractions) {
        // For-each loop to identify the matching city with the city name input
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(cityToRemove)) {
                // Removes the city in the cities list in memory
                cities.remove(city);
                
                // Updates the file with the removed city
                File.overwriteCityFile(cities);
                
                // Create a temporary arraylist of every attractions
                // To remove all of the attractions that is in the removed city
                // Without this, some atraction may still not be deleted
                List<Attraction> temp = new ArrayList<>(attractions);
                
                // Removes all of the attrations that is in the city
                for (Attraction attraction : temp) {
                    if (attraction.getCity().equals(city)) {
                        // Calls the remove attraction function to remove the attraction
                        removeAttraction(attractions, attraction.getName());
                    }
                }
                
                // Once done removing, set the flag to true, notifying that the city has been successfully deleted
                return true;
            }
        }
        
        // When it fails to remove, it will return false flag
        return false;
    }
    
    public static Attraction addAttraction(String id, String attractionInput, City matchedCity) {
        // Builds the object for the newAttraction
        Attraction newAttraction = new Attraction(id, attractionInput, matchedCity);
        
        // Adds the new attraction to the file.
        File.appendAttractionFile(newAttraction);
        
        // Returns the new attraction object
        return newAttraction;
    }
    
    public static boolean removeAttraction(List<Attraction> attractions, String attractionToRemove) {
        // Use an iterator to iterate through the attractions
        Iterator<Attraction> it = attractions.iterator();
        
        // A flag to check whether an attraction has been successfully removed
        boolean removed = false;
        
        // When the iterator still has a next element, we keep executing the function.
        while (it.hasNext()) {
            // Set the temporary attraction to the next attraction.
            Attraction temporaryAttraction = it.next();

            // Checks if the temporary attraction is equal to the name or the ID
            if (temporaryAttraction.getName().equalsIgnoreCase(attractionToRemove) ||
                temporaryAttraction.getId().equalsIgnoreCase(attractionToRemove)) {
                // If yes, we remove it from the iterator and set the removed flag to true, to indicate success removal
                it.remove();
                removed = true;
                
                // Break out of the while-loop so program doesn't have to iterate through all of the attractions
                break;
            }
        }

        // If it fails to remove, return a false flag to indicate failure
        if (!removed) {
            return false;
        }

        // Reassign every attractions with a new ID according to the iterator (follows sequence in STATE.java)
        for (int i = 0; i < attractions.size(); i++) {
            // Get the attraction then set the id and increment it everytime
            attractions.get(i).setId(String.format("A%04d", i+1));
        }
        
        // Updates to the file with the new updated assigned IDs
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