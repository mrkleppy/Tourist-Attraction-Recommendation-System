package Class;

import java.util.Objects;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Attraction {
    private String id;
    private String name;
    private City city;

    public Attraction(){
        this.id = "";
        this.name = "";
        this.city = null;
    }

    public Attraction(String id, String name, City city){
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public City getCity(){
        return this.city;
    }

    public void setCity(City city){
        this.city = city;
    }
    
    public static String generateNextAttractionId() {
        Map<String, Attraction> attractionByName = new HashMap<>();
        List<Attraction> attractions = File.readAttractionFile(attractionByName);
    
        int maxId = 0;

        for (Attraction attraction : attractions) {
            String id = attraction.getId();

            if (id != null && id.matches("A\\d{4}")) {
                int num = Integer.parseInt(id.substring(1)); // Removes the 'A' at the start
            
                if (num > maxId) {
                    maxId = num;
                }
            }
        }

        return String.format("A%04d", maxId + 1);
    }

    @Override
    public String toString() {
        return getName() + ", " + getCity().getName() + ", " + State.formatStateName(getCity().getState());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        
        Attraction attraction = (Attraction)obj;
        return this.getName().equals(attraction.getName());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
