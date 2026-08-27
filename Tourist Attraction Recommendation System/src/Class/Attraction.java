package Class;

import java.util.Objects;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Attraction {

    // Attributes
    private String id;
    private String name;
    private City city;

    // Default constructor
    public Attraction(){
        this.id = "";
        this.name = "";
        this.city = null;
    }

    // Parameterised constructor
    public Attraction(String id, String name, City city){
        this.id = id;
        this.name = name;
        this.city = city;
    }

    // Accessor and Mutator methods
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
    
    // Generate the next attraction ID based on the existing attractions in the file
    public static String generateNextAttractionId() {
        List<Attraction> attractions = File.readAttractionFile();
    
        int maxId = 0;

        // Iterate through the attractions to find the maximum ID number
        for (Attraction attraction : attractions) {
            String id = attraction.getId();

            // Check if the ID is in the correct format (A followed by 4 digits)
            if (id != null && id.matches("A\\d{4}")) {
                int num = Integer.parseInt(id.substring(1)); // Removes the 'A' at the start
            
                // Update maxId if the current number is greater
                if (num > maxId) {
                    maxId = num;
                }
            }
        }

        // Return the next ID in the format A0001, A0002, etc.
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
