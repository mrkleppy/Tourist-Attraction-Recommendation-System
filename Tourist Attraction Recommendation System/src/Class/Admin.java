package Class;

import java.util.List;
import java.util.Objects;

public class Admin extends User {
 
    public Admin(String username, String password) {
        super(username, password);
    }
 
    /**
     * Removes the given city from the state's list of cities, if present.
     * Returns true if a city was removed, false if it wasn't found.
     */
    public boolean removeCity(State state, City city) {
        return removeCity(state, city.getName());
    }
 
    /**
     * Removes the first city with a matching name (case-insensitive)
     * from the state's list of cities.
     */
    public boolean removeCity(State state, String cityName) {
        City[] cities = state.getCities();
        if (cities == null || cities.length == 0) {
            return false;
        }
 
        int indexToRemove = -1;
        for (int i = 0; i < cities.length; i++) {
            if (cities[i].getName().equalsIgnoreCase(cityName)) {
                indexToRemove = i;
                break;
            }
        }
 
        if (indexToRemove == -1) {
            return false;
        }
 
        City[] updated = new City[cities.length - 1];
        int j = 0;
        for (int i = 0; i < cities.length; i++) {
            if (i != indexToRemove) {
                updated[j++] = cities[i];
            }
        }
 
        state.setCities(updated);
        return true;
    }
 
    @Override
    public String getRole() {
        return "Admin";
    }
}
    
    @Override
    public int hashCode() {
        return Objects.hash(this.getUsername());
    }
}
