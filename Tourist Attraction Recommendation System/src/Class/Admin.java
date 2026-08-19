package Class;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class Admin extends User {
    public Admin() {
        super("", "", "admin");
    }

    public Admin(String username, String password) {
        super(username, password, "admin");
    }
    
    public static City addCity(String cityInput, State matchedState) {
        City city = new City(cityInput, matchedState);
        File.appendCityFile(city);
        
        return city;
    }
    
    public static boolean removeCity(List<City> cities, String cityToRemove, List<Attraction> attractions) {
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(cityToRemove)) {
                cities.remove(city);
                File.overwriteCityFile(cities);
                
                List<Attraction> temp = new ArrayList<>(attractions);
                
                for (Attraction attraction : temp) {
                    if (attraction.getCity().equals(city)) {
                        removeAttraction(attractions, attraction.getName());
                    }
                }
                
                return true;
            }
        }
        
        return false;
    }
    
    public static Attraction addAttraction(String attractionInput, City matchedCity) {
        Attraction attraction = new Attraction(attractionInput, matchedCity);
        File.appendAttractionFile(attraction);
        
        return attraction;
    }
    
    public static boolean removeAttraction(List<Attraction> attractions, String attractionToRemove) {
        for (Attraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(attractionToRemove)) {
                attractions.remove(attraction);
                File.overwriteAttractionFile(attractions);
                return true;
            }
        }
        
        return false;
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