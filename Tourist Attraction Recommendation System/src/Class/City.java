package Class;

import java.util.Objects;
import java.util.List;

public class City {
    
    // Attributes
    private String name;
    private State state;
    private int totalAttraction;

    // Default constructor
    public City(){
        this.name = "";
        this.state = null;
        this.totalAttraction = 0;
    }

    // Parameterised constructor
    public City(String name, State state) {
        this.name = name;
        this.state = state;
    }

    // Parameterised constructor with totalAttraction
    public City(String name, State state, int totalAttraction){
        this.name = name;
        this.state = state;
        this.totalAttraction = totalAttraction;
    }

    // Accessor and Mutator methods
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public State getState(){
        return state;
    }

    public void setState(State state){
        this.state = state;
    }
    
    public int getTotalAttraction() {
        return totalAttraction;
    }
    
    public void setTotalAttraction(int totalAttraction) {
        this.totalAttraction = totalAttraction;
    }

    // Normalise the city input to remove whitespace and convert to uppercase
    private static String normaliseCityInput(String input) {
        return input == null ? "" : input.trim().replaceAll("\\s+","".toUpperCase());
    }

    // Find a city by its name from the list of cities
    public static City findCity(String input) {

        // Normalise the input first
        String normalisedInput = normaliseCityInput(input);

        // Read the list of cities from the file
        List<City> cities = File.readCityFile();

        // Iterate through the list of cities to find a match for the normalised input
        for (City city : cities) {
            String normalisedCityName = normaliseCityInput(city.getName());
            if (normalisedCityName.equalsIgnoreCase(normalisedInput)) {
                return city;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        
        City city = (City)obj;
        return this.getName().equals(city.getName());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
