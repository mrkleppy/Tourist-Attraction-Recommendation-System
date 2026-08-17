package Class;

import java.util.Objects;
import java.util.List;

public class City {
    private String name;
    private State state;
    private int totalAttraction;

    public City(){
        this.name = "";
        this.state = null;
        this.totalAttraction = 0;
    }

    public City(String name, State state) {
        this.name = name;
        this.state = state;
    }

    public City(String name, State state, int totalAttraction){
        this.name = name;
        this.state = state;
        this.totalAttraction = totalAttraction;
    }

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

    private static String normaliseCityInput(String input) {
        return input == null ? "" : input.trim().replaceAll("\\s+","".toUpperCase());
    }

    public static City findCity(String input) {
        String normalisedInput = normaliseCityInput(input);

        List<City> cities = File.readCityFile();

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
