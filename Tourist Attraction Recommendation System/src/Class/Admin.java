package Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin extends User {
    public Admin() {
        super("", "", "admin");
    }

    public Admin(String username, String password) {
        super(username, password, "admin");
    }

    /**
     * Removes a city (matched by name, case-insensitive) from the given list.
     * Before removing, checks whether any attractions are linked to that city;
     * if so, those attractions are removed too. Persists both City.csv and
     * Attraction.csv.
     *
     * @return the list of attractions that were removed along with the city,
     *         or {@code null} if no city with that name was found (nothing removed).
     *         An empty list means the city was found and removed, but had no
     *         linked attractions.
     */
    public List<Attraction> removeCity(String cityName, List<City> cities, List<Attraction> attractions) {
        City toRemove = null;
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(cityName)) {
                toRemove = city;
                break;
            }
        }

        if (toRemove == null) {
            return null;
        }

        // Check whether any attractions are linked to this city
        List<Attraction> linkedAttractions = new ArrayList<>();
        for (Attraction attraction : attractions) {
            if (attraction.getCity().equals(toRemove)) {
                linkedAttractions.add(attraction);
            }
        }

        // Remove the linked attractions, if any were found
        if (!linkedAttractions.isEmpty()) {
            attractions.removeAll(linkedAttractions);
        }

        cities.remove(toRemove);

        File.overwriteCityFile(cities);
        File.overwriteAttractionFile(attractions);

        return linkedAttractions;
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