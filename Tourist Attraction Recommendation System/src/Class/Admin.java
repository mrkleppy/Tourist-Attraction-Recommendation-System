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
     * Removes a city (matched by name, case-insensitive) from the given list,
     * along with any attractions that belong to it, then persists both
     * City.csv and Attraction.csv. Returns true if the city was found and removed.
     */
    public boolean removeCity(String cityName, List<City> cities, List<Attraction> attractions) {
        City toRemove = null;
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(cityName)) {
                toRemove = city;
                break;
            }
        }

        if (toRemove == null) {
            return false;
        }

        cities.remove(toRemove);

        List<Attraction> remaining = new ArrayList<>();
        for (Attraction attraction : attractions) {
            if (!attraction.getCity().equals(toRemove)) {
                remaining.add(attraction);
            }
        }
        attractions.clear();
        attractions.addAll(remaining);

        File.overwriteCityFile(cities);
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